package api.model;


public class User{	
	
	private String username;

	public User(String username){
		this.username = username;
	}//end constructor

	public String getUsername(){
		return this.username;
	}//end get

	public void setUsername(String username){
		this.username=username;
	}//end set

<<<<<<< HEAD
    public User(String username, String email) {
        this.username = username;
        this.email= email;
        this.groups = new ArrayList();
    }

    public User(String username, String email, String groupe) {
        this.username = username;
        this.email= email;
        this.groups = new ArrayList();
        this.addGroup(groupe);
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
=======
	public String descrpt(){
		return this.username+" ";
>>>>>>> f01df52a27671ee3ca75335aa85a3623188cccd5
	}

}//end class
