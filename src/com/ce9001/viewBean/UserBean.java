package com.ce9001.viewBean;

public class UserBean {
	private int id;
	private int userID;
	private String name;
	private String course;
	private int firstTimeAccess;
	private String accountType;
	
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getFirstTimeAccess() {
		return firstTimeAccess;
	}
	public void setFirstTimeAccess(int firstTimeAccess) {
		this.firstTimeAccess = firstTimeAccess;
	}
}
