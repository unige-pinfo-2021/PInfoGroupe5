import { Component, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    public auth: AuthService,
    public router:Router
  ) { }
  profileJson: string = null;

  ngOnInit(): void {
    this.auth.user$.subscribe(
      (profile) => (this.profileJson = JSON.stringify(profile, null, 2))
    );
  }

  onViewJoinGroup(): void {

    // redirect
    this.router.navigate(['/auth']);

  }

  onViewCreateGroup(): void {

    // redirect
    this.router.navigate(['/group']);
    
  }

}
