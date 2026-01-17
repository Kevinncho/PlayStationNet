import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
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
  styleUrls: ['./login.scss'],
})
export class Login {

  @Output() close = new EventEmitter<void>();

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
      next: (response) =>{
        this.loading = false;

        //Save token
        localStorage.setItem('token', response.token);

        this.messageService.add({
          severity: 'success',
          summary: 'Login succesful',
          detail: 'Welcome to the Game Store'
        });

        setTimeout(() => this.close.emit(), 600);
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
