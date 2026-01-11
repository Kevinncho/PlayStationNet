import { Injectable } from "@angular/core";
import { Observable, of, throwError } from "rxjs";
import { delay, tap } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  login(email: string, password: string): Observable<any> {

    //Simulación backend
    if (email === 'admin@gamestore.com' && password === '1234') {
      return of({
        token: 'FAKE_JWT_TOKEN_ABC123',
        user: {
          email,
          role: 'ADMIN'
        }
      }).pipe(
        delay(100),
        tap(response => {
          localStorage.setItem('token', response.token);
        })
      );
    }

    return throwError(() => new Error('Invalid credentials'));
  }

  logout(): void{
    localStorage.removeItem('token');
  }

  isLogged(): boolean {
    return !!localStorage.getItem('token');