import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserModel } from '../models/user.mode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }


  public byId(id: string): Observable<UserModel> {
    return this.http.get<UserModel>(`
      ${environment.url}:${environment.port}/api/user/get/byId/${id}  
    `);
  }

  public create(user: UserModel): Observable<UserModel> {
    return this.http.post<UserModel>(`
      ${environment.url}:${environment.port}/${environment.path}/user/create  
    `, user)
  }
}
