package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public static void returnItem(String post_num) {
		Connection conn = null;
        Statement stmt = null;

        try {
        	conn = DBconnect.connect();
        	 stmt = conn.createStatement();
        	String sql = "UPDATE items "+
        				 "SET rent_state = 0, rent_id = 'null'"+
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
	
	public static void addNotice(String read_id, String write_id, String post_num, String rent) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBconnect.connect();
			
			String sql = "INSERT INTO notification VALUES (null,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, read_id);
			pstmt.setString(2, write_id);
			pstmt.setString(3, post_num);
			pstmt.setString(4, rent);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
            try{
                if( conn != null && !conn.isClosed()){
                    conn.close();
                }
                if( pstmt != null && !pstmt.isClosed()){
                    pstmt.close();
                }
            }
            catch( SQLException e){
                e.printStackTrace();
            }
        }
		
	}
	
	public static String loadNotice(String id) {
		StringBuilder results = new StringBuilder();
		
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
        	conn = DBconnect.connect();
        	 stmt = conn.createStatement();
        	 
        	String sql = "SELECT write_id, post_num, rent  From notification WHERE read_id = "+"'"+id+"'";
        	rs = stmt.executeQuery(sql);
        	
        	while(rs.next()) {
                    results.append(rs.getString(1)+"님이 "+rs.getString(2)+"번 물건을 "+rs.getString(3)+"하셨습니다.@@");
                }
        	sql = "DELETE FROM notification WHERE read_id = "+"'"+id+"'";
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
        
		return results.toString();
	}
	
}
