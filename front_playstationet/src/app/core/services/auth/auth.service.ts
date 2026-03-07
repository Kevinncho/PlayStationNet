import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { jwtDecode } from "jwt-decode";

interface LoginResponse {
  token: string;
}

interface JwtPayload {
  sub: string;
  roles: string[];
  exp: number;
  iat: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'token';
  private readonly ROLES_KEY = 'roles';

  private apiUrl = 'http://localhost:8080';

  constructor(private readonly http: HttpClient) {}

  login(username: string, password: string): Observable<LoginResponse> {

    return this.http.post<LoginResponse>('http://localhost:8080/auth/login', {
      username,
      password
    }).pipe(
      tap(res => this.setSession(res.token))
    );
  }

  private setSession(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);

    const decoded = jwtDecode<JwtPayload>(token);
    localStorage.setItem(this.ROLES_KEY, JSON.stringify(decoded.roles));
  }

  isLogged(): boolean {
    return !!localStorage.getItem(this.TOKEN_KEY);
  }

  getToken():string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getRoles(): string[] {
    const roles = localStorage.getItem(this.ROLES_KEY);
    return roles ? JSON.parse(roles) : [];
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

  logout(): void{
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.ROLES_KEY);
  }

  register (data: any) {
    return this.http.post(`${this.apiUrl}/auth/register`, data);
  }
}
