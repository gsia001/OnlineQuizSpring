package com.ce9001.service.impl;

import java.sql.SQLException;

import com.ce9001.dao.UserDao;
import com.ce9001.service.UserService;
import com.ce9001.viewBean.UserBean;

public class UserServiceImpl implements UserService
{

		private UserDao userDao;

		public UserDao getUserDao()
		{
				return this.userDao;
		}

		public void setUserDao(UserDao userDao)
		{
				this.userDao = userDao;
		}

		@Override
		public boolean isValidUser(String username, String password) throws SQLException
		{
				return userDao.isValidUser(username, password);
		}
		
		@Override
		public boolean firstTimeLogin(String username) throws SQLException
		{
				return userDao.firstTimeLogin(username);
		}
		
		@Override
		public UserBean getUserInfo(String username) throws SQLException{
			return userDao.getUserInfo(username);
		}
		
		@Override
		public boolean changePassword(String userName, String password) throws SQLException{
			return userDao.changePassword(userName, password);
		}
		

}
