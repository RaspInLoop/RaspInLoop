import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { GraphService} from '../graph.service'
import { Graph } from '../model/graph';


class JsonCodec extends mxObjectCodec {

    // constructor() {
    //   super((value)=>{});
    // }

    encode(value) {
        const xmlDoc = mxUtils.createXmlDocument();
        const newObject = xmlDoc.createElement("Object");
        for(let prop in value) {
          newObject.setAttribute(prop, value[prop]);
        }
        return newObject;
    }

    decode(model) {
      return Object.keys(model.cells).map(
        (iCell)=>{
          const currentCell = model.getCell(iCell);
          return (currentCell.value !== undefined)? currentCell : null;
        }
      ).filter((item)=> (item !== null));
    }
}

@Component({
  selector: 'app-hardware-editor',
  templateUrl: './hardware-editor.component.html',
  styleUrls: ['./hardware-editor.component.scss']
})
export class HardwareEditorComponent implements AfterViewInit {

  _graph :mxGraph;

  constructor(private graphService: GraphService) {
  }

   @ViewChild('graphContainer') graphContainer: ElementRef;
   ngAfterViewInit() {

     if (!mxClient.isBrowserSupported()) {
         return mxUtils.error('Browser is not supported!', 200, false);
     }
      mxEvent.disableContextMenu(graphContainer);
      this._graph = new mxGraph(graphContainer);
      this._graph.setConnectable(true);
      this._graph.setAllowDanglingEdges(false);
      new mxRubberband(this._graph); // Enables rubberband selection

      this.bindEvents();
      this.labelDisplayOveride();
      this.styling();

       // Now load the data from server

     this.refreshGraphModelData();
   }

   refreshGraphModelData() {
     this.graphService.getCurrentGraph().subscribe(graph => { this.render(graph.json);} );
   }

  labelDisplayOveride() { // Overrides method to provide a cell label in the display
    this._graph.convertValueToString = (cell)=> {
      if (mxUtils.isNode(cell.value)) {
        if (cell.value.nodeName.toLowerCase() === 'object') {
          const name = cell.getAttribute('name', '');
          return name;
        }
      }
      return '';
    };
  }

  styling() {
    // Creates the default style for vertices
    let style = [];
    style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RECTANGLE;
    style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
    style[mxConstants.STYLE_STROKECOLOR] = 'gray';
    style[mxConstants.STYLE_ROUNDED] = true;
    style[mxConstants.STYLE_FILLCOLOR] = '#b0b0b0';
    style[mxConstants.STYLE_GRADIENTCOLOR] = '#909090';
    style[mxConstants.STYLE_FONTCOLOR] = 'black';
    style[mxConstants.STYLE_FOLDABLE] = 0;
    style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
    style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
    style[mxConstants.STYLE_FONTSIZE] = '12';
    style[mxConstants.STYLE_FONTSTYLE] = 0;
    this._graph.getStylesheet().putDefaultVertexStyle(style);

    style = new Object();
    this._graph.getStylesheet().putCellStyle('main', style);

    style = new Object();
    style[mxConstants.STYLE_STROKECOLOR] = '#fdc251';
    style[mxConstants.STYLE_FILLCOLOR] = 'none';
    style[mxConstants.STYLE_ROUNDED] = false;
    this._graph.getStylesheet().putCellStyle('main_transitions', style);

    style = new Object();
    style[mxConstants.STYLE_ROUNDED] = false;
    style[mxConstants.STYLE_STROKECOLOR] = '#fdc251';
    this._graph.getStylesheet().putCellStyle('transition', style);

    style = new Object();
    style[mxConstants.STYLE_ROUNDED] = false;
    style[mxConstants.STYLE_STROKECOLOR] = '#5796fe';
    this._graph.getStylesheet().putCellStyle('action', style);

    //Port style
    style = new Object();
    style[mxConstants.STYLE_FONTCOLOR] = '#774400';
    style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_LEFT;
    style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
    style[mxConstants.STYLE_FONTSIZE] = '10';
    style[mxConstants.STYLE_FONTSTYLE] = 2;

    style = new Object();
    style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_IMAGE;
    style[mxConstants.STYLE_IMAGE_WIDTH] = '32';
	  style[mxConstants.STYLE_IMAGE_HEIGHT] = '32';
    style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
    style[mxConstants.STYLE_PERIMETER_SPACING] = '6';
    style[mxConstants.STYLE_FILLCOLOR] = '#fdc251';
    style[mxConstants.STYLE_GRADIENTCOLOR] = '#e57405';
    style[mxConstants.STYLE_FOLDABLE] = 0;
    this._graph.getStylesheet().putCellStyle('port_transitions', style);

    style = new Object();
    style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_IMAGE;
    style[mxConstants.STYLE_IMAGE_WIDTH] = '32';
		style[mxConstants.STYLE_IMAGE_HEIGHT] = '32';
    style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
    style[mxConstants.STYLE_PERIMETER_SPACING] = '6';
    style[mxConstants.STYLE_FILLCOLOR] = '#5796fe';
    style[mxConstants.STYLE_GRADIENTCOLOR] = '#0567dd';
    this._graph.getStylesheet().putCellStyle('port_actions', style);

    // Creates the default style for edges
    let transitionsEdgeStyle = this._graph.getStylesheet().getDefaultEdgeStyle();
    transitionsEdgeStyle[mxConstants.STYLE_STROKECOLOR] = '#ffffff';
    transitionsEdgeStyle[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR] = '#000000';
    transitionsEdgeStyle[mxConstants.STYLE_STROKEWIDTH] = '2';
    transitionsEdgeStyle[mxConstants.STYLE_ROUNDED] = true;
    transitionsEdgeStyle[mxConstants.STYLE_EDGE] = mxEdgeStyle.EntityRelation;

  }

