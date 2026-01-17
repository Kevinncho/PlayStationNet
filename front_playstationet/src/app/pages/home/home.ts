import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth/auth.service';
import { ButtonModule } from "primeng/button";
import { CommonModule } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { Login } from '../login/login';
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule,
    DialogModule,
    Login
],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {
  showLogin = false;

  onLoginSuccess() {
    this.showLogin = false;
  }
}
