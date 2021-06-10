import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import { GroupService } from '../services/group.service';
import { AuthService } from '@auth0/auth0-angular';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})

export class AuthenticationComponent implements OnInit {
  checkoutForm = this.formBuilder.group({
  	invitation:''
  });

  public mobile = false;
  public films = [];
  public group = [];

  constructor(

    private router: Router,
    private formBuilder: FormBuilder,
    private groupService: GroupService,
    private auth: AuthService,
    private route2: ActivatedRoute,
    private userService: UserService,

  	) {}

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

        //check if user in DB
        this.userService.updateUserDB(userName,userEmail);

      }
    );   

    if (window.screen.width <= 390) { // 768px portrait
      this.mobile = true;
    };
    
  }

onSubmit(): void {

    const invitation = this.checkoutForm.value['invitation'];
    const userName = this.getUserName(this.profileJson);

    this.groupService
      .addUser(userName, invitation)
      .subscribe(data => {
        
        // add user to group
        this.group.push(data);
        
        //redirect to film scoring page        
        if(data["reussit"]){          
          this.router.navigate(['/films', data["groupName"]]);
        }

    });

  }


}
