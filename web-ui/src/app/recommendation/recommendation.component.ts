import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs'; // ERROR import 
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Film }  from '../models/film.model';
import { FilmService } from '../services/film.service';
import { UserService } from '../services/user.service';
import * as $ from 'jquery';
import * as $$ from 'jquery.animate';
import * as AOS from 'aos';
import { GroupService } from '../services/group.service';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-recommendation',
  templateUrl: './recommendation.component.html',
  styleUrls: ['./recommendation.component.css']
})
export class RecommendationComponent implements OnInit {

  public films = [];
  public mobile = false;

  constructor(
    private router: Router,
    private filmService: FilmService,
    private groupService: GroupService,
    private userService: UserService,
    private auth: AuthService
  ){}

  ngOnInit(): void {

  	// get films from recommandation tool

    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    };
    
  }
  
  // get films recommandation

  onViewFilm(id: number) {
    this.router.navigate(['/films', 'view', id]);
  }

}//end comp


