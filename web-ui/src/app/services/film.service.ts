import { Injectable,ErrorHandler } from '@angular/core';
import { Subject } from 'rxjs'; // ERROR import
import { Observable, throwError, of} from 'rxjs';
import {HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Film }  from '../models/film.model';
import { catchError, retry } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  public films = []; //Film[]
  
  private handleError<T>(operation = 'operation', result? : T) {
    return (error: any): Observable<T> => {

    reuslt: 1234
    return of(result);
    };
  }

  constructor(
    private http: HttpClient
  ){}

  //get random film by id
  getRandomFilms():Observable<Film[]>{
    return this.http.get<Film[]>("http://tindfilm/film");
  }

  //score random film
  scoreFilm(score:any, groupName: any): Observable<any> {
    let url = 'http://tindfilm/group/'+groupName+'/scores';
    return this.http.post<any>(url,score,httpOptions)
      .pipe(
        catchError(this.handleError('addScores', score))
      );
  }

  // get details of films
  getSingleFilm(id: number){
	  this.getRandomFilms()
      .subscribe(
          data => this.films = data
      );
	  return this.films[id];
  }

  // permet d'obtenir toutes les infos sur un film
  // à partir de son id.
  getFilm(id: number) {
    let url = "http://tindfilm/film/get/" + id.toString();
    return this.http.get(url);
  }
 
}
