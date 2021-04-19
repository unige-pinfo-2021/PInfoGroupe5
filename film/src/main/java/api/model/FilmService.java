package api.model;

import java.util.*;

import java.io.IOException;

// Mock, replace by service DataBase
public class FilmService{

	private ArrayList<Film> selection = new ArrayList();

	public FilmService(){}

	public ArrayList<Film> getall(){
		return this.selection;
	}


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


	public boolean containsFilm(Film film){
		return this.selection.contains(film);
	}

	public boolean addFilm(Film film){
		if(!containsFilm(film)){
			this.selection.add(film);
			return true;
		}
		return false;
	}

	public boolean deleteFilm(Film film){
		if(containsFilm(film)){
			this.selection.remove(film);
			return true;
		}
		return false;
	}

	// return list de films en fonction de criteres
	
}//end class
