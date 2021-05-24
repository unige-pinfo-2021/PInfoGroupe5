import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder} from '@angular/forms';
import { Subscription } from 'rxjs'; // ERROR import 
import { GroupService } from '../services/group.service';
import { Group }  from '../models/group.model';
import {Router} from '@angular/router';

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
  public userName = "tom"


  constructor(private router: Router,private formBuilder: FormBuilder,private groupService: GroupService) { }
	
	/*	KeycloakService.auth.authz.loadUserInfo().success(function(data){
		  $scope.userName =  data.name ;
		}) 
*/

  groupSubscription: Subscription;

  ngOnInit(): void {
    this.groupService.getGroups()
    .subscribe(
        data => this.groups = data
    );
    console.log(this.groups);
    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    }
  }

  onSubmit(): void {
  	let groupName = this.checkoutForm.value['groupName'];
  	let formObj = this.checkoutForm.getRawValue();
  	let userName = this.userName;
    let inviation = this.groupService.createInvitation();
    let dict = {
      "groupeName":groupName,
      "admin":userName,
      "invitation":inviation
    };
    let json = JSON.stringify(dict);
    this.groupService.createGroup(json)
    	.subscribe(
      	data => this.groups = data
    );
    this.router.navigate(['/films', groupName]);
  }
}

