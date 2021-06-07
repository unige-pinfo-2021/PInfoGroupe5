import { Component, Inject, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { DOCUMENT } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(
  	public auth: AuthService,
    private router: Router,
	 @Inject(DOCUMENT) private doc: Document
  ) {}

  ngOnInit(): void {}

  onViewJoinGroup(): void {

    // redirect
    this.router.navigate(['/auth']);

  }

  onViewCreateGroup(): void {

    // redirect
    this.router.navigate(['/group']);
    
  }

  loginWithRedirect(): void {
    this.auth.loginWithRedirect();
  }

  signupWithRedirect(): void {
    this.auth.loginWithRedirect({ screen_hint: 'signup' });
	}

  logout(): void {
    this.auth.logout({ returnTo: this.doc.location.origin });
  }
}


