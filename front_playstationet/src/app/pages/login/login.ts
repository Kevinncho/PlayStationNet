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

  username: string = '';
  password: string = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private messageService: MessageService
  ) {}


  login(form: NgForm): void {
    if (form.invalid) return;

    this.loading = true;

    this.authService.login(this.username, this.password).subscribe({
      next: (response) =>{
        console.log('✅ LOGIN OK');
        console.log('TOKEN:', localStorage.getItem('token'));
        console.log('ROLE:', localStorage.getItem('role'));
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
      error: (err) => {
        console.error('❌ ERROR LOGIN', err);
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Login failed',
          detail: 'Invalid username or password'
        });
      }
    })
  }
}
