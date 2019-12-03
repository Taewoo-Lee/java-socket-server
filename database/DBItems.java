package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBItems {
	
	public static void add(String name, String kind, String postByID, String content, String price, String limit_date) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBconnect.connect();
			
			String sql = "INSERT INTO items VALUES (null,?,?,?,?,?,?,?,?,?,?,?)";
			
			long time = System.currentTimeMillis(); 

			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			String pub_time = dayTime.format(new Date(time));
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, kind);
			pstmt.setString(3, postByID);
			pstmt.setString(4, "0");
			pstmt.setString(5, "null");
			pstmt.setString(6, "null");
			pstmt.setString(7, content);
			pstmt.setString(8, price);
			pstmt.setString(9, limit_date);
			pstmt.setString(10, "0");
			pstmt.setString(11, pub_time); //pub_date
			
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
	
	public static ObservableList loadItems() {
		
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        ObservableList<ObservableList> ItemList = FXCollections.observableArrayList();
        
        try {
        	conn = DBconnect.connect();
        	stmt = conn.createStatement();
        	 
        	String sql = "SELECT post_num, post_name, kinds, post_by_id, limit_date From items";
        	rs = stmt.executeQuery(sql);
        	
        	while(rs.next()) {
        		ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                ItemList.add(row);
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
        
        return ItemList;
	}
}
