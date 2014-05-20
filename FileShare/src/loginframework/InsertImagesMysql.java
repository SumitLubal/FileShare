package loginframework;

import java.sql.*;
import java.io.*;

import org.omg.PortableServer.Servant;

import share.Server;

public class InsertImagesMysql {
	public static void main(String username, String filepath) {
		 System.out.println("Insert Image Example!");
		 String driverName = "com.mysql.jdbc.Driver";
		 String dbName = "test";
		 String userName = "root";
		 String password = "root";
		 Connection con = null;
		 try{
		   Class.forName(driverName);
		   con = DriverManager.getConnection(DB.dbURL,userName,password);
		   System.out.println(username);
		   Statement st = con.createStatement();
		   File imgfile = new File(filepath);
		  
		  FileInputStream fin = new FileInputStream(imgfile);
		  
		   PreparedStatement pre =
		   con.prepareStatement("insert into photos values(?,?)");
		  
		   pre.setBinaryStream(1,(InputStream)fin,(int)imgfile.length());
		   pre.setString(2,username);
		   pre.executeUpdate();
		   System.out.println("Successfully inserted the file into the database!");

		   pre.close();
		   con.close(); 
		 }catch (Exception e1){
		 System.out.println(e1.getMessage());
		 }
	}
}
