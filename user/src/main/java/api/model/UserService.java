package api.model;

import java.util.*;

public class UserService{

	private DataBaseUser db;
	private Map<String,User> listofUsers = new HashMap<String,User>();

	public UserService(){
		//this.db = new DataBaseUser("src/main/resources/database.properties");
		// cree une base de donnée artificielle pour les testes
		double[][] proba ={ {0.1,0.4,0.3,0.6,0.2},{0.1,0.2,0.3,0.4,0.3},{0.2,0.4,0.3,0.6,0.9},{0.1,0.4,0.9,0.1,0.2}};
		String[] nomCritere = {"humour","horreur","action","drame","aventure"};
		String[] username = {"Yohna","Emile","Kaine","NieR"};
		String[] emails = {"yohna@123","emile@543","kaine@324","nier@0984"};

		for(int i= 0;i<4;i++)
		{
			
			Map<String,Double> criteres = new HashMap<String,Double>();
			for(int j = 0;j<5;j++)
			{
				criteres.put(nomCritere[j], proba[i][j]);
			}
			User user = new User(username[i],emails[i],criteres);
			this.listofUsers.put(username[i], user);
		}
	}//end constructor


	public boolean existUser(String username){
		//return db.EXIST_User(username);
		return this.listofUsers.containsKey(username);
	}//end existUser

	
	public void addUserDB(User newUser){
		//boolean updt = false; return updt
		//this.db.INSERT_User(username);
		this.listofUsers.put(newUser.getUsername(), newUser);
		
	}//end setUserDB

	public User getUserDB(String username){
		//ArrayList<String> params = db.SELECT_User(username);
		//return new User(params.get(0));
		return this.listofUsers.get(username);
	}//end removeUserDB

	public Map<String,User> getAll()
	{
		return this.listofUsers;
	}
	public void removeUserDB(String username){
		//boolean updt = false; return updt
		//this.db.DELETE_User(username);
		this.listofUsers.remove(username);
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
