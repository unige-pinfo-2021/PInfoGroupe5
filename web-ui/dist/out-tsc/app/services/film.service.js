import { __decorate } from "tslib";
import { Injectable } from '@angular/core';
let FilmService = class FilmService {
    constructor(http) {
        this.http = http;
        this.films = []; //Film[]
    }
    getFilms() {
        return this.http.get("assets/data.json");
    }
    getSingleFilm(id) {
        this.getFilms()
            .subscribe(data => this.films = data);
        return this.films[id];
    }
};
FilmService = __decorate([
    Injectable({
        providedIn: 'root'
    })
], FilmService);
export { FilmService };
//# sourceMappingURL=film.service.js.map