import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { authGuard } from './auth/auth-guard';

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
