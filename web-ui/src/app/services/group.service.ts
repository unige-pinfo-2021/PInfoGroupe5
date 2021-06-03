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

  constructor(
    private http: HttpClient
  ) { }

	//get all groups
  getGroups():Observable<Group[]>{
  // WORKS FINE
    console.log(this.http.get<Group[]>("assets/groupData.json"));
    return this.http.get<Group[]>("assets/groupData.json");
  }

  //get all groups
  getGroupInvitation(json:any):Observable<Group[]>{
    console.log(this.http.get<Group[]>("assets/groupData.json"));
    return this.http.get<Group[]>("assets/groupData.json");
  }

  createGroup(group: any): Observable<any> {
  // WORKS FINE
    return this.http.post<any>('http://tindfilm/group/create', group, httpOptions)
      .pipe(
        catchError(this.handleError('addGroup', group))
      );
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

  /*
  GET: /group/{userName}/groups
  retourne la liste des groupes de l'utilisateur
  */
  //get all groups
  getUserGroups(userName: string):Observable<Group[]>{
    let url = "http://tindfilm/group/"+userName+"/groups"
/*    console.log(this.http.get<Group[]>(url));
*/  return this.http.get<Group[]>(url);
  }

  // delete 
  // efface un groupe
  deleteGroup(groupName : string, administrateur: string)
  {
    let url = "http://tindfilm/group/" + groupName;
    let requeteJson = { admin: administrateur};
    return this.http.delete(url,JSON.stringify(requeteJson));
  }

  // efface l'utilisateur d'un groupe
  deleteUserGroup(userName : string , groupName : string, administrateur:string)
  {
    let url = "http://tindfilm/group/" + groupName +"/" + userName;
    let requeteJson = { admin: administrateur};
    return this.http.delete(url,JSON.stringify(requeteJson)); 
  }

  //efface le catalogue du groupe
  deleteCatalogue(groupName : string, administrateur:string)
  {
    let url = "http://tindfilm/group/" + groupName +"/Catalogue";
    let requeteJson = { admin: administrateur};
    return this.http.delete(url,JSON.stringify(requeteJson)); 
  }

  // obtenir info du groupe
  getGroupInfo(groupName :string)
  {
    let url = "http://tindfilm/group/"+groupName;
    return this.http.get(url);
  }

  // obtenir la liste des utilisateurs d'un groupe
  getUsersGroup(groupName: string)
  {
    let url = "http://tindfilm/group/"+ groupName + "/users";
    return this.http.get(url);
  }

  // obtenir la liste des utilisateurs de chaque groupe
  getUsersGroupAll()
  {
    return this.http.get("http://tindfilm/group/all/users");
  }

  // obtenir la liste des id de film du catalogue
  getCatalogue(groupName: string, userName: string)
  {
    let url = "http://tindfilm/group/"+groupName+"/"+userName;
    return this.http.get(url);
  }

  // obtenir les scores des films
  getScore(groupName: string)
  {
    let url = "http://tindfilm/group/" + groupName + "/scores";
    return this.http.get(url);
  }

  // ajoute un utilisateur
  addUser(userName : string, invitation :string )
  {
    let url = "http://tindfilm/group/users";
    let requete = { userName : userName, invitation: invitation};
    return this.http.post(url,requete,httpOptions);
  }

  // créer un catalogue au hasard
  createRandomCatalogue(groupName : string, administrateur :string)
  {
    let url = "http://tindfilm/group/"+groupName+"/newCatalogue";
    let requete = {type: "random", admin: administrateur};
    return this.http.post(url, requete ,httpOptions);
  }

  // créer un catalogue selon des recommandations
  createCatalogue(groupName : string, administrateur :string)
  {
    let url = "http://tindfilm/group/"+groupName+"/newCatalogue";
    let requete = {type: "", admin: administrateur};
    return this.http.post(url, requete ,httpOptions);
  }

  // modifie les scores des films
  setScore(groupName: string, userName: string, filmID : number , increment: boolean)
  {
    let url = "http://tindfilm/group/"+groupName+"/scores";
    let requete = {userName: userName , idFilm: filmID, increment: increment};
    return this.http.post(url,requete,httpOptions)
  }
  

  // retourne les votes du groupe sous le format suivant:
  //{ userName : {idFilm1 : +1, idFilm2 : -1, etc}, userName2 : {etc..}, etc...}
  getVote(groupName : string)
  {
    let url = "http://tindfilm/group/"+groupName+"/votes";
    return this.http.get(url);

  }
}
