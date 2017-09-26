package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteConnection {
	
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:BudgetDB.sqlite");
			return conn;
		} catch (Exception e){
			System.out.println("Failed");
			return null;
		}
		
	}
}
