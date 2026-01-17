import { Injectable } from '@angular/core';
import { environment } from '../../../environment/enivornment';
import { HttpClient } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { catchError, Observable, throwError } from 'rxjs';

export interface Api {
  state: string;
  response: any;
}

@Injectable({
  providedIn: 'root',
})
export class GeneralService {
  private readonly apiUrl: string = environment.apiUrl;

  constructor(private readonly http: HttpClient, private readonly messageService: MessageService) {}

  // * Apis methods

  // GET
  getApi<Api>(endpoint: string): Observable<Api> {
    return this.http
      .get<Api>(`${this.apiUrl}/${endpoint}`)
      .pipe(catchError((err) => this.handleError(err)));
  }

  // POST
  postApi<Api>(endpoint: string, body: any): Observable<Api> {
    return this.http
      .post<Api>(`${this.apiUrl}/${endpoint}`, body)
      .pipe(catchError((err) => this.handleError(err)));
  }

  // PUT
  putApi<Api>(endpoint: string, body: any): Observable<Api> {
    return this.http
      .put<Api>(`${this.apiUrl}/${endpoint}`, body)
      .pipe(catchError((err) => this.handleError(err)));
  }

  // DELETE (opcional)
  deleteApi<Api>(endpoint: string): Observable<Api> {
    return this.http
      .delete<Api>(`${this.apiUrl}/${endpoint}`)
      .pipe(catchError((err) => this.handleError(err)));
  }

  // Manejo de errores centralizado
  private handleError(err: any): Observable<never> {
    this.ErrorMessage('Unexpected error');
    return throwError(() => err);
  }

  /* ejemplo de uso

    this.apiService.getApi<User>('users/1').subscribe({
    next: (data) => console.log('Usuario:', data),
    error: (err) => console.error('Error capturado en el subscribe', err)
  });

  */

  ErrorMessage(mensaje: string): void {
    this.messageService.add({
      severity: 'error',
      summary: '¡Error!',
      detail: mensaje,
      sticky: true,
    });
  }
}
