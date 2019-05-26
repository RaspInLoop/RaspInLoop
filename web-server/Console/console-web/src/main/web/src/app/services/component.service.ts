import { Injectable, SecurityContext } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessageService } from './message.service';
import { catchError, map, tap } from 'rxjs/operators';
import {Component, Point, Size, Port, Parameters, PortGroup} from '../model/model';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';




interface ComponentJSON {
    id: string;
    name: string;
    svgIcon: string;
    position: Point;
    size: Size;
    portGroups: PortGroupJSON[];
    description: string;
    htmlDocumentation: string;
    parameters?: Parameters[];
}

interface PortGroupJSON{
  ports: Port[];
  definition: PortGroupDefinitionJSON;
}

interface PortGroupDefinitionJSON{
  name: string;
  svg: string;
}

@Injectable({
  providedIn: 'root'
})
export class ComponentService {

    // map componnent ID with Component
  dataMap = new Map<string, Component>();
  private modelicaServiceUrl = 'api/modelica/component';  // URL to web api
  constructor(private http: HttpClient, private messageService: MessageService, private sanitizer: DomSanitizer) { }

  getComponent(nodeId: string):  Observable<Component>  {
    if (!this.dataMap.has(nodeId))
    {
      return this.getAndStore(nodeId);
    } else {
      return of(this.dataMap.get(nodeId));
    }
  }

  private getAndStore(nodeId: string): Observable<Component>{
    const url = `${this.modelicaServiceUrl}/${nodeId}`;
    return this.http.get<any>(url)
    .pipe(
      map(apiResult => this.storeAndConvert(apiResult)),
      catchError(this.handleError<Component>('getPackage',
                                                    {id: '',
                                                     name: 'Unknown',
                                                     svgContent: '<svg/>',
                                                     position: {x: 0, y: 0},
                                                     size: {width: 0, height: 0},
                                                     portGroups: [],
                                                     description: '',
                                                     htmlDocumentation: '',
                                                     parameters: []})
    ));
  }

  private storeAndConvert(apiResult: ComponentJSON): Component {
    const component = this.buildComponent(apiResult);
    this.dataMap.set(component.id, component);
    return component;
  }

  private buildComponent(componentJSON: ComponentJSON): Component {
    let parameters: Parameters[];
    if (componentJSON.parameters !== undefined) {
      parameters = componentJSON.parameters;
    }

    let portGroups: PortGroup[];
    if (componentJSON.portGroups !== undefined) {
      portGroups = componentJSON.portGroups.map(p => Object.assign({}, p, {
        definition:  {name: p.definition.name,
                      svg:  p.definition.svg .replace(/\<\?xml.+\?\>|\<\!DOCTYPE.+]\>/g, '')}
         })
      );
    }
    return Object.assign({}, componentJSON, {
      svgContent: componentJSON.svgIcon.replace(/\<\?xml.+\?\>|\<\!DOCTYPE.+]\>/g, ''), // TODO: remove script
      portGroups: portGroups,
      parameters: parameters
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
