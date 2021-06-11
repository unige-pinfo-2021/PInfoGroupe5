import { Component, OnInit,Inject } from '@angular/core';
import { FormControl, FormBuilder} from '@angular/forms';
import { Subscription } from 'rxjs'; // ERROR import
import { GroupService } from '../services/group.service';
import { Router } from '@angular/router';
import { ClipboardService } from 'ngx-clipboard';
import { AuthService } from '@auth0/auth0-angular';
import * as $ from 'jquery';
import { DOCUMENT } from '@angular/common';
import {UserService} from "../services/user.service"


@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  checkoutForm = this.formBuilder.group({
    groupName: '',
  });

  public groups = [];
  public mobile = false;
  public isAdmin = false;
  public groupsInfo = [];
  public info = [];
  public user = null;

  constructor(
    @Inject(DOCUMENT) document,
    private router: Router,
    private formBuilder: FormBuilder,
    private groupService: GroupService,
    private _clipboardService: ClipboardService,
    public auth: AuthService,
    public userService:UserService
  ) { }

  public invitation = this.groupService.createInvitation();

  public getUserName(key: any): any {
    var k = JSON.parse(key);
    return k.name;
  }

  public getUserEmail(key: any): any {
    var k = JSON.parse(key);
    return k.email;
  }

  //random group profile picture
  public picNum = this.userService.getRandomNum()

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

    // manage responsive design
    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    };

  }

  onSubmit(): void {

    // get data from form
  	let groupName = this.checkoutForm.value['groupName'];
    let userName = this.getUserName(this.profileJson);

    // create payload
    let dict = {
      "groupName":groupName,
      "admin":userName,
      "invitation":this.invitation
    };
    let json = JSON.stringify(dict)

    // process new group
    this.groupService
      .createGroup(json)
      .subscribe(data => {

        // create new group
        this.groups.push(data);

        // redirect to film selector
        this.router.navigate(['/films', groupName]);

      });

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

  // view film details
  onViewRecommendation(groupName: any) {

    this.router.navigate(['/recommendation', groupName]);

  }

  // view film details
  onViewScoreMovies(groupName: any) {

    this.router.navigate(['/films', groupName]);

  }

  // get films recommandation
  deleteCatalogue(groupName: any){

    this.auth.user$.subscribe(
      (profile) => {
        (this.profileJson = JSON.stringify(profile, null, 2));

        // get user profile data
        const userName = this.getUserName(this.profileJson);

        this.groupService.deleteCatalogue(groupName,userName);

        this.router.navigate(['/recommendation', groupName]);
      }
    );
  }
  getImage(i:string){

    return (((i[0].charCodeAt(0)+i[1].charCodeAt(0)) % 15 ) + 15 ) % 14;

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

  
}
