import { Component } from '@angular/core';
import { ButtonModule } from "primeng/button";
import { CommonModule } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { Login } from '../login/login';
import { Register } from '../register/register/register';
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule,
    DialogModule,
    Login,
    Register
],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {
  showLogin = false;
  showRegister = false;

  onLoginSuccess() {
    this.showLogin = false;
  }
}
