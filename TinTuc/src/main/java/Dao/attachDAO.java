package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connect.connect;
import model.attachFileModel;
import model.tintuc;


public class attachDAO {
	public static Connection conn=connect.getConnection();
    public static PreparedStatement stmt=null;
    public static ResultSet rs=null;
    public boolean inserAttachFile(int a,String b,String c)
    {
    	String sql="insert into attach_file(post_id,link,name) values(?,?,?)";
   	 conn=connect.getConnection();
   	 try {
			stmt=conn.prepareStatement(sql);
			    
			stmt.setInt(1,a);          
           stmt.setString(2,b);  
           stmt.setString(3,c);   
           stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            
   	return true;
    }
    public boolean deleteAttachFile(int ID)
    {
    	 String sql="delete from attach_file where id=?";
    	 conn=connect.getConnection();
    	 try {
			stmt=conn.prepareStatement(sql);
			    
			stmt.setInt(1,ID);          
            
            stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             
    	return true;
    }
    public attachFileModel getAttachFile(int ID)
    {
    	attachFileModel attach  = null; 
    	
    	 String sql="select * from attach_file where id=?";
    	 conn=connect.getConnection();
    	 try {
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1,ID);  
            rs=stmt.executeQuery();
            while(rs.next())
            {
            	attach=new attachFileModel();
            	attach.setId(rs.getInt("id"));
            	attach.setPost_id(rs.getInt("post_id"));
            	attach.setLink(rs.getString("link"));
            	attach.setName(rs.getString("name"));
            }
            
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             
    	return attach;
    }
    public java.util.List<attachFileModel> getAllAttachFileByPostID(int id)
    {
    	attachFileModel attach  = null; 
    	java.util.List<attachFileModel> list=null;
    	list=new ArrayList<attachFileModel>();
    	 String sql="select * from attach_file where post_id=?";
    	 conn=connect.getConnection();
    	 try {
    		
			stmt=conn.prepareStatement(sql);
            stmt.setInt(1, id);
			rs=stmt.executeQuery();
            while(rs.next())
            {
            	attach=new attachFileModel();
            	attach.setId(rs.getInt("id"));
            	attach.setPost_id(rs.getInt("post_id"));
            	attach.setLink(rs.getString("link"));
            	attach.setName(rs.getString("name"));
            	list.add(attach);
            }
            
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             
    	return list;
    }
    public boolean updateAttachFile(String a,String b,int c)
    {
    	 String sql="update attach_file set link=?,  name=?  where post_id=?";
    	 conn=connect.getConnection();
    	 try {
			stmt=conn.prepareStatement(sql);
			    
			stmt.setString(1,a);          
            stmt.setString(2,b);  
            stmt.setInt(3,c);  
         
           
            stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             
    	return true;
    }
}
