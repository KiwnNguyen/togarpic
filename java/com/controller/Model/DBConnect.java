package com.controller.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static Connection connection;
	

	   private static String url = "jdbc:sqlserver://aptech_group1.mssql.somee.com;initial catalog=aptech_group1";
	   private static String user = "tannguyen18_SQLLogin_1";
	    private static String password = "2j192j2u1t";
	    
		public static Connection getConnection() throws SQLException {
			connection = DriverManager.getConnection(url, user, password);
			return connection;
		}
}
