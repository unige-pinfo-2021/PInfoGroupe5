import { Injectable } from '@angular/core';
import { Subject } from 'rxjs'; // ERROR import
import { Observable } from 'rxjs';
import {HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { User }  from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public users = []; //User[]

  constructor(private http: HttpClient) { }
  getAllUsers():Observable<User[]>{
  	console.log("testservice")
  	console.log(this.http.get<User[]>("http://tindfilm/user/all"));
    return this.http.get<User[]>("http://tindfilm/user/all");
  }


}
