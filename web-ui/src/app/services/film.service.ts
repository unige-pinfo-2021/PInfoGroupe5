import { Injectable } from '@angular/core';

import { Subject } from 'rxjs'; // ERROR import
import { Observable } from 'rxjs';
import {HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';

import { Film }  from '../models/film.model';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  public films = []; //Film[]
  
  constructor(private http: HttpClient){}

  getFilms():Observable<Film[]>{
    let headers = {'Access-Control-Allow-Origin':'*'}
    return this.http.get<Film[]>("http://tindfilm/film",{headers});
  }

  
  /*DOC */
  /*http://tindfilm/film*/
  /*https://angular.io/guide/http*/


  
  getSingleFilm(id: number){
	this.getFilms()
    .subscribe(
        data => this.films = data
    );
  console.log(this.films);
	return this.films[id];
  }
 
}
