import {CollectionViewer, SelectionChange} from '@angular/cdk/collections';
import {FlatTreeControl} from '@angular/cdk/tree';
import {Component, Injectable} from '@angular/core';
import {BehaviorSubject, merge, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {PackageService, PackageTreeNode} from '../services/package.service';





/**
 * File database, it can build a tree structured Json object from string.
 * Each node in Json object represents a file or a directory. For a file, it has filename and type.
 * For a directory, it has filename and children (a list of files or directories).
 * The input will be a json object string, and the output is a list of `FileNode` with nested
 * structure.
 */
@Injectable()
export class DynamicDataSource {

  dataChange = new BehaviorSubject<PackageTreeNode[]>([]);

  get data(): PackageTreeNode[] { return this.dataChange.value; }
  set data(value: PackageTreeNode[]) {
    this.treeControl.dataNodes = value;
    this.dataChange.next(value);
  }

  constructor(private treeControl: FlatTreeControl<PackageTreeNode>,
              private database: PackageService) {}

  connect(collectionViewer: CollectionViewer): Observable<PackageTreeNode[]> {
    this.treeControl.expansionModel.onChange.subscribe(change => {
      if ((change as SelectionChange<PackageTreeNode>).added ||
        (change as SelectionChange<PackageTreeNode>).removed) {
        this.handleTreeControl(change as SelectionChange<PackageTreeNode>);
      }
    });

    return merge(collectionViewer.viewChange, this.dataChange).pipe(map(() => this.data));
  }

  /** Handle expand/collapse behaviors */
  handleTreeControl(change: SelectionChange<PackageTreeNode>) {
    if (change.added) {
      change.added.forEach(node => this.toggleNode(node, true));
    }
    if (change.removed) {
      change.removed.slice().reverse().forEach(node => this.toggleNode(node, false));
    }
  }

  /**
   * Toggle the node, remove from display list
   */
  toggleNode(node: PackageTreeNode, expand: boolean) {
    const index = this.data.indexOf(node);
    if (!node.childrenIds || index < 0) { // If no children, or cannot find the node, no op
      return;
    }

    node.isLoading = true;
    if (expand) {
        const nodes = node.childrenIds.map(id => this.database.getPackageTreeNode(id)
                                                  .subscribe( ptn => {
                                                    this.data.splice(index + 1, 0, ptn);
                                                    // notify the change
                                                    this.dataChange.next(this.data);
                                                    node.isLoading = false;
                                                  }));

      } else {
        let count = 0;
        for (let i = index + 1; i < this.data.length
          && this.data[i].level > node.level; i++, count++) {}
        this.data.splice(index + 1, count);
        this.dataChange.next(this.data);
        node.isLoading = false;
      }
  }
}


@Component({
  selector: 'app-blocktree',
  templateUrl: './blocktree.component.html',
  styleUrls: ['./blocktree.component.scss'],
    providers: [PackageService]
})
export class BlocktreeComponent {
  constructor(database: PackageService) {
    this.treeControl = new FlatTreeControl<PackageTreeNode>(this.getLevel, this.isExpandable);
    this.dataSource = new DynamicDataSource(this.treeControl, database);
    database.initialData().subscribe(packageTreeNode => {this.dataSource.data = [packageTreeNode];
                                                          this.dataSource.dataChange.next( this.dataSource.data);
                                                          }
                                                          );
  }

  treeControl: FlatTreeControl<PackageTreeNode>;

  dataSource: DynamicDataSource;

  getLevel = (node: PackageTreeNode) => node.level;

  isExpandable = (node: PackageTreeNode) => node.childrenIds.length > 0;

  hasChild = (_: number, _nodeData: PackageTreeNode) => _nodeData.childrenIds.length;

  drag(ev) {
    ev.dataTransfer.setData('text', ev.target.id);
  }
}
