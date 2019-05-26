import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'RaspInLoop';

  isExpanded = false;
  element: HTMLElement;

  toggleActive(event: any) {
    event.preventDefault();
    if (this.element !== undefined){
      this.element.style.backgroundColor = '#232e31';
    }
    const target = event.currentTarget;
    target.style.backgroundColor = '#d36423';
    this.element = target;
  }
}
