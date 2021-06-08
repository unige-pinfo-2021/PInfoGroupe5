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

  public getUserName(key: any): any {
    var k = JSON.parse(key);
    return k.name;
  }

  saveUser(json:any):Observable<any>{
    return this.http.post<any>("http://tindfilm/user/new",json,httpOptions)
  }

  getUser(userName:any):Observable<any>{
    let url = "http://tindfilm/user/exist/"+userName
    return this.http.get<any>(url);

  }
  getUserBool(userName:any):Observable<boolean>{
    let url = "http://tindfilm/user/exist/"+userName
    return this.http.get<boolean>(url);

  }
  updateUserDB(userName:any,email:any):void{
    let dict = {
      "username":userName,
      "email":email
    }
    let json = JSON.stringify(dict);
    let ObsExistence = this.getUser(userName);
    let existence:boolean = true;
    const existenceSubscrition = ObsExistence
      .subscribe((existence1) =>
        {existence = existence1;
          if(existence) {
          } else {
            this.saveUser(json)
              .subscribe((data) => this.users.push(data)
            )
          }
        }
      )
    }

  getAllUsers():Observable<User[]>{
  	console.log("testservice")
  	console.log(this.http.get<User[]>("http://tindfilm/user/all"));
    return this.http.get<User[]>("http://tindfilm/user/all");
  
  }

  getRandomNum(){
    return Math.floor(Math.random() * 16)  
  }

}
