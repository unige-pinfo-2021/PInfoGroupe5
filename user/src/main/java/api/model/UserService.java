package api.model;

import java.util.*;

public class UserService{

	//private DataBaseUser db;
	private ArrayList<User> listofUsers = new ArrayList();

	public UserService(){
<<<<<<< HEAD
		this.listofUsers.add(new User("tom","tom1954@"));
		this.listofUsers.get(0).addGroup("dessin anime");

		this.listofUsers.add(new User("jerry","Jerry1954@"));
		this.listofUsers.get(1).addGroup("dessin anime");

		this.listofUsers.add(new User("Louis","Louis123@"));
		this.listofUsers.get(2).addGroup("film francais");

		this.listofUsers.add(new User("Gerard","Gerars432@"));
		this.listofUsers.get(3).addGroup("film francais");
		//this.db = new DataBaseUser("database.properties");
=======
		this.db = new DataBaseUser("src/main/resources/database.properties");
>>>>>>> f01df52a27671ee3ca75335aa85a3623188cccd5
	}//end constructor


	public boolean existUser(String username){
		//return db.EXIST_User(username);
		return false;
	}//end existUser

	
<<<<<<< HEAD
	public void setUser(String username,String email ,String groupe){
=======
	public void setUserDB(String username){
>>>>>>> f01df52a27671ee3ca75335aa85a3623188cccd5
		//boolean updt = false; return updt
		//this.db.INSERT_User(username);
		this.listofUsers.add(new User(username,email,groupe));
	}//end setUserDB

<<<<<<< HEAD
	public User getUser(String username){
		//ArrayList<ArrayList<String>> params = db.SELECT_User(username);
		//return new User(params.get(0).get(0),params.get(0).get(1));
		User retour = new User();
		for(User u : this.listofUsers)
		{
			if(u.getUsername() == username)
			{
				return u;
			}
		}
		return retour;
	}

	public ArrayList<User> getall()
	{
		/*ArrayList<ArrayList<String>> params = db.SELECT_All();
		ArrayList<User> utilisateurs = new ArrayList<User>();
		for(ArrayList<String> info : params)
		{
			utilisateurs.add(new User(info.get(0),info.get(1)));
		}
		return utilisateurs;*/
		return this.listofUsers;
	}

	public void removeUser(String username){
=======
	public User getUserDB(String username){
		ArrayList<String> params = db.SELECT_User(username);
		return new User(params.get(0));
	}//end removeUserDB

	public void removeUserDB(String username){
>>>>>>> f01df52a27671ee3ca75335aa85a3623188cccd5
		//boolean updt = false; return updt
		//this.db.DELETE_User(username);
		for(int i = 0;i<this.listofUsers.size();i++)
		{
			if(this.listofUsers.get(i).getUsername() == username)
			{
				this.listofUsers.remove(i);
				break;
			}
		}
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
