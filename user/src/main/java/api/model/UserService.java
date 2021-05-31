package api.model;

import java.util.*;

import java.io.IOException;

public class UserService{

	private DataBaseUser db;

	public UserService() throws Exception{
		this.db = new DataBaseUser();
	}//end constructor


	public boolean existUser(String username){
		return db.EXIST_User(username);
	}//end existUser

	
	public void setUserDB(/*User user*/ String username, String email){
		//boolean updt = false; return updt
		this.db.INSERT_User(/*user.getUsername()*/ username, /*user.getEmail()*/ email);
	}//end setUserDB

	public ArrayList<Map<String,String>> getAllDB()
	{
		return this.db.SELECT_AllUser();
	}

	public User getUserDB(String username){
		ArrayList<Map<String,String>> params = db.SELECT_User(username);
		return new User(params.get(0).get("name"),params.get(0).get("email"));
	}//end removeUserDB

	public void removeUserDB(/*User user*/ String username){
		//boolean updt = false; return updt
		this.db.DELETE_User(/*user.getUsername()*/ username);
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
