import { Component, OnInit } from '@angular/core';
import { MessageService } from '../services/message.service';
import { GraphService } from '../services/graph.service';

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
