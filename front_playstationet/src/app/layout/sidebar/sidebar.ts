import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AvatarModule } from 'primeng/avatar';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { StyleClassModule } from 'primeng/styleclass';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../core/services/auth/auth.service';
@Component({
  selector: 'app-sidebar',
  imports: [
    CommonModule,
    ButtonModule,
    AvatarModule,
    RippleModule,
    StyleClassModule,
    RouterLink,
    RouterLinkActive,
  ],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.scss'],
})
export class Sidebar implements OnInit {
  collapsed = false;
  username = 'Usuario';

  constructor(private readonly authService: AuthService) {}

  ngOnInit(): void {
    const tokenUser = this.authService.getCurrentUsername();
    if (tokenUser) {
      this.username = tokenUser;
    }
  }

  toggleSidebar() {
    this.collapsed = !this.collapsed;
  }
}
