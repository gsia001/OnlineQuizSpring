package com.ce9001.delegate;

import java.sql.SQLException;

import com.ce9001.service.UserService;
import com.ce9001.viewBean.ChangePasswordBean;

public class ChangePasswordDelegate {

	private UserService userService;

	public UserService getUserService()
	{
			return this.userService;
	}

	public void setUserService(UserService userService)
	{
			this.userService = userService;
	}
	public boolean changePassword(String userName, String password) throws SQLException
    {
		    return userService.changePassword(userName, password);
    }
}
