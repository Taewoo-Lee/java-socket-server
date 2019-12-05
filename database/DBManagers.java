package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManagers {
	public static String managers_load(String id) {
		
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        String password = "";
        
        try {
        	conn = DBconnect.connect();
        	 stmt = conn.createStatement();
        	 
        	String sql = "SELECT password From managers WHERE id = "+"'"+id+"'";
        	rs = stmt.executeQuery(sql);
        	
        	while(rs.next()) {
        	password = rs.getString(1);
        	}
        	
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
            try{
                if( conn != null && !conn.isClosed()){
                    conn.close();
                }
                if( stmt != null && stmt.isClosed()){
                    stmt.close();
                }
            }
            catch( SQLException e){
                e.printStackTrace();
            }
        }
        return password;
	}
	
	
	public static void deleteData(String post_num) {
		Connection conn = null;
        Statement stmt = null;
        int rs;
        
        try {
        	conn = DBconnect.connect();
        	 stmt = conn.createStatement();
        	 
        	String sql = "DELETE FROM items WHERE post_num = "+post_num;
        	rs = stmt.executeUpdate(sql);
        	
        	
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
            try{
                if( conn != null && !conn.isClosed()){
                    conn.close();
                }
                if( stmt != null && stmt.isClosed()){
                    stmt.close();
                }
            }
            catch( SQLException e){
                e.printStackTrace();
            }
        }
	}
}
