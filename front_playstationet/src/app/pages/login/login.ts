import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ToastModule } from 'primeng/toast';
import { AuthService } from '../../core/services/auth/auth.service';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    FormsModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    CardModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  email: string = '';
  password: string = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private messageService: MessageService
  ) {}

  login(form: NgForm) {
    if (form.invalid) return;

    this.loading = true;

    this.authService.login(this.email, this.password).subscribe({
      next: () =>{
        this.loading = false;
        this.messageService.add({
          severity: 'succes',
          summary: 'Login succesful',
          detail: 'Welcome to the Game Store'
        });
      },
      error: () => {
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Login failed',
          detail: 'Invalid email or password'
        });
      }
    })
  }
}
