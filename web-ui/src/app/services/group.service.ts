import { Injectable, ErrorHandler} from '@angular/core';

import { Group }  from '../models/group.model';
import { Observable, throwError, of} from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import {HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};


@Injectable({
  providedIn: 'root'
})
export class GroupService {
	public errr = "error"
	private handleError<T>(operation = 'operation', result? : T) {
	  return (error: any): Observable<T> => {
      return of(result);
	  };
	}

  constructor(private http: HttpClient) { }

	//get all groups
  getGroups():Observable<Group[]>{
    console.log(this.http.get<Group[]>("assets/groupData.json"));
    return this.http.get<Group[]>("assets/groupData.json");
  }

  // create group
  // url > /create
  // data > {groupeName,admin,invitation}
	createGroup(json:any): Observable<any> {
      console.log(this.http.post<any>('http://tindfilm/group/create', json, httpOptions))
	    return this.http.post<any>('http://tindfilm/group/create', json, httpOptions)
/*	    .pipe(
	      catchError(this.handleError('createGroupError'))
	    );*/
    }

   // create invitation
  createInvitation(){
      // I generate the UID from two parts here 
      // to ensure the random number provide enough bits.
      var firstPart = (Math.random() * 46656) | 0;
      var secondPart = (Math.random() * 46656) | 0;
      var firstPartId = ("000" + firstPart.toString(36)).slice(-3);
      var secondPartID = ("000" + secondPart.toString(36)).slice(-3);
      return firstPartId + secondPartID;
  }


  



  // delete 


  // url > /{groupname}
  // data > {admin}




  // add user



  // url > /{groupname}/users
  // data > {userName,invitation}

}
