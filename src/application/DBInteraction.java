package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBInteraction {
	
	private Connection connect() {
		// SQLite connection string
        String url = "jdbc:sqlite:BudgetDB.sqlite";
        Connection conn = null;
        try {
			Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
			System.out.println("Database connection successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	
	
	/*sql example:"CREATE TABLE COMPANY " +
	"(ID INT PRIMARY KEY		NOT NULL, " +
	"NAME			STRING		NOT NULL, " +
	"AGE			INT			NOT NULL, " +
	"ADDRESS		CHAR(50), " +
	"SALARY 		REAL)"; */
	public void createTable(String sql) {
		Connection conn;
		Statement state;
		
		try {
			conn = this.connect();
			
			state = conn.createStatement();
			state.executeUpdate(sql);
			state.close();
			if (conn != null){
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			System.err.println(e);
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
	
	public void addData(String date, double food, double gas, String notes) {
		String sql = "INSERT INTO budget(date,food,gas,notes) "
				+ "VALUES(?,?,?,?)";
		
		Connection conn;
		try {
			conn = this.connect();
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, date);
				pstmt.setDouble(2, food);
				pstmt.setDouble(3, gas);
	            pstmt.setString(4, notes);
	            pstmt.executeUpdate();
			if (conn != null){
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			System.err.println(e);
			System.exit(0);
		}
	}
	
	public List<String> pullData(String date) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT * FROM budget WHERE date = ?";
		Connection conn;
		ResultSet rs;
		
		try {
			conn = this.connect();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			while (rs.next()) {
				for (int i = 4; i <= rsmd.getColumnCount(); i++){
					String columnName = rsmd.getColumnName(i);
					list.add(columnName);
					list.add(rs.getString(columnName));
				}
				
			}
			
			conn.close();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(0);
		}
		return list;
	}
	
	public void addColumn(String newCategory) {
		String sql = "ALTER TABLE budget ADD COLUMN " + newCategory + " REAL;";
		Connection conn;
		
		try {
			conn = this.connect();
			
			Statement state = conn.createStatement();
			state.executeQuery(sql);
			System.out.println("Column " + newCategory + " added successfully");
			
			conn.close();
			state.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}



