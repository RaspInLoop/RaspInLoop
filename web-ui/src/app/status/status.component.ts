import { Component, OnInit } from '@angular/core';
import { MessageService } from '../message.service';
import { GraphService } from '../graph.service';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss']
})
export class StatusComponent implements OnInit {

  constructor(public messageService: MessageService, public graphService: GraphService) {}

  ngOnInit() {
  }

}
