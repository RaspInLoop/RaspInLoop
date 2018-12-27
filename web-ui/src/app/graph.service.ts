import { Injectable } from '@angular/core';
import * as joint from 'jointjs';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import {Model, Component, Link, PortGroupDefinition, PortGroup, Port } from './model/model';
import { ModelDescription } from './model/modelDescription';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

export const MODEL_DESCRITPION: ModelDescription[] = [
  { id: 11, name: 'Mr. Nice', creationDate: "12/06/2018", description: "le model 11" },
  { id: 12, name: 'Narco', creationDate: "12/06/2018", description: "le model 12" },
  { id: 13, name: 'Bombasto', creationDate: "12/06/2018", description: "le model 13" },
  { id: 14, name: 'Celeritas', creationDate: "12/06/2018", description: "le model 14" },
  { id: 15, name: 'Magneta', creationDate: "12/06/2018", description: "le model 15" },
  { id: 16, name: 'RubberMan', creationDate: "12/06/2018", description: "le model 16" },
  { id: 17, name: 'Dynama', creationDate: "12/06/2018", description: "le model 17" },
  { id: 18, name: 'Dr IQ', creationDate: "12/06/2018", description: "le model 18" },
  { id: 19, name: 'Magma', creationDate: "12/06/2018", description: "le model 19" },
  { id: 20, name: 'Tornado', creationDate: "12/06/2018", description: "le model 20" }
];

export const MODEL: Model[] = [
  {
    id: 0,
    components: [{
      svgContent: "",
      position: { x: 100, y: 50 },
      size: { width: 100, height: 100 },
      portGroups: [
        {
          definition: {
            name: "1_dim_rotational",
            svg: '<circle  r="4" stroke="#8ea7aeff" stroke-width="2" fill="#232e31ff"  class="port-body" />'
          },
          ports: [{id:"ghi", position: { x: 100, y: 50 }, description:"port 3"}]
        },
        {
          definition: {
            name: "input_real",
            svg: '<path d="M -10,-5 0,0 -10,5 z" fill="#d45500" class="port-body" />'
          },
          ports: [{id:"abc", position: { x: 5, y: 30 }, description:"port 1"},
                  {id:"def", position: { x: 5, y: 60 }, description:"port 2"}]
        }
      ]
    }],
    links:[]
  }
];


export const EMPTY_MODEL: Model = {
    id: 0,
    components: [],
    links:[]
  };


@Injectable({
  providedIn: 'root'
})
export class GraphService {

  currentGraphId: number;
  graph: joint.dia.Graph;
  componentCtor: joint.dia.Cell.Constructor<joint.dia.Element>;
  private graphsUrl = 'api/graphs';  // URL to web api

  constructor( private http: HttpClient, private messageService: MessageService) {
    this.currentGraphId = 0;
    this.graph = new joint.dia.Graph;
    this.componentCtor = 
    joint.dia.Element.define('Mo.Component', {
      attrs: {
        rect: { width: 100, height: 100 },
        '.body': {
            fill: '#232e31ff', stroke: '#8ea7aeff', 'stroke-width': 2,
            'pointer-events': 'visiblePainted', rx: 10, ry: 10 }
        }
    }, { markup: '<g class="rotatable"><g class="scalable"><rect class="body"/><image/><title/></g></g>'
    });

    this.messageService.add('GraphService: graph Ctor done.');
  }

  paperReady() {
    this.getGraphModel(this.currentGraphId).subscribe(model => this.updateModel(model));
  }

  updateModel(model) {
    for (let component of model.components) {
      this.drawComponent(component);
    }
    for (let link of model.links) {
      this.drawLink(link);
    }
  }

  drawComponent(component: Component) {

    var rect = new this.componentCtor();
    rect.attr({
      image: {
        'xlink:href': 'data:image/svg+xml;utf8,' + component.svgContent
      } 
    });
    rect.prop('ports/groups',  this.getPortGroups(component.portGroups));
    rect.position(component.position.x, component.position.y);
    rect.resize(component.size.width, component.size.height);
    for (let portGroup of component.portGroups) {
      for (let port of portGroup.ports) {
        this.addPort(rect, port, portGroup.definition);
      }
    }
    rect.addTo(this.graph);
  }

  getPortGroups(portGroups: PortGroup[]): Object {

    let result = new Object;
    for (let portGroup of portGroups) {

      let groupdef = Object.assign({}, {
        position: { name: 'absolute', args: {} },
        attrs: {
          ".port-body": {
            "magnet": true
          }
        },
        markup: portGroup.definition.svg+"<title/>"
      });
      result[portGroup.definition.name] = groupdef;
    }
    console.log("adding portGroup", JSON.stringify(result) );
    return result;
  }

  drawLink(link: Link) {

  } 

  addPort(rect: joint.dia.Element, port: Port, groupdef: PortGroupDefinition){
    let portdef = Object.assign({},  {
      "id": port.id,
      "group": groupdef.name,
      "args": {
          "x": port.position.x,
          "y": port.position.y
      },
      "attrs": {
          "title": {
              "text": port.description
          }
      }
    });
    console.log("adding port", JSON.stringify(portdef) );
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

  getJointJSGraph(): Observable<joint.dia.Graph> {
    return of(this.graph);
  }

  getGraphModel(id: number): Observable<Model> {
    this.messageService.add('GraphService: fetched Model#' + id + ".");
    const url = `${this.graphsUrl}/model/${id}`;
    return this.http.get<Model>(url)
      .pipe(
        catchError(this.handleError<Model>('getGraphModel', EMPTY_MODEL))
      );
  }

  getGraphModelDescription(id: number): Observable<ModelDescription[]> {
    this.messageService.add('GraphService: fetched Model description.');
    const url = `${this.graphsUrl}/modeldescriptions}`;
    return this.http.get<ModelDescription[]>(url)
    .pipe(
      catchError(this.handleError<ModelDescription[]>('getGraphModel'))
    );
  }

  saveGraphModel(): void {
    var jsonString = JSON.stringify(this.graph.toJSON());
    console.log(jsonString);
    this.messageService.add('GraphService: current graph saved.');
  }

 
}
