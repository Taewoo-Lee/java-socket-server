package database;
import java.sql.*;

public class DBconnect {

	 public static Connection connect() {
		 
		 	String usr = "h5seung";
		 	String password = "seung1023";
            String url = "jdbc:mysql://59.27.140.107:3306/h5seung?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";

	        Connection conn = null;

	        try{
	           Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = DriverManager.getConnection(url, usr, password);
	        }
	        catch(ClassNotFoundException e){
	            System.out.println("Driver load fail");
	        }
	        catch(SQLException e){
	        	e.printStackTrace();
	        }
	        
	        return conn;
	        
	    }
}