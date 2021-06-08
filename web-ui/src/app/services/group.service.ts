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

	private handleError<T>(operation = 'operation', result? : T) {
	  return (error: any): Observable<T> => {
      return of(result);
	  };
	}

  constructor(
    private http: HttpClient
  ) { }

   // generate invitation code
  createInvitation(){
      // I generate the UID from two parts here
      // to ensure the random number provide enough bits.
      var firstPart = (Math.random() * 46656) | 0;
      var secondPart = (Math.random() * 46656) | 0;
      var firstPartId = ("000" + firstPart.toString(36)).slice(-3);
      var secondPartID = ("000" + secondPart.toString(36)).slice(-3);
      return firstPartId + secondPartID;
  }

  // create new group
  createGroup(group: any): Observable<any> {
    return this.http.post<any>('http://tindfilm/group/create', group, httpOptions)
      .pipe(
        catchError(this.handleError('addGroup', group))
      );
  }

  //get user's groups if not admin
  getUserGroups(userName: string):Observable<any>{
    const url = "http://tindfilm/group/"+userName+"/groups"
    return this.http.get<any>(url);
  }

  // delete
  // efface un groupe
 deleteGroup(groupName : string, administrateur: string){
    let url = "http://tindfilm/group/" + groupName + "/" + administrateur;
    return this.http.delete(url)
      .pipe(
        catchError(this.handleError('deleteGroup', ))
      );

    ;
  }

  // efface l'utilisateur d'un groupe
  deleteUserGroup(userName : string , groupName : string, administrateur:string){
    let url = "http://tindfilm/group/" + groupName +"/" + userName + "/" + administrateur;
    return this.http.delete(url);
  }

  //efface le catalogue du groupe
  deleteCatalogue(groupName : string, administrateur:string){
    let url = "http://tindfilm/group/" + groupName + "/" + administrateur +"/Catalogue";
    return this.http.delete(url);
  }

  // obtenir info du groupe
  getGroupInfo(groupName :string){
    let url = "http://tindfilm/group/"+groupName;
    return this.http.get<any>(url);
  }

  // obtenir la liste des utilisateurs d'un groupe
  getUsersGroup(groupName: string) {
    const url = "http://tindfilm/group/"+ groupName + "/users";
    return this.http.get<any>(url);
  }

  // obtenir la liste des utilisateurs de chaque groupe
  getUsersGroupAll(){
    return this.http.get<any>("http://tindfilm/group/all/users");
  }

  // obtenir la liste des id de film du catalogue
  getCatalogue(groupName: string, userName: string) {
    const url = "http://tindfilm/group/"+groupName+"/"+userName+"/Catalogue";
    return this.http.get<any>(url);
  }

  // obtenir les scores des films
  getScore(groupName: string) {
    const url = "http://tindfilm/group/" + groupName + "/scores";
    return this.http.get<any>(url);
  }

  // ajoute un utilisateur
  addUser(userName : string, invitation :string ) {
    const url = "http://tindfilm/group/users";
    const requete = { "userName" : userName, "invitation": invitation};
    return this.http.post<any>(url,requete,httpOptions);
  }

  // créer un catalogue au hasard
  createRandomCatalogue(groupName : string, admin :string){
    const url = "http://tindfilm/group/"+groupName+"/newCatalogue";
    const requete = {"type": "random", "admin": admin};
    return this.http.post<any>(url, requete ,httpOptions);
  }

  // créer un catalogue selon des recommandations
  createCatalogue(groupName : string, admin :string){
    const url = "http://tindfilm/group/"+groupName+"/newCatalogue";
    const requete = {"type": "", "admin": admin};
    return this.http.post<any>(url, requete ,httpOptions);
  }


  // retourne les votes du groupe sous le format suivant:
  //{ userName : {idFilm1 : +1, idFilm2 : -1, etc}, userName2 : {etc..}, etc...}
  getVote(groupName : string){
    const url = "http://tindfilm/group/"+groupName+"/votes";
    return this.http.get<any>(url);

  }
  // compute vote ratio
















  // delete
  // efface un groupe
/*  deleteGroup(groupName : string, admin: string)
  {
    let url = "http://tindfilm/group/" + groupName;
    let requeteJson = { admin: admin};
    return this.http.delete(url,JSON.stringify(requeteJson))
      .pipe(
        catchError(this.handleError('addGroup', ))
      );

    ;
  }

  // efface l'utilisateur d'un groupe
  deleteUserGroup(userName : string , groupName : string, admin:string)
  {
    let url = "http://tindfilm/group/" + groupName +"/" + userName;
    let requeteJson = { admin: admin};
    return this.http.delete(url,JSON.stringify(requeteJson));
  }

  //efface le catalogue du groupe
  deleteCatalogue(groupName : string, admin:string)
  {
    let url = "http://tindfilm/group/" + groupName +"/Catalogue";
    let requeteJson = { admin: admin};
    return this.http.delete(url,JSON.stringify(requeteJson));
  }*/
}
