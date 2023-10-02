package com.controller.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection connection;
	
//	 private static String url = "jdbc:sqlserver://DESKTOP-CLDULA4\\TEMPLE:1433;databaseName=shopnew;";
	   private static String url = "jdbc:sqlserver://aptechsem4.mssql.somee.com;initial catalog=aptechsem4";
//	    private static String user = "Dat";
//	    private static String password = "992002";
	   
	   
	   private static String user = "tannguyen181_SQLLogin_1";
	    private static String password = "yqmou4lv7u";
	    
		public static Connection getConnection() throws SQLException {
			connection = DriverManager.getConnection(url, user, password);
			return connection;
		}
}
