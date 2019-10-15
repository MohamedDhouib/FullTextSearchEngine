package hello;

import java.sql.*;

public class DataBase {
	private Statement st;
	public DataBase() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?" + "user=root&password=password");
		st = con.createStatement();
		st.executeUpdate("DROP DATABASE IF EXISTS SearchEngine");
		st.executeUpdate("CREATE DATABASE SearchEngine");
		System.out.println("DATABASE SearchEngine Created");
		
	}
	
}
