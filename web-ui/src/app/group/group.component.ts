import { Component, OnInit,Inject } from '@angular/core';
import { FormControl, FormBuilder} from '@angular/forms';
import { Subscription } from 'rxjs'; // ERROR import 
import { GroupService } from '../services/group.service';
import { Router } from '@angular/router';
import { ClipboardService } from 'ngx-clipboard';
import { AuthService } from '@auth0/auth0-angular';
import * as $ from 'jquery';
import { DOCUMENT } from '@angular/common'; 

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

  constructor(
    @Inject(DOCUMENT) document,
    private router: Router,
    private formBuilder: FormBuilder,
    private groupService: GroupService,
    private _clipboardService: ClipboardService,
    public auth: AuthService,
  ) { }
	
  public invitation = this.groupService.createInvitation();

  public getUserName(key: any): any {
    var k = JSON.parse(key);
    return k.name;
  }

  profileJson: string = null;

  ngOnInit(): void {
    this.groupService.getGroups()
    .subscribe(
        data => this.groups = data
    );
    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    };
    this.auth.user$.subscribe(
      (profile) => (this.profileJson = JSON.stringify(profile, null, 2))
    );

  }

  onSubmit(): void {

  	let groupName = this.checkoutForm.value['groupName'];   
    let userName = this.getUserName(this.profileJson);

    let dict = {
      "groupName":groupName,
      "admin":userName,
      "invitation":this.invitation
    };

    let json = JSON.stringify(dict)

    this.groupService
      .createGroup(json)
      .subscribe(data => this.groups.push(data));

    this.router.navigate(['/films', groupName]);

  }

  copyToClipboard(item) {
    document.addEventListener('copy', (e: ClipboardEvent) => {
      
      e.clipboardData.setData('text/plain', (item.toString()));
      e.preventDefault();

      document.removeEventListener('copy', null);
    });

    document.execCommand('copy');
  }
}

