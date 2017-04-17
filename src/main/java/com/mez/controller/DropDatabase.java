package com.mez.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DropDatabase {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	  static final String DB_URL = "jdbc:mysql://localhost/teste";

	  static final String USER = "root";
	  static final String PASS = "root";

	  public static void executa() throws Exception {
	    Connection conn = null;
	    Statement stmt = null;

	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    System.out.println("Deleting database...");
	    stmt = conn.createStatement();

	    String sql = "DROP DATABASE teste";
	    stmt.executeUpdate(sql);

	    stmt.close();
	    conn.close();
	  }

}
