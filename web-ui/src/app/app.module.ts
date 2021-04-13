import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
//import { AppRoutingModule } from './app-routing.module';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule } from '@angular/common/http';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FilmListComponent } from './film-list/film-list.component';

import { FilmService} from './services/film.service';
import { SingleFilmComponent } from './film-list/single-film/single-film.component';

const appRoutes: Routes = [
	{path: 'films', component: FilmListComponent},
	{path: 'films/view/:id', component: SingleFilmComponent }	
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FilmListComponent,
    SingleFilmComponent
  ],
  imports: [
    BrowserModule,

    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [FilmService],
  bootstrap: [AppComponent]
})
export class AppModule { }
