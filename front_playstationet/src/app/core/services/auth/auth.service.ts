import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { Role } from "../../models/role.enum";

interface LoginResponse {
  token: string;
  role: number
}
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'token';
  private readonly ROLE_KEY = 'role';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<LoginResponse> {

    return this.http.post<LoginResponse>('http://localhost:8080/auth/login', {
      email,
      password
    }).pipe(
      tap(res => this.setSession(res))
    );
  }

  private setSession(auth: LoginResponse): void {
    localStorage.setItem(this.TOKEN_KEY, auth.token);
    localStorage.setItem(this.ROLE_KEY, auth.role.toString());
  }

  isLogged(): boolean {
    return !!localStorage.getItem(this.TOKEN_KEY);
  }

  getToken():string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getRole(): Role | null {
    const role = localStorage.getItem(this.ROLE_KEY);
    return role ? Number(role) as Role : null;
  }

  hasRole(role: Role[]): boolean {
    const userRole = this.getRole();
    return userRole !== null && role.includes(userRole);
  }

  logout(): void{
    localStorage.clear();
  }
}
