package application;

import java.sql.*;

public class LoginModel {
	
	Connection conn;
	
	public LoginModel() {
		conn = SQLiteConnection.Connector();
		if (conn == null) System.exit(1);
	}
	
	public boolean isDbConnected() {
		try {
			return !conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isLogin(String user, String pass) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Budget where username = ? and password = ?";
		try{
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} 
			else {
				return false;
			}
		} catch (Exception e ) {
			System.out.println(e);
			return false;
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
}
