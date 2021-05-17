import { __decorate } from "tslib";
import { Component } from '@angular/core';
let SingleFilmComponent = class SingleFilmComponent {
    constructor(route, router, filmService) {
        this.route = route;
        this.router = router;
        this.filmService = filmService;
        this.films = [];
    }
    ngOnInit() {
        const id = this.route.snapshot.params['id'];
        this.filmService.getSingleFilm(id)
            .subscribe(data => this.film = data);
        //console.log(this.film.title);
    }
    onBack() {
        this.router.navigate(['/films']);
    }
};
SingleFilmComponent = __decorate([
    Component({
        selector: 'app-single-film',
        templateUrl: './single-film.component.html',
        styleUrls: ['./single-film.component.css']
    })
], SingleFilmComponent);
export { SingleFilmComponent };
//# sourceMappingURL=single-film.component.js.map