package api.film;

import java.util.*;

public class FilmService{

	private ArrayList<Film> database = new ArrayList();

	public FilmService(){
		database.add(new Film("Film01", "Prod0"));
		database.add(new Film("Film1", "Prod1"));
		database.add(new Film("Film2", "Prod2"));
	}

	public ArrayList<Film> getall(){
		return this.database;
	}

	public Film getFilm(int id){
		return (this.database).get(id);
	}

	public Film getFilm(String title){
		for(Film film: database){
			if((film.getTitle()).equals(title)){
				return film;
			} 	
		}
		return null;
	}// faire même fonction mais avec producer

	public boolean containsFilm(Film film){
		return this.database.contains(film);
	}

	public boolean addFilm(Film film){
		if(!containsFilm(film)){
			this.database.add(film);
			return true;
		}
		return false;
	}

	public boolean deleteFilm(Film film){
		if(containsFilm(film)){
			this.database.remove(film);
			return true;
		}
		return false;
	}

	
}//end class
