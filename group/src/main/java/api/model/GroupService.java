package api.model;

import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import org.json.*;


public class GroupService{

	//private DataBaseUser db;
	private Group test;
	public GroupService()throws IOException, InterruptedException
	{
		//this.db = new DataBaseUser("database.properties");
		Set<String> users = new HashSet<String>();
		String[] usersTab = {"NieR","Kaine","Emile","Yohna"};
		for(String user : usersTab)
		{
			users.add(user);
		}
		this.test = new Group("testGroup",users);
		
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
