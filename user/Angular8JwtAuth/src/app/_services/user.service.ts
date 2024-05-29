import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../user/user.model';

const API_URL = 'http://localhost:8099/api/test/';



@Injectable({
  providedIn: 'root'
})
export class UserService {

  apiUrl = 'http://localhost:8099/user2'; // Replace with your Spring Boot API URL

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl+"/retrieve");
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl+"/retrieve");
  }

  retrieveUser(id: String): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  updateUser(id: String,user: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/Update/${id}`, user);
  }


  



  deleteUser(id: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/DeleteUser/${id}`, { responseType: 'text' });

  }

}