  getJsonModel() {
      const encoder = new JsonCodec();
      const jsonModel = encoder.decode(this._graph.getModel());
      return {
        "graph": jsonModel
      }
  }

  bindEvents() {
    this._graph.addListener(
      mxEvent.CLICK,
      (sender, evt) => {
        let cell = evt.getProperty('cell');
        if (cell && cell !== null) {
              if(cell.value === "PORT_Transitions" || cell.value === "PORT_Actions") {
                cell.setCollapsed(!cell.collapsed);
                this._graph.getView().clear(cell, false, false);
                this._graph.getView().validate();
              }
        }
      }
    );
  }

  render(dataModel) {
        const jsonEncoder = new JsonCodec();

        this._vertices = {};
        this._ports = {};
        this._dataModel = dataModel;

				const parent = this._graph.getDefaultParent();
				this._graph.getModel().beginUpdate(); // Adds cells to the model in a single step
				try {

          this._dataModel.graph.map(
            (node)=> {
                  if(typeof node.value === "object") {
                      const entity = node.value;
                      if(entity.type === "component") {
                        const xmlNode = jsonEncoder.encode(entity);
                        this._vertices[node.id] = this._graph.insertVertex(parent, null, xmlNode, node.geometry.x, node.geometry.y, node.geometry.width, node.geometry.height,
                                                                         entity.mainNode==="true"?"main;":"", false);
                        //console.log(this._vertices[node.id])
                        this._vertices[node.id].setConnectable(false);

                        if(node.children) {
                          node.children.forEach(
                            (child)=>{
                              if(child.value === "PORT_Transitions" || child.value === "PORT_Actions") {
                                this._ports[child.id] = this._graph.insertVertex(this._vertices[node.id], null, child.value, child.geometry.x, child.geometry.y, child.geometry.width, child.geometry.height, child.style, true);
                                this._ports[child.id].geometry.offset = new mxPoint(child.geometry.offset.x, child.geometry.offset.y);

                                   if(child.value === "PORT_Transitions" || child.value === "PORT_Actions") {
                                      this._ports[child.id].setCollapsed(true);
                                      this._ports[child.id].setConnectable(false);
                                      const type = "state";
                                      //if( entity.mainNode === "true") {
                                         let name = "transition";
                                         if(child.value === "PORT_Actions") {
                                           name = "action";
                                         }

                                        if(child.children) {
                                          child.children.forEach(
                                            (grandChild)=>{
                                              if(typeof grandChild.value === "object") {
                                                const grandChildEntity = grandChild.value;
                                                if(grandChildEntity.type === "state") {
                                                  const grandChildXmlNode = jsonEncoder.encode(grandChildEntity);
                                                  this._vertices[grandChild.id] = this._graph.insertVertex(this._ports[child.id], null, grandChildXmlNode, grandChild.geometry.x, grandChild.geometry.y, grandChild.geometry.width, grandChild.geometry.height, grandChild.style, true);
                                                  this._vertices[grandChild.id].geometry.offset = new mxPoint(grandChild.geometry.offset.x, grandChild.geometry.offset.y);
                                                }
                                              }
                                            }
                                          )
                                        }
                                   }
                              }
                            }
                          );
                        }
                    }
                  }
            }
          );

          this._dataModel.graph.map(
            (node)=> {
                  if(node.value === "Edge" || node.value === null) {
                       this._graph.insertEdge(parent, null, 'Edge', this._vertices[node.source],  this._vertices[node.target])
                  }
            }
          );

				} finally {
					this._graph.getModel().endUpdate(); // Updates the display
        }
  }
}
