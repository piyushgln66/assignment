package com.sp.group.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.group.dao.FriendsDao;

@Service
public class FriendsServiceImpl implements FriendsService {
	
	@Autowired
	private FriendsDao friendsDao;
	
	@Override
	public void initTables() {
		friendsDao.initTables();
	}

	@Override
	public void connect(String first_email, String second_email) {
		friendsDao.connect(first_email, second_email);
	}

	@Override
	public List<String> getFriendsList(String email) {
		return friendsDao.getFriendsList(email);
	}

	@Override
	public List<String> getMutualFriends(String first_email, String second_email) {
		return friendsDao.getMutualFriends(first_email, second_email);
	}

	@Override
	public void subscribe(String requestor, String target) {
		friendsDao.subscribe(requestor, target);
	}

	@Override
	public void block(String requestor, String target) {
		friendsDao.block(requestor, target);
	}

	@Override
	public List<String> getUpdateRecipients(String sender_email, String text) {
		List<String> recipientsList = friendsDao.getUpdateRecipients(sender_email);
		List<String> recipientsListInText = new ArrayList<String>();
		Pattern pattern = Pattern.compile("[\\w.]+@[\\w.]+");
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()){
            String group = matcher.group();
            recipientsListInText.add(group);
        }
        List<String> finalList = friendsDao.filterBlockingRecipients(recipientsListInText, sender_email);
        recipientsList.addAll(finalList);
        return recipientsList;
	}
	
}
