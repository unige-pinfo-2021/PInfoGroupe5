package api.model;

import java.util.*;

// Mock, replace by service DataBase
public class UserService{

	private ArrayList<User> database = new ArrayList();

	public UserService(){
		User user = new User("user0", "email0");
		user.addGroup("Group");
		user.addGroup("all");
		database.add(user);

		user = new User("user1", "email1");
		user.addGroup("all");
		database.add(user);

		user = new User("user2", "email2");
		user.addGroup("Group");
		user.addGroup("all");
		database.add(user);

		user = new User("user5", "email5");
		user.addGroup("all");
		database.add(user);
		/*database.add(new User("user0", "email0"));
		database.add(new User("user1", "email1"));
		database.add(new User("user2", "email2"));*/
	}

	public ArrayList<User> getall(){
		return this.database;
	}

	public User getUser(int id){
		return (this.database).get(id);
	}

	public User getUser(String username){
		for(User user: database){
			if((user.getUsername()).equals(username)){
				return user;
			} 	
		}
		return null;
	}

	public boolean containsUser(User user){
		return this.database.contains(user);
	}

	public boolean addUser(User user){
		if(!containsUser(user)){
			this.database.add(user);
			return true;
		}
		return false;
	}

	public boolean deleteUser(User user){
		if(containsUser(user)){
			this.database.remove(user);
			return true;
		}
		return false;
	}

	// Function that returns group => creer une classe Group ?
	public ArrayList<User> getGroupUser(String name){
		ArrayList<User> group = new ArrayList();
		for(User user : database){
			if(user.isInGroup(name)){
				group.add(user);
			
			}
		}
		return group;
	}

}//end class
