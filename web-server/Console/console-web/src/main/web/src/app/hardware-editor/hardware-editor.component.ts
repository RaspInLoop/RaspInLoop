import { Component, AfterViewInit } from '@angular/core';
import { GraphService} from '../services/graph.service';
import * as $ from 'jquery';



@Component({
  selector: 'app-hardware-editor',
  templateUrl: './hardware-editor.component.html',
  styleUrls: ['./hardware-editor.component.scss']
})
export class HardwareEditorComponent implements  AfterViewInit {

  private paperScroller: any;
  constructor(private graphService: GraphService) {

  }

  ngAfterViewInit() {
    const element = $('#paper');
    this.graphService.getPaper(element);
   }

  allowDrop(ev) {
    ev.preventDefault();
  }

  drop(ev) {
    ev.preventDefault();
    const compId = ev.dataTransfer.getData('text');
    this.graphService.addComponenent(compId);
  }



}
