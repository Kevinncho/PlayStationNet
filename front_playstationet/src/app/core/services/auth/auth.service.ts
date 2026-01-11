import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Api } from '../general/general.service';
// import { User } from '../models/user.model';

export interface User {
  name: string;
  password: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly endpoint = 'users';

  constructor(private readonly http: HttpClient) {}

  createUser(user: User): Observable<Api<User>> {
    return this.http.post<Api<User>>(`${this.apiUrl}/${this.endpoint}`, user);
  }
}
