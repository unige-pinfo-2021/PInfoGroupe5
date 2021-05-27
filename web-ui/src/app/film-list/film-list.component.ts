import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs'; // ERROR import 
import {Router, ActivatedRoute} from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Film }  from '../models/film.model';
import { FilmService } from '../services/film.service';
import * as $ from 'jquery';
import * as $$ from 'jquery.animate';
import * as AOS from 'aos';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.css']
})
export class FilmListComponent implements OnInit {

  public films = [];
  filmSubscription: Subscription;
  public mobile = false;

  constructor(private router: Router,private route2: ActivatedRoute, private formBuilder: FormBuilder, private filmService: FilmService){}

  ngOnInit(): void {
    this.filmService.getFilms()
    .subscribe(
        data => this.films = data
    );
    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    }
  }
  
  like(film :any) {
    const groupName = this.route2.snapshot.paramMap.get('groupName');
    var filmDiv = ('#'+film).toString();
    var idFilm = film[0];
    var increment = film[1];
    var dict = {"idFilm":idFilm,"increment":increment};
    var json = JSON.stringify(dict);
    console.log(json)
    $(filmDiv).hide();
    this.filmService.scoreFilm(json,groupName)
    .subscribe(
        data => this.films = data
    );
  }

  dislike() {
    alert("Disl")
  }

  onViewFilm(id: number) {
    this.router.navigate(['/films', 'view', id]);
  }

  ngOnDestroy() {
    //this.filmSubscription.unsubscribe();
  }
}//end comp
