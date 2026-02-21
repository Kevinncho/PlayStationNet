import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { AuthService } from './core/services/auth/auth.service';

export const routes: Routes = [
   {
    path: '',
    component: Home
  },
  {
    path: 'home',
    redirectTo: '',
    pathMatch: 'full'
  }
];
