import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder} from '@angular/forms';
import { Subscription } from 'rxjs'; // ERROR import
import { GroupService } from '../services/group.service';
import { Group }  from '../models/group.model';
import {Router} from '@angular/router';
import { ClipboardService } from 'ngx-clipboard';


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
  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private groupService: GroupService,
    private _clipboardService: ClipboardService
  ) { }

	/*	KeycloakService.auth.authz.loadUserInfo().success(function(data){
		  $scope.userName =  data.name ;
		})
*/

  groupSubscription: Subscription;
  public inviation = this.groupService.createInvitation();

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
    console.log(groupName);
  	let formObj = this.checkoutForm.getRawValue();
  	let userName = this.userName;
    console.log(groupName);
    let dict = {
      "groupeName":groupName,
      "admin":userName,
      "invitation":this.inviation
    };
    let json = JSON.stringify(dict);
    this.groupService.createGroup(json)
    	.subscribe(
      	data => this.groups = data
    );
    this.router.navigate(['/films', groupName]);
  }

/*  copyText(inviation: any ) {
    this.clipboardApi.copy(inviation)
    console.log(inviation)
  }
*/



copyToClipboard(item) {
    document.addEventListener('copy', (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain', (item));
      e.preventDefault();
      document.removeEventListener('copy', null);
    });
    document.execCommand('copy');
  }
}
