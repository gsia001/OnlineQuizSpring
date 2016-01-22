package com.ce9001.delegate;

import java.sql.SQLException;

import com.ce9001.service.UserService;
import com.ce9001.viewBean.UserBean;

public class LoginDelegate {
	private UserService userService;

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public boolean isValidUser(String username, String password) throws SQLException {
		return userService.isValidUser(username, password);
	}

	public boolean firstTimeLogin(String username) throws SQLException {
		return userService.firstTimeLogin(username);
	}

	public UserBean getUserInfo(String username) throws SQLException {
		return userService.getUserInfo(username);
	}
}
