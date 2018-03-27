package com.sp.group.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class CustomResponse {
	
	@JsonView(Views.Connect.class)
	private boolean  success=true;
	
	@JsonView(Views.FriendList.class)
	private List<String> friends;
	
	@JsonView(Views.FriendList.class)
	private int count;
	
	@JsonView(Views.RecipientList.class)
	private List<String> recipients;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public CustomResponse() {
		super();
	}

	public CustomResponse(boolean success, List<String> friends, int count) {
		super();
		this.success = success;
		this.friends = friends;
		this.count = count;
	}

	public CustomResponse(boolean success, List<String> recipients) {
		super();
		this.success = success;
		this.recipients = recipients;
	}

}
