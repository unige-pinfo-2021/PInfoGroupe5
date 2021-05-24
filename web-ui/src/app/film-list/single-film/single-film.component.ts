import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router} from '@angular/router';
import { Film }  from '../../models/film.model';
import { FilmService } from '../../services/film.service';

@Component({
  selector: 'app-single-film',
  templateUrl: './single-film.component.html',
  styleUrls: ['./single-film.component.css']
})
export class SingleFilmComponent implements OnInit {

  public films = [];
  film:any;

  constructor(private route: ActivatedRoute, private router: Router, private filmService: FilmService){ }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.filmService.getSingleFilm(id)
    .subscribe(
        data => this.film = data
    )
  }

   onBack() {
    this.router.navigate(['/films']);
  }

}
