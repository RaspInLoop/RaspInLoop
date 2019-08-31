import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import {ComponentService} from './component.service';
import {Model, Package, Component, Link, PortGroupDefinition, PortGroup, Port } from '../model/model';
import { ModelDescription } from '../model/modelDescription';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import * as _ from 'lodash';
import * as backbone from 'backbone';
import * as joint from 'jointjs';


export const EMPTY_MODEL: Model = {
    id: 0,
    components: [],
    links: []
  };


@Injectable({
  providedIn: 'root'
})
export class GraphService {

  currentGraphId: number;
  graph: joint.dia.Graph;
  paper: joint.dia.Paper;

  componentCtorMap = new Map();
  linkCtor:  joint.dia.Cell.Constructor<joint.dia.Link>;

  dataMap = new Map<string, Package>();
  private graphsUrl = 'api/modelica/package';  // URL to web api

  constructor( private http: HttpClient, private messageService: MessageService, private  componentService: ComponentService) {
    this.currentGraphId = 0;
    this.graph = new joint.dia.Graph;
    this.messageService.add('GraphService: graph Ctor done.');
  }

  addComponenent(compId: string) {
    this.componentService.getComponent(compId).subscribe(comp => this.updateComponent(comp));
  }

  updateComponent(component) {
      this.drawComponent(component);
  }

  addModel(modelId: string) {
    this.getGraphModel(modelId).subscribe(model => this.updateModel(model));
  }

  updateModel(model) {
    for (const component of model.components) {
      this.drawComponent(component);
    }
    for (const link of model.links) {
      this.drawLink(link);
    }
  }

  drawComponent(component: Component) {

    if (! this.componentCtorMap.has(component.id)) {
      const ctor = joint.dia.Element.define(component.id, {
        attrs: {
          '.body': {
              fill: '#232e31ff', stroke: '#8ea7aeff', 'stroke-width': 2,
              'pointer-events': 'visiblePainted', rx: 10, ry: 10 }
          }
      }, { markup: '<g class="rotatable" >' +
                   ' <g class="scalable">' + component.svgContent + '<title/></g></g>'}
      );
      this.componentCtorMap.set(component.id, ctor);
    }

    const ctor = this.componentCtorMap.get(component.id);
    const rect = new ctor();

    rect.prop('ports/groups',  this.getPortGroups(component.portGroups));
    rect.position(component.position.x, component.position.y);
    rect.resize(component.size.width, component.size.height);
    for (const portGroup of component.portGroups) {
      for (const port of portGroup.ports) {
        this.addPort(rect, port, portGroup.definition);
      }
    }
    rect.addTo(this.graph);
  }

  getPortGroups(portGroups: PortGroup[]): Object {

    const result = new Object;
    for (const portGroup of portGroups) {

      const groupdef = Object.assign({}, {
        position: { name: 'absolute', args: {} },
        attrs: {
          '.port-body': {
            'magnet': true
          }
        },
        markup: portGroup.definition.svg + '<title/>'
      });
      result[portGroup.definition.name] = groupdef;
    }
    console.log('adding portGroup', JSON.stringify(result) );
    return result;
  }

  drawLink(link: Link) {

  }

  addPort(rect: joint.dia.Element, port: Port, groupdef: PortGroupDefinition) {
    const portdef = Object.assign({},  {
      'id': port.id,
      'group': groupdef.name,
      // by default port are extends from -100 to 100 (200)
      'args': {
          'x': (port.position.x / 200.0) * rect.size().width ,
          'y': (port.position.y / 200.0) * rect.size().height,
          'angle': port.orientation
      },
      'attrs': {
          'title': {
              'text': port.description
          }
      }
    });
    console.log('adding port', JSON.stringify(portdef) );
    rect.addPort(portdef);
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

  getPaper(element: any): joint.dia.Paper {
    if (this.paper === undefined) {
      this.initPaper(element);
    }
    return this.paper;
  }

  initPaper(element: any) {
     this.paper = new joint.dia.Paper({
       el: element,
       model: this.graph,
       gridSize: 1,
       snapLinks: true,
       interactive: true,
       linkPinning: false,

       validateConnection: function(sourceView, sourceMagnet, targetView, targetMagnet) {
// tslint:disable-next-line: triple-equals
           return sourceMagnet != targetMagnet;   }
     });

     this.paper.options.defaultRouter = {
         name: 'manhattan'    };

     this.paper.options.defaultConnector = {
         name: 'rounded',
         args: {  radius: 20  }
     };

     this.linkCtor = joint.dia.Link.define('Mo.Link', {
         attrs: {
             line: {
                 connection: true,
                 stroke: '#eeeeee',
                 strokeWidth: 2,
                 strokeLinejoin: 'round'
             },
             wrapper: {
                 connection: true,
                 strokeWidth: 10,
                 strokeLinejoin: 'round'
             }
         }
     }, {
         markup: [{
             tagName: 'path',
             selector: 'wrapper',
             attributes: {
                 'fill': 'none',
                 'cursor': 'pointer',
                 'stroke': 'transparent'
             }
         }, {
             tagName: 'path',
             selector: 'line',
             attributes: {
                 'fill': 'none',
                 'pointer-events': 'none'
             }
         }]
     });

     this.paper.options.defaultLink = new  this.linkCtor();
     this.paper.on('link:mouseenter', function(linkView) {
        const removeTool = new joint.linkTools.Remove();

       const tools = [
         new joint.linkTools.Vertices(),
         new joint.linkTools.SourceArrowhead(),
         new joint.linkTools.TargetArrowhead(),
         removeTool
       ];
       linkView.addTools(new joint.dia.ToolsView({
         name: 'onhover',
         tools: tools
       }));
     });

     this.paper.on('link:mouseleave', function(linkView) {
       if (!linkView.hasTools('onhover')) {
          return;
       }
       linkView.removeTools();
     });

     this.paper.on('myclick:circle', function(linkView, evt) {
          evt.stopPropagation();
          const link = linkView.model;
          const t = (link.attr('c1/atConnectionRatio') > .2) ? .2 : .9;
          const transitionOpt = {
              delay: 100,
              duration: 2000,
              timingFunction: joint.util.timing.inout
          };
          link.transition('attrs/c1/atConnectionRatio', t, transitionOpt);
          link.transition('attrs/c2/atConnectionRatio', t, transitionOpt);
      });
   }

  getGraphModel(id: string): Observable<Model> {
    this.messageService.add('GraphService: fetched Model#' + id + '.');
    const url = `${this.graphsUrl}/model/${id}`;
    return this.http.get<Model>(url)
      .pipe(
        catchError(this.handleError<Model>('getGraphModel', EMPTY_MODEL))
      );
  }

  saveGraphModel(): void {
    const jsonString = JSON.stringify(this.graph.toJSON());
    console.log(jsonString);
    this.messageService.add('GraphService: current graph saved.');
  }

}
