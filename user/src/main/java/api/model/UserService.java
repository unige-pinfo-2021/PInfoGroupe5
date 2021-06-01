package api.model;

import java.util.*;

import java.io.IOException;

public class UserService{

	private DataBaseUser db;

	public UserService() throws Exception{
		this.db = new DataBaseUser();
	}


	public boolean existUser(String username){
		return db.EXIST(username);
	}

	
	public void setUserDB(String username, String email){
		//boolean updt = false; return updt
		this.db.INSERT(username,  email);
	}

	public ArrayList<Map<String,String>> getAllDB()
	{
		return this.db.SELECTALL();
	}

	public User getUserDB(String username){
		ArrayList<Map<String,String>> params = db.SELECT(username);
		return new User(params.get(0).get("name"),params.get(0).get("email"));
	}

	public void removeUserDB(String username){
		//boolean updt = false; return updt
		this.db.DELETE(username);
	}


	//public void updateUserDB(){}

	/*public ArrayList<User> getlistofUsers(ArrayList<String> usernames){
		ArrayList<User> users = new ArrayList();
		for(String username: usernames){
			users.add(getUserDB(username));
		}
		return users;
	}//end getlistofUsers*/
	
}//end class
