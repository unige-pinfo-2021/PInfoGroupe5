package api.model;

import java.util.*;

public class InvitService{

	private DataBaseInvit db;


	public InvitService(){
		this.db = new DataBaseInvit("src/main/resources/database.properties");
	}//end constructor


	public boolean existInvit(String ID){
		return db.EXIST_Invit(ID);
	}//end 

	
	public void setInvitDB(Invit invit){
		//boolean updt = false; return updt
		this.db.INSERT_Invit(invit.getID(), invit.getfilm(), invit.getScore());
	}//end 

	public Invit getInvitDB(String ID, String film){
		ArrayList<String> params = db.SELECT_Invit(ID, film);
		return new Invit(params);//new (params.get(0));
	}//end 


	public  ArrayList<Invit> getallInvitDB(String ID){
		ArrayList<ArrayList<String>> params = db.SELECT_Invit(ID);

		ArrayList<Invit> invits = new ArrayList();

		for(ArrayList<String> param: params){
			invits.add(new Invit(param));
		}

		return invits;
	}//end 

	public void removeInvitDB(String ID){
		//boolean updt = false; return updt
		this.db.DELETE_Invit(ID);
	}//end 


	public void updateInvitDB(String ID, String film, int score_updt){
		this.db.UPDATE_Invit(ID, film,  score_updt);
	}//end 
	
}//end class
