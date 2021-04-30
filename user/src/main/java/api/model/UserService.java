package api.model;

import java.util.*;

public class UserService{

	private DataBaseUser db;
	//private ArrayList<User> listofUsers = new ArrayList();

	public UserService(){
		this.db = new DataBaseUser("src/main/resources/database.properties");
	}//end constructor


	public boolean existUser(String username){
		return db.EXIST_User(username);
	}//end existUser

	
	public void setUser(String username){
		//boolean updt = false; return updt
		this.db.INSERT_User(username);
	}//end setUserDB

	public User getUser(String username){
		ArrayList<ArrayList<String>> params = db.SELECT_User(username);
		return new User(params.get(0).get(0),params.get(0).get(1));
	}//end removeUserDB
	public ArrayList<User> getall()
	{
		ArrayList<ArrayList<String>> params = db.SELECT_All();
		ArrayList<User> utilisateurs = new ArrayList<User>();
		for(ArrayList<String> info : params)
		{
			utilisateurs.add(new User(info.get(0),info.get(1)));
		}
		return utilisateurs;
	}
	public void removeUser(String username){
		//boolean updt = false; return updt
		this.db.DELETE_User(username);
	}//end removeUserDB


	//public void updateUserDB(){}

	/*public ArrayList<User> getlistofUsers(ArrayList<String> usernames){
		ArrayList<User> users = new ArrayList();
		for(String username: usernames){
			users.add(getUserDB(username));
		}
		return users;
	}//end getlistofUsers*/
	
}//end class
