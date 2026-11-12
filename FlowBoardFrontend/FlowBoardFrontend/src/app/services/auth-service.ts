import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap, of, BehaviorSubject, Observable } from 'rxjs';
import { User } from '../interfaces/user';
import { AuthenticationResponse } from '../interfaces/authentication-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser = this.currentUserSubject.asObservable();

  constructor(private http:HttpClient){
    const stored = localStorage.getItem('currentUser');
    if(stored){
      this.currentUserSubject.next(JSON.parse(stored));    
    }
  }

  

  setToken(token: string): void {
    localStorage.setItem('access_token', token);
  }
  
  login(credentials: {username: string; password: string}) {
    const user = {
      username: credentials.username,
      email: credentials.username,
      password: credentials.password,
      
    };

    return this.http.post<AuthenticationResponse>(this.apiUrl + '/login', user).pipe(
      tap(response =>{ 
        
        this.setUser(response.user);
        this.setToken(response.token);
      }));    
  }

  register(credentials: {username: string; email: string; password: string, repeatPassword: string}){
      const user = {
        email: credentials.email,
        username: credentials.username,
        password: credentials.password,
        repeatPassword: credentials.repeatPassword
      }
 
      return this.http.post<AuthenticationResponse>(this.apiUrl + '/register', user).pipe(
      tap(
        response =>{ 
        this.setUser(response.user);
        this.setToken(response.token);
      }));
  }

  logout(): void{
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    localStorage.removeItem('access_token');

  }

  setUser(user: User): void{
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }
  setNewToken(token: string): void{
    localStorage.setItem('access_token', token);
  }

  isAuthenticated(): boolean{
    return this.currentUserSubject.value == null ? false : true;
  }
}
