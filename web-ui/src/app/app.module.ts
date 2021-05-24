import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
//import { AppRoutingModule } from './app-routing.module';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FilmListComponent } from './film-list/film-list.component';
import { FilmService} from './services/film.service';
import { GroupService} from './services/group.service';
import { SingleFilmComponent } from './film-list/single-film/single-film.component';
import { FooterComponent } from './footer/footer.component';
import { AuthenticationComponent } from './authentication/authentication.component';
import { GroupComponent } from './group/group.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';

/*function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080/auth',
        realm: 'your-realm',
        clientId: 'your-client-id',
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
    });
}

*/
const appRoutes: Routes = [
	{path: 'films', component: FilmListComponent},
  {path: 'films/:groupName', component: FilmListComponent},
	{path: 'films/view/:id', component: SingleFilmComponent },
  {path: 'auth', component: AuthenticationComponent },
  {path: 'group', component: GroupComponent },   
  {path: 'user', component: UserComponent },   
  {path: '', component: HomeComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FilmListComponent,
    SingleFilmComponent,
    FooterComponent,
    AuthenticationComponent,
    GroupComponent,
    HomeComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    
    KeycloakAngularModule,

    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),

  ],
  providers: [
    FilmService,
    GroupService,

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

/*
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },*/