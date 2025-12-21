import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  imports: [],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss',
})
export class Sidebar {
  @Input() show: boolean = false;

  @Output() selection = new EventEmitter<string>();




  select(app: string) {
    this.selection.emit(app);
  }
}
