package api.model;



public class App {
	   
	   public static void main( String[] args ){

			

			UserService userv = new UserService();

			System.out.println("User0: "+userv.existUser("User0"));
			System.out.println("GothamGirl: "+userv.existUser("GothamGirl"));

			userv.setUserDB("GothamGirl");
			userv.removeUserDB("User1");

			User user = userv.getUserDB("User0");
			System.out.println(user.descrpt());
		    }
}
 