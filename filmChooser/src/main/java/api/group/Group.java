package api.group;

import java.util.*;

public class Group{

	private String name;
	private ArrayList<String> users = new ArrayList();
	private ArrayList<String> selectFilms = new ArrayList();

	public Group(){}

	public Group(String name, ArrayList<String> users){
		this.name = name;
		this.users = users;
	}

	// getters, setters
	public String getName(){
		return this.name;
	}

	public ArrayList<String> getUsers(){
		return this.users;
	}

	public ArrayList<String> getFilmSelection(){
		return selectFilms;
	}

	// Gestion du groupe: ajouter ou supprimer un membre
	public boolean addUser(String username){
		if(!this.users.contains(username)){
			this.users.add(username);
			return true;
		}
		return false;
	}

	public boolean deleteUser(String username){
		if(this.users.contains(username)){
			this.users.remove(username);
			return true;
		}
		return false;
	}

	// creer une nouvelle selection de films pour le groupe

}//end class
