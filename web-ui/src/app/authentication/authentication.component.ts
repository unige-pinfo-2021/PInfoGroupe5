import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder} from '@angular/forms';

import {Router} from '@angular/router';
import { GroupService } from '../services/group.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})

export class AuthenticationComponent implements OnInit {
checkoutForm = this.formBuilder.group({
	userName: '',
	invitation:''
});
public mobile = false;
public group = [];

  constructor(

    private router: Router,
    private formBuilder: FormBuilder,
    private groupService: GroupService

  	) {}


  ngOnInit(): void {

    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    }
  }

  onSubmit(): void {
  	let invitation = this.checkoutForm.value['invitation'];
  	let userName = this.checkoutForm.value['userName'];

/*  	let formObj = this.checkoutForm.getRawValue();
  	let userName = this.userName;
    let dict = {
      "groupeName":groupName,
      "admin":userName,
      "invitation":this.inviation
    };
    let json = JSON.stringify(dict);
    this.groupService.createGroup(json)
    	.subscribe(
      	data => this.groups = data
    );*/
    let dict = {
      "userName":userName,
      "invitation":invitation
    };
    let json = JSON.stringify(dict);
    this.groupService.getUserGroups(json)
    	.subscribe(
      	data => this.group = data
    );
    //get groupName in list
    let groupName = "groupName"
    this.router.navigate(['/films', groupName]);
  }

}
