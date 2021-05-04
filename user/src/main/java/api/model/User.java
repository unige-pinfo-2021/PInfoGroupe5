package api.model;

import java.util.*;


public class User{	
	
	private String username;
	private String email = "";
	private Map<String,Double> critere = new HashMap<String,Double>();

	public User(String username){
		this.username = username;
	}//end constructor

	public User(String username,String email)
	{
		this(username);
		this.email = email;
	}

	public User(String username,String email,Map<String,Double> critere)
	{
		this(username,email);
		this.critere = critere;
	}

	public void addCritere(String critere, Double pourcentage)
	{
		this.critere.put(critere,pourcentage);
	}

	public Map<String,Double> getCritere()
	{
		return this.critere;
	}

	public String getEmail()
	{
		return this.email;
	}
	public String getUsername(){
		return this.username;
	}//end get

	public void setUsername(String username){
		this.username=username;
	}//end set

	public String descrpt(){
		return this.username+" ";
	}

}//end class
