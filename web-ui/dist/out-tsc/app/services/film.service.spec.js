import { TestBed } from '@angular/core/testing';
import { FilmService } from './film.service';
describe('FilmService', () => {
    let service;
    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(FilmService);
    });
    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
//# sourceMappingURL=film.service.spec.js.map