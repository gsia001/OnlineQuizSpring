package com.ce9001.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.ce9001.viewBean.UserBean;

import com.ce9001.dao.UserDao;

public class UserDaoImpl implements UserDao {

	DataSource dataSource;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public UserBean getUserInfo(String username) throws SQLException {
		String query = "Select * from users where username = ?";
		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
		pstmt.setString(1, username);
		ResultSet resultSet = pstmt.executeQuery();
		int firstTime = -1;
		int userID = -1;
		String accountType = "";
		String name = "";
		UserBean user = new UserBean();
		if (resultSet.next()) {
			firstTime = resultSet.getInt("FirstTimeAccess");
			accountType = resultSet.getString("AccountType");
			userID = resultSet.getInt("UserID");
		}
		String query2 = "Select * from " + accountType + " where UserID='" + userID + "' ";
		pstmt = dataSource.getConnection().prepareStatement(query2);
		resultSet = pstmt.executeQuery();
		if (resultSet.next()) {
			user.setId(resultSet.getInt(accountType + "ID"));
			user.setUserID(resultSet.getInt("UserID"));
			user.setName(resultSet.getString(accountType + "Name"));

		}
		user.setAccountType(accountType);
		user.setFirstTimeAccess(firstTime);
		return user;

	}

	@Override
	public boolean isValidUser(String username, String password) throws SQLException {
		String query = "Select count(1) from users where username = ? and password = ?";
		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next())
			return (resultSet.getInt(1) > 0);
		else
			return false;
	}

	@Override
	public boolean firstTimeLogin(String username) throws SQLException {
		String query = "Select * from users where username = ?";
		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
		pstmt.setString(1, username);
		ResultSet resultSet = pstmt.executeQuery();
		int firstTime = -1;
		int userID = -1;
		String accountType = "";
		String name = "";
		if (resultSet.next()) {
			firstTime = resultSet.getInt("FirstTimeAccess");
			if (firstTime == 0) {
				return true;
			}
			return false;

		} else
			return false;
	}

	public boolean changePassword(String userName, String password) throws SQLException {
		int change = -1;
		PreparedStatement statement = dataSource.getConnection().prepareStatement("UPDATE users SET Password=?, FirstTimeAccess=? WHERE UserName=?");
		statement.setString(3, userName);
		statement.setString(1, password);
		statement.setInt(2, 1);
		change = statement.executeUpdate();
		if (change == 1) {
			return true;
		}
		return false;

	}

}