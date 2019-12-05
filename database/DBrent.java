package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBrent {
	public static void rentItem(String post_num, String rent_id) {
		Connection conn = null;
        Statement stmt = null;
   	 System.out.println(post_num+rent_id);
        try {
        	conn = DBconnect.connect();
        	 stmt = conn.createStatement();
        	String sql = "UPDATE items "+
        				 "SET rent_state = 1, rent_id = "+"'"+rent_id+"'"+
        				 "WHERE post_num = "+post_num;
        	stmt.executeUpdate(sql);

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
	public static String loadRentState(String post_num) {
		String result = null;
		
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
        	conn = DBconnect.connect();
        	 stmt = conn.createStatement();
        	 
        	String sql = "SELECT rent_state From items WHERE post_num = "+"'"+post_num+"'";
        	rs = stmt.executeQuery(sql);
        	
        	while(rs.next()) {
        	result = rs.getString(1);
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
		
		return result;
	}
	
}
