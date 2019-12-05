package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBThings {
	public static String loadMyThings(String myID) {
		
		StringBuilder results = new StringBuilder();
		
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
        	conn = DBconnect.connect();
        	stmt = conn.createStatement();
        	 
        	String sql = "SELECT post_num, kinds, post_name, rent_state, limit_date, price, like_num From items WHERE post_by_id = "+"'"+myID+"'";
        	rs = stmt.executeQuery(sql);
        	while(rs.next()) {
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    results.append(rs.getString(i)+"@@");
                }
                results.append("//");
                
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
        
        return results.toString();
	}
	
public static String loadYourThings(String myID) {
		
		StringBuilder results = new StringBuilder();
		
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
        	conn = DBconnect.connect();
        	stmt = conn.createStatement();
        	 
        	String sql = "SELECT kinds, post_name, limit_date, price, like_num From items WHERE rent_id = "+"'"+myID+"'";
        	rs = stmt.executeQuery(sql);
        	while(rs.next()) {
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    results.append(rs.getString(i)+"@@");
                }
                results.append("//");
                
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
        
        return results.toString();
	}
}
