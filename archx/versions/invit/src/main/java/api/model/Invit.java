package api.model;

import java.util.*;

public class Invit{	
	
	private String ID;
	private String film;
	private int score;
	private int total_score;

	public Invit(String ID, String film, int total_score){
		this.ID = ID;
		this.film = film;
		//this.score = score;
		this.total_score=total_score;
	}//end constructor


	public Invit(ArrayList<String> data){
		this.ID = data.get(0);
		this.film = data.get(1) ;
		this.score = Integer.valueOf(data.get(2));

		this.total_score=Integer.valueOf(data.get(3));
	}//end constructor

	public String getID(){
		return this.ID;
	}//end get

	public String getfilm(){
		return this.film;
	}//end get

	public int getScore(){
		return this.total_score;
	}//end get
	public int gettotalscore(){
		return this.total_score;
	}//end get

	public void setID(String ID){
		this.ID=ID;
	}//end set

	public String descrpt(){
		return this.ID+": \n"+this.film+" "+this.score+"\n"+this.total_score;
	}

}//end class
