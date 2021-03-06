import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs'; // ERROR import
import { Router,ActivatedRoute } from '@angular/router';
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
  public filmsId = [];
  public mobile = false;

  constructor(
    private router: Router,
    private filmService: FilmService,
    private groupService: GroupService,
    private userService: UserService,
    private auth: AuthService,
    private route2: ActivatedRoute

  ){}

  public getUserName(key: any): any {
    var k = JSON.parse(key);
    return k.name;
  }

  public getUserEmail(key: any): any {
    var k = JSON.parse(key);
    return k.email;
  }

  profileJson: string = null;


  ngOnInit(): void {

    this.auth.user$.subscribe(
      (profile) => {
        (this.profileJson = JSON.stringify(profile, null, 2));

        // get user profile data
        const userName = this.getUserName(this.profileJson);
        const userEmail = this.getUserEmail(this.profileJson);
        const groupName = this.route2.snapshot.paramMap.get('groupName');

        //check if user in DB
        this.userService.updateUserDB(userName,userEmail);
        //get recommended movies
        this.groupService.createCatalogue(groupName,userName).
        subscribe(
          data =>{
            this.groupService.getCatalogue(groupName,userName)
              .subscribe(
                data =>{
                  this.filmsId = data.catalogue;
                  for (var i = 0; i < this.filmsId.length; i++){
                    this.filmService.getFilm(this.filmsId[i])
                    .subscribe(
                      result => {
                        if(result["poster_path"].charAt(0) == "/"){
                          this.films.push(result);
                        }
                      }
                    )
                  }
                }
            );
          }
      );

      }
    );

    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    };
    //this.deleteCatalogue();
  }


  onViewFilm(id: number) {
    this.router.navigate(['/films', 'view', id]);
  }

}//end comp
