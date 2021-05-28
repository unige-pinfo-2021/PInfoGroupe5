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
import { ClipboardModule } from 'ngx-clipboard';
import { AuthModule } from '@auth0/auth0-angular';
import { environment as env } from '../environments/environment';
import { AuthGuard } from '@auth0/auth0-angular';

const appRoutes: Routes = [
	{path: 'films', component: FilmListComponent,canActivate: [AuthGuard]},
  {path: 'films/:groupName', component: FilmListComponent,canActivate: [AuthGuard]},
	{path: 'films/view/:id', component: SingleFilmComponent,canActivate: [AuthGuard]},
  {path: 'auth', component: AuthenticationComponent,canActivate: [AuthGuard]},
  {path: 'group', component: GroupComponent,canActivate: [AuthGuard]},   
  {path: 'user', component: UserComponent,canActivate: [AuthGuard]},   
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
    ClipboardModule,

    AuthModule.forRoot({
      ...env.auth,
    }),


  ],
  providers: [
    FilmService,
    GroupService,

  ],


  bootstrap: [AppComponent]
})
export class AppModule { }

