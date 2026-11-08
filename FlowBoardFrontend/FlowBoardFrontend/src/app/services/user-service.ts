import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users'
  constructor(private http:HttpClient){}

  updatePassword(oldPassword: string, newPassword: string, userId: number): Observable<string>{
      const changePasswordRequest = {currentPassword: oldPassword,newPassword}
      
      return this.http.patch(this.apiUrl + '/changePassword/' + userId, changePasswordRequest, {responseType: 'text'});
  }

  updateUserData(email: string, username: string, userId: number): Observable<User>{
    const userData = {email, username, userId}
      return this.http.patch<User>(this.apiUrl + '/updateUser/' + userId, userData);
  }

  deleteAccount(userId: number): Observable<string>{
      return this.http.delete<string>(this.apiUrl + '/' + userId);
  }
}
