package api.model;


public class User{	
	
	private String username;
	private String email;

	public User(String username){
		this.username = username;
		this.email = "";
	}//end constructor

	public User(String username,String email){
		this.username = username;
		this.email = email;
	}//end constructor

	public String getUsername(){
		return this.username;
	}//end get

	public String getEmail()
	{
		return this.email;
	}

	public void setUsername(String username){
		this.username=username;
	}//end set

	public String descrpt(){
		return this.username+" "+this.email+" ";
	}

}//end class
