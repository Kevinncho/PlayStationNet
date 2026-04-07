import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
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
export class Sidebar {
  collapsed = false;
  readonly username$;
  readonly isLoggedIn$;

  constructor(private readonly authService: AuthService) {
    this.username$ = this.authService.currentUsername$;
    this.isLoggedIn$ = this.authService.isLoggedIn$;
  }

  toggleSidebar() {
    this.collapsed = !this.collapsed;
  }
}
