import { Component, OnInit } from '@angular/core';
import { GraphService} from '../graph.service'
import * as jQuery from 'jquery';
import * as _ from 'lodash';
import * as $ from 'backbone';
import * as joint from 'jointjs';


@Component({
  selector: 'app-hardware-editor',
  templateUrl: './hardware-editor.component.html',
  styleUrls: ['./hardware-editor.component.scss']
})
export class HardwareEditorComponent implements OnInit {


  constructor(private graphService: GraphService) {

  }



   paper: joint.dia.Paper;

  ngOnInit() {

     this.graphService.getCurrentGraph().subscribe(graph => this.initialisePaper(graph) );
   }


   initialisePaper(graph: joint.dia.Graph ) {
     this.paper = new joint.dia.Paper({
       el: jQuery("#paper"),
       width: 900,
       height: 400,
       model: graph,
       gridSize: 1,
       snapLinks: true,
       interactive: true,
       linkPinning:false,

       validateConnection: function(sourceView, sourceMagnet, targetView, targetMagnet) {
           return sourceMagnet != targetMagnet;   }
     });

     this.paper.options.defaultRouter = {
         name: 'manhattan'    }

     this.paper.options.defaultConnector = {
         name: 'rounded',
         args: {  radius: 20  }
     }

     var Link = joint.dia.Link;

     Link.define('Mo.Link', {
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

    // this.paper.options.defaultLink = new  joint.shapes.devs.Link();
     this.paper.options.defaultLink = new  joint.shapes.Mo.Link();
     this.paper.on('link:mouseenter', function(linkView) {
        var removeTool = new joint.linkTools.Remove();
        
       var tools = [
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
       if (!linkView.hasTools('onhover')) return;
       linkView.removeTools();
     });

     this.paper.on('myclick:circle', function(linkView, evt) {
          evt.stopPropagation();
          var link = linkView.model;
          var t = (link.attr('c1/atConnectionRatio') > .2) ? .2 :.9;
          var transitionOpt = {
              delay: 100,
              duration: 2000,
              timingFunction: joint.util.timing.inout
          };
          link.transition('attrs/c1/atConnectionRatio', t, transitionOpt);
          link.transition('attrs/c2/atConnectionRatio', t, transitionOpt);
      });

     this.graphService.paperReady();
   }


}
