package com.sp.group.dao;

import java.util.List;

public interface FriendsDao {
	
	public void initTables();
	
	public void connect(String first_email, String second_email);
	
	public List<String> getFriendsList(String email);
	
	public List<String> getMutualFriends(String first_email, String second_email);
	
	public void subscribe(String requestor, String target);
	
	public void block(String requestor, String target);
	
	public List<String> getUpdateRecipients(String sender_email);
	
	public List<String> filterBlockingRecipients(List<String> emails, String sender_email);
	
}
