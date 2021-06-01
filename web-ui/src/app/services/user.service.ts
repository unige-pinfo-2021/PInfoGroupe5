import { Injectable } from '@angular/core';
import { Subject } from 'rxjs'; // ERROR import
import { Observable } from 'rxjs';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { User }  from '../models/user.model';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public users = []; //User[]

  constructor(
  	private http: HttpClient
  ) { }

  saveUser(json:any):Observable<any>{
  	return this.http.post<any>("http://tindfilm/user/new",json,httpOptions)
  }

  getUser(userName:any):Observable<any>{
  	let url = "http://tindfilm/user/exist/"+userName
    return this.http.get<any>(url);
  
  }

  getAllUsers():Observable<User[]>{
  	console.log("testservice")
  	console.log(this.http.get<User[]>("http://tindfilm/user/all"));
    return this.http.get<User[]>("http://tindfilm/user/all");
  
  }

}
