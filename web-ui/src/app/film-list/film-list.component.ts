import { Component, OnInit } from '@angular/core';

import { Subscription } from 'rxjs'; // ERROR import 
import {Router} from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Film }  from '../models/film.model';
import { FilmService } from '../services/film.service';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.css']
})
export class FilmListComponent implements OnInit {

  public films = [];
  filmSubscription: Subscription;

  constructor(private router: Router, private formBuilder: FormBuilder, private filmService: FilmService){}

  ngOnInit(): void {
    this.filmService.getFilms()
    .subscribe(
        data => this.films = data
    );
  }


    onViewFilm(id: number) {
         this.router.navigate(['/films', 'view', id]);
  }


   ngOnDestroy() {
    //this.filmSubscription.unsubscribe();
  }


}//end comp
