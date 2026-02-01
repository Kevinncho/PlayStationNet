import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-register',
  imports: [
    CommonModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    ReactiveFormsModule,
    CardModule
  ],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  @Output() close = new EventEmitter<void>();

  step = 1;
  form: FormGroup;

  constructor(private fb: FormBuilder) {
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
    console.log(this.form.value);
    // aquí irá el backend
  }
}
}
