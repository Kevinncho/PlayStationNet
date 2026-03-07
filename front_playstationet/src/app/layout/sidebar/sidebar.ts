import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AvatarModule } from 'primeng/avatar';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { StyleClassModule } from 'primeng/styleclass';
@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, ButtonModule, AvatarModule, RippleModule, StyleClassModule],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.scss'],
})
export class Sidebar {
  collapsed = false;

  toggleSidebar() {
    this.collapsed = !this.collapsed;
  }
}
