package api.model;

import java.util.*;
import java.sql.*;
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

	public int  test() {
		int rowCount = 0;
		try (
		         // Step 1: Construct a database 'Connection' object called 'conn'
		         Connection conn = DriverManager.getConnection(
		               "jdbc:mysql://129.194.10.130:3306/tinderfilmBD",
		               "groupe5", "12345");   // For MySQL only
		               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"
		 
		         // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
		         Statement stmt = conn.createStatement();
		      ) {
		         // Step 3: Write a SQL query string. Execute the SQL query via the 'Statement'.
		         //  The query result is returned in a 'ResultSet' object called 'rset'.
		         String strSelect = "select * from user";
		         System.out.println("The SQL statement is: " + strSelect + "\n"); // Echo For debugging
		 
		         ResultSet rset = stmt.executeQuery(strSelect);
		 
		         // Step 4: Process the 'ResultSet' by scrolling the cursor forward via next().
		         //  For each row, retrieve the contents of the cells with getXxx(columnName).
		         System.out.println("The records selected are:");
		         
		         // Row-cursor initially positioned before the first row of the 'ResultSet'.
		         // rset.next() inside the whole-loop repeatedly moves the cursor to the next row.
		         // It returns false if no more rows.
		         while(rset.next()) {   // Repeatedly process each row
		            String title = rset.getString("name");  // retrieve a 'String'-cell in the row
		            String price = rset.getString("email");  // retrieve a 'double'-cell in the row
		            int    qty   = 0;       // retrieve a 'int'-cell in the row
		            System.out.println(title + ", " + price + ", " + qty);
		            ++rowCount;
		         }
		         System.out.println("Total number of records = " + rowCount);
		 
		      } catch(SQLException ex) {
		         ex.printStackTrace();
		      }
		return rowCount;
	}
	//public void updateUserDB(){}

	/*public ArrayList<User> getlistofUsers(ArrayList<String> usernames){
		ArrayList<User> users = new ArrayList();
		for(String username: usernames){
			users.add(getUserDB(username));
		}
		return users;
	}//end getlistofUsers*/
	
}//end class
