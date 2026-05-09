import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, throwError } from "rxjs";
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

export interface UserProfile {
  id?: number;
  username: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
  createdAt?: string;
  isAdmin?: boolean;
  roles?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'token';
  private readonly ROLES_KEY = 'roles';
  private readonly currentUsernameSubject = new BehaviorSubject<string | null>(null);
  private readonly isLoggedInSubject = new BehaviorSubject<boolean>(false);

  private apiUrl = 'http://localhost:8080';
  readonly currentUsername$ = this.currentUsernameSubject.asObservable();
  readonly isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor(private readonly http: HttpClient) {
    this.syncAuthState();
  }

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
    this.syncAuthState();
  }

  isLogged(): boolean {
    return !!this.getValidPayload();
  }

  getToken():string | null {
    const token = localStorage.getItem(this.TOKEN_KEY);
    if (!token) return null;

    return this.getValidPayload() ? token : null;
  }

  getRoles(): string[] {
    if (!this.getValidPayload()) {
      return [];
    }

    const roles = localStorage.getItem(this.ROLES_KEY);
    return roles ? JSON.parse(roles) : [];
  }

  getCurrentUsername(): string | null {
    return this.getValidPayload()?.sub ?? null;
  }

  getMyUser(): Observable<UserProfile> {
    const token = this.getToken();
    if (!token) {
      return throwError(() => new Error('No authentication token found'));
    }

    return this.http.get<UserProfile>(`${this.apiUrl}/users/me`, {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    });
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

  logout(): void{
    this.clearSession();
    this.syncAuthState();
  }

  register(data: any): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/register`, data).pipe(
      tap((res) => this.setSession(res.token))
    );
  }

  private syncAuthState(): void {
    const payload = this.getValidPayload();
    this.currentUsernameSubject.next(payload?.sub ?? null);
    this.isLoggedInSubject.next(!!payload);
  }

  private getValidPayload(): JwtPayload | null {
    const token = localStorage.getItem(this.TOKEN_KEY);
    if (!token) return null;

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      const nowInSeconds = Math.floor(Date.now() / 1000);

      if (!decoded.exp || decoded.exp <= nowInSeconds) {
        this.clearSession();
        return null;
      }

      return decoded;
    } catch {
      this.clearSession();
      return null;
    }
  }

  private clearSession(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.ROLES_KEY);
  }
}
