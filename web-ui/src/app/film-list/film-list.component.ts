import { Component, OnInit } from '@angular/core';
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
  public info = [];
  public groupsInfo = [];
  public mobile = false;
  public list = [];
  public user = null;

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

  public getUserEmail(key: any): any {
    var k = JSON.parse(key);
    return k.email;
  }

  profileJson: string = null;


  ngOnInit(): void {

    //get userName
    this.auth.user$.subscribe(
      (profile) => {
        (this.profileJson = JSON.stringify(profile, null, 2));

        // get user profile data
        const userName = this.getUserName(this.profileJson);
        this.user = userName;
        const userEmail = this.getUserEmail(this.profileJson);

        //check if user in DB
        this.userService.updateUserDB(userName,userEmail);

        //get random films
        this.filmService.getRandomFilms()
        .subscribe(
            data => this.films = data
        );

        //get groups of user
        this.groupService.getUserGroups(userName)
          .subscribe(
            data => {
              this.groups = data;

              //get info of each groups
              for (var i in data){
                this.groupService.getGroupInfo(data[i])
                  .subscribe(
                    data => {
                      this.info = data;
                      this.groupsInfo.push(data);
                    }
                  )
              }
            }
          );
      }
    );

    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    };

  }

  score(film :any) {

    //get param for payload
    const groupName = this.route2.snapshot.paramMap.get('groupName');
    const filmID = film[0];
    const increment = film[1];
    const userName = this.getUserName(this.profileJson);

    // send score
    this.filmService
      .setScore(groupName,userName,filmID,increment)
      .subscribe(
        data => {
          this.films.push(data);
        }
      );

    // hide film div
    var filmDiv = ('#'+film[0]).toString();
    $(filmDiv).hide();

  }

  // view film details
  onViewFilm(id: number) {

    this.router.navigate(['/films', 'view', id]);

  }

  // view film details
  onViewRecommendation(groupName: any) {

    this.router.navigate(['/recommendation', groupName]);

  }

  // view film details
  onViewScoreMovies(groupName: any) {

    this.router.navigate(['/films', groupName]);

  }

  copyToClipboard(item) {
    document.addEventListener('copy', (e: ClipboardEvent) => {

      // parameters
      e.clipboardData.setData('text/plain', (item.toString()));
      e.preventDefault();

      // remove previous copy
      document.removeEventListener('copy', null);
    });

    // copy item
    document.execCommand('copy');
  }

  // give recommendations
  createCatalogue(list: any){

    //parameters
    var groupName = list[0];
    var admin = list[1];
    var userName = this.getUserName(this.profileJson);

    //check if user is admin
    if (admin == userName){
      //create catalogue
      this.groupService
        .createCatalogue(userName,groupName)
        .subscribe(data => this.films.push(data));
    }

    //route to recommendation
    //this.router.navigate(['/recommendation', groupName]);
  }

  onClickDeleteGroup(groupName: any){
    const userName = this.getUserName(this.profileJson);

    //delete group
    this.groupService.deleteGroup(groupName,userName)
      .subscribe(data => {
        data;
        window.location.reload()
      });

  }


}//end comp
