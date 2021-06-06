import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs'; // ERROR import 
import { Router, ActivatedRoute } from '@angular/router';
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
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.css']
})
export class FilmListComponent implements OnInit {

  public films = [];
  public groups = [];
  filmSubscription: Subscription;
  public mobile = false;

  constructor(
    private router: Router,
    private route2: ActivatedRoute, 
    private formBuilder: FormBuilder, 
    private filmService: FilmService,
    private groupService: GroupService,
    private userService: UserService,
    private auth: AuthService
  ){}

  public getUserName(key: any): any {
    var k = JSON.parse(key);
    return k.name;
  }

  profileJson: string = null;

  ngOnInit(): void {

    this.filmService.getFilms()
    .subscribe(
        data => this.films = data
    );
    
    //get userName
    this.auth.user$.subscribe(
      (profile) => {
        (this.profileJson = JSON.stringify(profile, null, 2));
        const userName = this.getUserName(this.profileJson);

        //get groups of user
        this.groupService.getUserGroups("tom")
          .subscribe(
            data => this.groups = data
        );
      }
    );   

    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    };
    
  }
  
  score(film :any) {
    //get data for payload
    var groupName = this.route2.snapshot.paramMap.get('groupName');
    var idFilm = film[0];
    var increment = film[1];

    // make rest payload
    var dict = {
      "idFilm":idFilm,
      "increment":increment
    };
    var json = JSON.stringify(dict);

    // send like to backend
    this.filmService
      .scoreFilm(json,groupName)
      .subscribe(data => this.films.push(data));

    // hide film div
    var filmDiv = ('#'+film[0]).toString();
    $(filmDiv).hide();

  }

  onViewFilm(id: number) {
    this.router.navigate(['/films', 'view', id]);
  }

  onViewRecommendation(groupName: any){
    var groupN = groupName
    console.log(groupN)
/*    this.router.navigateByUrl('/recommendation/'+"groupName");
*/
    this.router.navigate(['/recommendation', groupN]);
  }

  ngOnDestroy() {
    //this.filmSubscription.unsubscribe();
  }
}//end comp
