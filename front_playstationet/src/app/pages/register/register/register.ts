import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { AuthService } from '../../../core/services/auth/auth.service';

@Component({
  selector: 'app-register',
  imports: [
    CommonModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    ReactiveFormsModule,
    CardModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  @Output() close = new EventEmitter<void>();

  step = 1;
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private messageService: MessageService
  ) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],

      // Step 2 (after)
      firstName: [''],
      lastName: [''],
    });
  }

  nextStep() {
    if (this.form.valid) {
      this.step = 2;
    }
  }

  submit() {
    this.form.markAllAsTouched();

    if (this.form.valid) {
      this.authService.register(this.form.value).subscribe({

        next: () => {

          this.messageService.add({
            severity: 'succes',
            summary: 'Account created',
            detail: 'Your account has been succesfully created'
          });

          setTimeout(() => {
            this.close.emit();
          }, 800);
        },

        error: (err) => {
          console.error('Register error', err);

          this.messageService.add({
            severity: 'error',
            summary: 'Registration failed',
            detail: 'Something went wrong'
          });
        }
      });
    }
  }
}
