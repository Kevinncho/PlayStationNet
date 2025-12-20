import { Component, signal } from '@angular/core';
// import { RouterOutlet } from '@angular/router';
import { Home } from './pages/home/home';
import { Login } from './pages/login/login';
import { Sidebar } from './layout/sidebar/sidebar';
import { Navbar } from './layout/navbar/navbar';

@Component({
  selector: 'app-root',
  imports: [Home, Login, Sidebar, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('playstationet');
  showSidebar = false;
  selectedApp = 'home';
  isLoggedIn = false;

  onLoginSuccess() {
    this.isLoggedIn = true;
  }
  logout() {
    this.isLoggedIn = false;
  }
  changeApp(app: string) {
    this.selectedApp = app;
  }
}
