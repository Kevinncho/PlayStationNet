import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { AuthService, UserProfile } from '../../core/services/auth/auth.service';

@Component({
  selector: 'app-user-data',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, TagModule],
  templateUrl: './user-data.html',
  styleUrl: './user-data.scss',
})
export class UserData implements OnInit {
  loading = true;
  updating = false;
  errorMessage = '';
  user: UserProfile | null = null;

  constructor(private readonly authService: AuthService) {}

  ngOnInit(): void {
    const fallbackUsername = this.authService.getCurrentUsername();
    const roles = this.authService.getRoles();

    if (fallbackUsername) {
      this.user = {
        username: fallbackUsername,
        roles,
        isAdmin: roles.includes('ROLE_ADMIN'),
      };
    }

    this.loadUserData();
  }

  loadUserData(): void {
    const token = this.authService.getToken();
    if (!token) {
      this.loading = false;
      this.updating = false;
      this.user = null;
      this.errorMessage = 'No hay sesion iniciada. Inicia sesion para ver tus datos.';
      return;
    }

    this.loading = !this.user;
    this.updating = !!this.user;
    this.errorMessage = '';

    this.authService.getMyUser().subscribe({
      next: (profile) => {
        this.user = profile;
        this.loading = false;
        this.updating = false;
      },
      error: () => {
        const fallbackUsername = this.authService.getCurrentUsername();
        const roles = this.authService.getRoles();

        this.user = {
          username: fallbackUsername ?? 'Usuario',
          roles,
          isAdmin: roles.includes('ROLE_ADMIN'),
        };
        this.loading = false;
        this.updating = false;
        this.errorMessage =
          'No se pudieron cargar todos tus datos del servidor. Mostrando datos del token local.';
      },
    });
  }

  getDisplayName(): string {
    if (!this.user) return '';

    const fullName = `${this.user.firstName ?? ''} ${this.user.lastName ?? ''}`.trim();
    return fullName || this.user.username;
  }
}
