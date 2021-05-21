import { __decorate } from "tslib";
import { Component } from '@angular/core';
let FilmListComponent = class FilmListComponent {
    constructor(router, formBuilder, filmService) {
        this.router = router;
        this.formBuilder = formBuilder;
        this.filmService = filmService;
        this.films = [];
    }
    ngOnInit() {
        this.filmService.getFilms()
            .subscribe(data => this.films = data);
    }
    onViewFilm(id) {
        this.router.navigate(['/films', 'view', id]);
    }
    ngOnDestroy() {
        //this.filmSubscription.unsubscribe();
    }
}; //end comp
FilmListComponent = __decorate([
    Component({
        selector: 'app-film-list',
        templateUrl: './film-list.component.html',
        styleUrls: ['./film-list.component.css']
    })
], FilmListComponent);
export { FilmListComponent };
//# sourceMappingURL=film-list.component.js.map