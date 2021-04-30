package api.model;


public class User{	
	
	private String username;

	public User(String username){
		this.username = username;
	}//end constructor

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
