package loginframework;
import java.sql.*;


public class DB {
	static String dbURL = "jdbc:mysql://127.0.0.1/test";
	static String username = "root";
	static String password = "root";
	public static void writeOn(String querry){
		Connection conn = null;
		Statement stmt = null;
		try {
			String username = "root";
			String password = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, username, password);
			stmt = conn.createStatement();
			stmt.execute(querry);
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

	}
	public static void createOrReplaceTable(String tableName){
		Connection conn = null;
		Statement stmt = null;
		try {
			String username = "root";
			String password = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, username, password);
			stmt = conn.createStatement();
			String querry = "create table "+tableName+" (name varchar(200),date varchar(200))";
			stmt.execute(querry);
		} catch (Exception e) {
			System.err.println("Table already exist");
			System.err.println(e.getMessage());
		}finally{
			System.out.println("Table Created or already existed");
		}
		
	}
	public static void fireQuerry(String querry) {

		Connection conn = null;
		Statement stmt = null;
		try {
			String username = "root";
			String password = "root";

			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(dbURL, username, password);

			stmt = conn.createStatement();
			stmt.execute(querry);
			System.out.println("Cool welcome to our security System"+username);
			
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

	}

	public static ResultSet readData() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String username = "root";
			String password = "root";

			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(dbURL, username, password);

			stmt = conn.createStatement();

			if (stmt.execute("select * from auth")) {
				rs = stmt.getResultSet();
			} else {
				System.err.println("select failed");
			}
		} catch (ClassNotFoundException ex) {
			System.err.println("Failed to load mysql driver");
			System.err.println(ex);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

		}
		return rs;
	}
	public static ResultSet readFromTable(String tableName){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String username = "root";
			String password = "root";

			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(dbURL, username, password);

			stmt = conn.createStatement();

			if (stmt.execute("select * from "+tableName)) {
				rs = stmt.getResultSet();
			} else {
				System.err.println("select failed");
			}
		} catch (ClassNotFoundException ex) {
			System.err.println("Failed to load mysql driver");
			System.err.println(ex);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

		}
		return rs;
	}
	public static ResultSet selectQuerry(String querry){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String username = "root";
			String password = "root";

			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(dbURL, username, password);

			stmt = conn.createStatement();

			if (stmt.execute(querry)) {
				rs = stmt.getResultSet();
			} else {
				System.err.println("select failed");
			}
		} catch (ClassNotFoundException ex) {
			System.err.println("Failed to load mysql driver");
			System.err.println(ex);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

		}
		return rs;
		
	}
	public static int getCountOfMonth(String username) {
		ResultSet rs = selectQuerry("select * from passwordchange where name='"+username+"'");
		int count = 0;
		try {
			rs.next();
			count =  rs.getInt("count");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Test Erroe");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}