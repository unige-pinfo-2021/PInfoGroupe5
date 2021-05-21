package api.film;

import java.util.*;
import java.net.*;
import java.io.*;

public class FilmService{

	private ArrayList<Film> database = new ArrayList();

	public FilmService(){
		database.add(new Film("Film01", "Prod0"));
		database.add(new Film("Film1", "Prod1"));
		database.add(new Film("Film2", "Prod2"));
		
		System.out.println("un service film est initialisé");
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
	
	public void run()
	{
		try{
		ServerSocket serverSocket = new ServerSocket(2000);
		Socket clientSocket = serverSocket.accept();
		
		PrintWriter out;
    		BufferedReader in;
		
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		out.println("Serveur films");
		
		in.close();
		out.close();

		clientSocket.close();
		}
		catch(IOException ioEx)
        	{
       	}
	}

	
}//end class
