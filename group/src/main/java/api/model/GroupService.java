package api.model;

import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import org.json.*;
import java.io.FileWriter;


public class GroupService{

	//private DataBaseUser db;
	private Group test;
	public GroupService()throws IOException, InterruptedException
	{
		try {
			FileWriter myWriter = new FileWriter("/etc/hosts");
			myWriter.write("\n 129.194.10.130 tindfilm");
			myWriter.close();
		
		//this.db = new DataBaseUser("database.properties");
		Set<String> users = new HashSet<String>();
		String[] usersTab = {"NieR","Kaine","Emile","Yohna"};
		for(String user : usersTab)
		{
			users.add(user);
		}
		this.test = new Group("testGroup",users);
		} 
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }
		
	}//end constructor

	public Group getGroup(String groupName)
	{
		return this.test;
	}

	public Map<String,Double> getMoyenne()
	{
		return this.test.getMoyenne();
	}

	public Set<String> addUser(String groupe,String user){
		//boolean updt = false; return updt
		//this.db.INSERT_User(username);
		this.test.addUser(user);
		return this.test.getUsersName();
	}//end setUserDB

	public String getUser(String username)throws IOException, InterruptedException
	{
		//ArrayList<ArrayList<String>> params = db.SELECT_User(username);
		//return new User(params.get(0).get(0),params.get(0).get(1));
		
		return this.test.getUser(username);
	}

	public boolean removeUser(String groupName, String userName)
	{
		return this.test.removeUser(userName);
	}

	
}//end class
