package api.model;

import java.util.*;

public class GroupService{

	private String groupname;
	
	private DataBaseInvit dbi;
	private DataBaseUGroup dbu;

	public GroupService(){
		//this.groupname = groupname;

		this.dbi = new DataBaseInvit("src/main/resources/database.properties");
		this.dbu = new DataBaseUGroup("src/main/resources/database.properties");

	}//end constructor

	
	/* USERS */   // admin

	public void create_group(String groupname, String username){
		dbu.INSERT(groupname, username);	
	}//end

	public void delete_group(String groupname){
		dbu.DELETE(groupname); 
		//dbi.DELETE(groupname);
	} //end

	public void add_user(String groupname, String username){
		dbu.INSERT(groupname, username);
	}//end 

	public void delete_user(String groupname, String username){
		dbu.DELETE(groupname, username);
	}//end

	public ArrayList<String> get_user_list(String groupname){
		return  dbu.SELECT(groupname, false);
	} //end 


	public int get_n_users(String groupname){
		return  (dbu.SELECT(groupname, false)).size();
	} //end


	/* FILM SELECTION */

	public String get_selected_film(String groupname){
		String film = "Votes are still missing";

		ArrayList<Integer> votes = get_total_scores(groupname);
		int total_vote = 0;
		for(int vote :votes){
			total_vote+=vote;
		}
		
		if(total_vote == 0){
			ArrayList<Integer> scores = get_scores(groupname);
			ArrayList<String> films = get_films(groupname);
			film = films.get(scores.indexOf(Collections.max(scores)));
		}

		return film;
	}//end

	public void create_films(String groupname, ArrayList<String> films){
		//get selection de films = titres

		int n = get_n_users(groupname);

		for(String film: films){
			dbi.INSERT(groupname, film, n);
		}
		
	}//end

	public void update_films(String groupname, ArrayList<Integer> scores){
		ArrayList<String> films = get_films(groupname);
		for(int i=0; i < films.size() ; i++){
			dbi.UPDATE(groupname, films.get(i), scores.get(i));
		}
	}

	public void delete_films(String groupname){
		dbi.DELETE(groupname);
	}//end  

	public ArrayList<String> get_films(String groupname){
		return dbi.SELECT_films(groupname);
	}//end

	public ArrayList<Integer> get_scores(String groupname){
		return dbi.SELECT_scores(groupname, "score");
	}//end	

	public ArrayList<Integer> get_total_scores(String groupname){
		return dbi.SELECT_scores(groupname, "totalscore");
	}//end

}//end class
