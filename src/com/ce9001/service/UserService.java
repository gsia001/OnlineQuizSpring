
package com.ce9001.service;

import java.sql.SQLException;

import com.ce9001.viewBean.UserBean;


public interface UserService
{
		public boolean isValidUser(String username, String password) throws SQLException;
		public boolean firstTimeLogin(String username) throws SQLException;
		public UserBean getUserInfo(String username) throws SQLException;
		public boolean changePassword(String userName, String password) throws SQLException;
}
