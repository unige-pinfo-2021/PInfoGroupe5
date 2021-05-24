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

  constructor(private http: HttpClient){}

  getFilms():Observable<Film[]>{
    return this.http.get<Film[]>("http://tindfilm/film");
  }

  scoreFilm(json:any, groupName: any): Observable<any> {
    let url = 'http://tindfilm/group/'+groupName+'/scores';
    return this.http.post<any>(url,json,httpOptions)
    }
 
  getSingleFilm(id: number){
	this.getFilms()
    .subscribe(
        data => this.films = data
    );
	return this.films[id];
  }
 
}
