package api.model;

import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import org.json.*;
import java.net.*;
import java.io.*;


public class Group {

    private String groupName; 
    private Set<String> users = new HashSet<String>();
    private String admin;
    private String invitation;
    
    public Group() {
	this.groupName = "Unknown";
    }

    public Group(String groupName,String admin,String invitation) {
        this.groupName = groupName;
        this.admin = admin;
        this.invitation = invitation;
    }

    public Group(String groupName,String admin,String invitation,Set<String> users)
    {
        this(groupName,admin,invitation);
        this.users = users;
    }
    
   public String getGroupName(){
	return this.groupName;
   }

   public String getGroupAdmin(){
	return this.admin;
   }

   public String getGroupInvitation(){
	return this.invitation;
   }

    public void setGroupName(String groupName){
	this.groupName = groupName;
   }

    public void addUser(String userName){
	this.users.add(userName);
   }
 
	/* renvoie le nom des utilisateurs */
	 public Set<String> getUsersName(){
		return this.users;
    }
    
    
     public boolean removeUser(String userName){
        return this.users.remove(userName);
    }
    
     public boolean isInGroup(String userName){
    	return this.users.contains(userName);
    }   
}
