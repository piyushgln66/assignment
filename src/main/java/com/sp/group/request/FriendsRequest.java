package com.sp.group.request;

import java.util.List;

public class FriendsRequest {
	private List<String> friends;
	
	public FriendsRequest() {
		super();
	}

	public FriendsRequest(List<String> friends) {
		super();
		this.friends = friends;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	
}
