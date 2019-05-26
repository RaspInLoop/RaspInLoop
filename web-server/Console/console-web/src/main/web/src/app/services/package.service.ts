import { Injectable, SecurityContext } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessageService } from './message.service';
import { catchError, map, tap } from 'rxjs/operators';
import {Package} from '../model/model';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';


/** Flat node with expandable and level information */
export class PackageTreeNode {
  constructor(public id: string,
              public item: string,
              public icon: SafeHtml,
              public level = 0,
              public childrenIds: string[],
              public isLoading = false) {}
}

interface PackageJSON {
  name: string;
  description: string;
  id: string;
  svgIcon: string;
  packagesNames?: string[];
  componentsName?: string[];
}

@Injectable({
  providedIn: 'root'
})
/**
 * Database for dynamic data. When expanding a node in the tree, the data source will need to fetch
 * the descendants data from the database.
 */
export class PackageService {
  // map package ID with package
  dataMap = new Map<string, Package>();
  private modelicaServiceUrl = 'api/modelica/package';  // URL to web api

  constructor( private http: HttpClient, private messageService: MessageService, private sanitizer: DomSanitizer ) {
  }

  rootLevelNodes: string[] = ['Modelica'];

  /** Initial data from database */
  initialData(): Observable<PackageTreeNode> {
    return this.getAndStore('Modelica');
  }

  getPackageTreeNode(nodeId: string):  Observable<PackageTreeNode>  {
    if (!this.dataMap.has(nodeId)) {
      return this.getAndStore(nodeId);
    } else {
      const _package = this.dataMap.get(nodeId);
      return of (new PackageTreeNode(_package.id,
          _package.name,
          _package.icon,
          this.computeLevel(_package.id),
          _package.childIds,
          false));
    }
  }

  private getAndStore(nodeId: string): Observable<PackageTreeNode> {
    const url = `${this.modelicaServiceUrl}/${nodeId}`;
    return this.http.get<any>(url)
    .pipe(
      map(result => this.storeAndConvert(result)),
      catchError(this.handleError<PackageTreeNode>('getPackage',
                                                    new PackageTreeNode('',
                                                      'Unknown',
                                                      this.sanitizer.bypassSecurityTrustHtml( '<svg/>'),
                                                      0,
                                                      [])))
    );
  }

  private storeAndConvert(packageJSON: PackageJSON): PackageTreeNode {
    const _package = this.buildPackage(packageJSON);
    this.dataMap.set(_package.id, _package);
    if (_package.childIds === undefined) {
      _package.childIds = [];
    }
    return new PackageTreeNode(_package.id,
            _package.name,
            _package.icon,
            this.computeLevel(_package.id),
            _package.childIds.concat(_package.componentIds),
             false);
  }

  private computeLevel(id: string): number {
    // ID has the following form: package1.package2.package3.model
    return id.split('.').length - 1;
  }

  private buildPackage(packageJSON: PackageJSON): Package {
    let componentIds: string [];
    let childsIds: string [];
    if (packageJSON.packagesNames !== undefined) {
      childsIds = packageJSON.packagesNames.map(p => packageJSON.id + '.' + p);
    }
    if (packageJSON.componentsName !== undefined) {
      componentIds = packageJSON.componentsName.map(c => packageJSON.id + '.' + c);
    }

    return Object.assign({}, packageJSON, {
      icon: this.sanitizer.bypassSecurityTrustHtml(packageJSON.svgIcon), // TODO: remove script
      childIds: childsIds,
      componentIds: componentIds
    });
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      this.messageService.add(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}

