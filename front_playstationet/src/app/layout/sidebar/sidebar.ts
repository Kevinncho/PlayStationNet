import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MenuItem } from 'primeng/api';

export interface MenuItemProfile extends MenuItem {
  perfil?: number[];
}

@Component({
  selector: 'app-sidebar',
  imports: [],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.scss'],
})
export class Sidebar implements OnInit {
  @Input() show: boolean = false;

  @Output() selection = new EventEmitter<string>();

  select(app: string) {
    this.selection.emit(app);
  }

  globalProfileId!: number;

  menuItems: MenuItemProfile[] = [
      {
        label: 'Menu 1',
        items: [
          {
            label: 'Inicio',
            icon: 'pi pi-home',
            command: () => this.select('inicio'),
            perfil: [1,2,3,4,5]
          }
        ]
      },
      {
        label: 'Menú 2',
        items: [
          {
            label: 'Home2',
            icon: 'pi pi-home'
          }
        ]
      }
    ];

  ngOnInit() {
    this.select('');
  }
}
