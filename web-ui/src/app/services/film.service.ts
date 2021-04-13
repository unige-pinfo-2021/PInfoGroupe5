import { Injectable } from '@angular/core';

import { Subject } from 'rxjs'; // ERROR import
import { Observable } from 'rxjs';
import {HttpClient, HttpClientModule } from '@angular/common/http';

import { Film }  from '../models/film.model';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  public films = []; //Film[]
  
  constructor(private http: HttpClient){}

  getFilms():Observable<Film[]>{
        return this.http.get<Film[]>("assets/data.json");
  }
  
  getSingleFilm(id: number){
	this.getFilms()
    .subscribe(
        data => this.films = data
    );
	return this.films[id];
  }
 
}
