package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connect.connect;
import model.page;
import model.tintuc;

public class pageDao {
	public static Connection conn=connect.getConnection();
    public static PreparedStatement stmt=null;
    public static ResultSet rs=null;
    public boolean updatePage(String a,String b)
    {
    	 String sql="update page1 set tieude=? ,noidung=?  where ID=1";
    	 conn=connect.getConnection();
    	 try {
			stmt=conn.prepareStatement(sql);
			    
			stmt.setString(1,a);          
            stmt.setString(2,b);  
            
            stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             
    	return true;
    }
    public page getPage()
    {
    	page tt  = null; 
    	
    	 String sql="select * from page1 where ID=1";
    	 conn=connect.getConnection();
    	 try {
			stmt=conn.prepareStatement(sql);
		 
            rs=stmt.executeQuery();
            while(rs.next())
            {
            	tt=new page();
            	tt.setID(rs.getInt("ID"));
            	tt.setTieuDe(rs.getString("tieude"));
            	tt.setNoiDung(rs.getString("noidung"));

            	
            }
            
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             
    	return tt;
    }

}
