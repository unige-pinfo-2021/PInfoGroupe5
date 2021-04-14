package api.model;

import java.util.*;

public class User {

    private String username;
    private String email; 
    private ArrayList<String> groups;
    //private ArrayList<Films> Historic;

   //private ArrayList<String> preferences;

    public User() {
	this.username = "Unknown";
        this.email = "default";
	this.groups = new ArrayList();
    }

    public User(String username, String email) {
        this.username = username;
        this.email= email;
        this.groups = new ArrayList();
    }

   public String getUsername(){
	return this.username;
   }

   public String getEmail(){
	return this.email;
   }

    public void setUsername(String username){
	this.username = username;
   }

    public void setEmail(String email){
	this.email = email;
   }

   public boolean equals(Object obj){
   		boolean flag = false;
   		if (obj instanceof User){
   			flag = (this.username).equals(((User) obj).getUsername()) && (this.email).equals(((User) obj).getEmail());
   		}
   		return flag;
	}

	/*Gestion des groupes pour un utilisateur*/
	 public ArrayList<String> getGroupList(){
		return this.groups;
    }
    
    public boolean addGroup(String name){
    	if(!this.groups.contains(name)){
			this.groups.add(name);
			return true;
		}
		return false;
    }
    
     public boolean deleteGroup(String name){
    	if(this.groups.contains(name)){
			this.groups.remove(name);
			return true;
		}
		return false;
    }
    
     public boolean isInGroup(String name){
    	return this.groups.contains(name);
    }

}//end class
