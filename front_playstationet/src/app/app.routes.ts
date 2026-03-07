import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { UserData } from './pages/user-data/user-data';

export const routes: Routes = [
   {
    path: '',
    component: Home
  },
  {
    path: 'user-data',
    component: UserData
  },
  {
    path: 'home',
    redirectTo: '',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: ''
  }
];
