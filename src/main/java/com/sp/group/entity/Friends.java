package com.sp.group.entity;

public class Friends {
	private String first_email;
	private String second_email;
	
	public Friends() {
		super();
	}
	public Friends(String first_email, String second_email) {
		super();
		this.first_email = first_email;
		this.second_email = second_email;
	}
	public String getFirst_email() {
		return first_email;
	}
	public void setFirst_email(String first_email) {
		this.first_email = first_email;
	}
	public String getSecond_email() {
		return second_email;
	}
	public void setSecond_email(String second_email) {
		this.second_email = second_email;
	}
	
	
	
}
