package loginframework;

import java.sql.*;
import java.io.*;

import share.Server;

public class RetriveImagesMysql {
	public static String retrieve(String user) {
		System.out.println("Retrive Image Example!");
		String driverName = "com.mysql.jdbc.Driver";
		String url = DB.dbURL;
		String userName = DB.username;
		String password = DB.password;
		Connection con = null;
		String filePath = "f://image.png";
		try {
			Class.forName(driverName);
			con = DriverManager.getConnection(url, userName, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select photo from photos where user = '"+user+"';");
			while (rs.next()) {
				InputStream in = rs.getBinaryStream(1);
				OutputStream f = new FileOutputStream(new File(filePath));
				int c = 0;
				while ((c = in.read()) > -1) {
					f.write(c);
				}
				f.close();
				in.close();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return filePath;
	}

	public static String getFilePath(String userName) {
		String filePath = null;
		return retrieve(userName);
		// TODO Auto-generated method stub
		
	}
}
