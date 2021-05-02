package api.model;

import java.util.*;


public class GroupService{

	//private DataBaseUser db;
	private Group test = new Group("testGroup");
	public GroupService(){
		//this.db = new DataBaseUser("database.properties");
	}//end constructor

	public Group getGroup(String groupName)
	{
		return this.test;
	}

	public Set<String> addUser(String groupe,String user){
		//boolean updt = false; return updt
		//this.db.INSERT_User(username);
		this.test.addUser(user);
		return this.test.getUsersName();
	}//end setUserDB

	public Set<String> getUser(String username){
		//ArrayList<ArrayList<String>> params = db.SELECT_User(username);
		//return new User(params.get(0).get(0),params.get(0).get(1));
		
		return this.test.getUsersName();
	}

	public boolean removeUser(String groupName, String userName)
	{
		return this.test.removeUser(userName);
	}

	
}//end class
