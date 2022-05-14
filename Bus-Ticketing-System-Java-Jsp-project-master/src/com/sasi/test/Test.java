package com.sasi.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://ec2-52-4-104-184.compute-1.amazonaws.com:5432/ddshttahk9btj7?sslmode=require";
			String username =  "tlznodimqwirfj";
			String password = "82710d527913abe9d8e10657fe5a491bd2e076029d7abe3c1cb9ff9d924578f4";
			connection = DriverManager.getConnection(url, username, password);
			preparedStatement = connection.prepareStatement("select * from redbus_users");
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}
}
