package api.model;

import java.util.*;

import java.io.IOException;

// Mock, replace by service DataBase
public class FilmService{

	private ArrayList<Film> database = new ArrayList();

	public FilmService(){
		/*database.add(new Film("Film01", "Prod0"));
		database.add(new Film("Film1", "Prod1"));
		database.add(new Film("Film2", "Prod2"));*/
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

	// return list de films en fonction de criteres

	public Film rest_call(int type, String title) throws IOException, InterruptedException{
		Rest_Caller rc = new Rest_Caller();
		String result = rc.restCallFilm(type, title);

		result = result.replace("{","");
		result = result.replace("}","");
		result = result.replace(":","");
		result = result.replace(",","");

		String[] splt = result.split("\"");//"[\\:,.]"); 

		ArrayList<String> inflist = new ArrayList(Arrays.asList(splt) );
		Film film =  new Film(inflist);
		//return film.descrpt();
		return film;
	}
	
}//end class
