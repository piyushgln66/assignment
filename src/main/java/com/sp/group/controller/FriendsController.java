package com.sp.group.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.sp.group.request.EmailRequest;
import com.sp.group.request.FriendsRequest;
import com.sp.group.request.SubscribeBlockRequest;
import com.sp.group.request.UpdateRequest;
import com.sp.group.response.CustomResponse;
import com.sp.group.response.Views;
import com.sp.group.service.FriendsService;

@RestController
public class FriendsController {
    
    @Autowired
    private FriendsService friendsService;
    
	/**
	 * This API is used to drop exiting tables
	 * and creating them fresh
	 * @return CustomResponse
	 */
    @JsonView(Views.Connect.class)
	@RequestMapping(value="/init")
	public CustomResponse connect() {
		friendsService.initTables();
		return new CustomResponse();
	}

    /**
     * This API is used to connect two people
     * as friends
     * @param friends This parameter contains 2 email addresses
     * @return CustomResponse
     */
    @JsonView(Views.Connect.class)
	@RequestMapping(value="/connect", method=RequestMethod.POST)
	public CustomResponse connect(@RequestBody FriendsRequest friends) {
		friendsService.connect(friends.getFriends().get(0), friends.getFriends().get(1));
		return new CustomResponse();
	}
    
    /**
     * This API is used to get the list of 
     * friends of a person
     * @param emailRequest This parameter contains an email address
     * @return CustomResponse
     */
    @JsonView(Views.FriendList.class)
	@RequestMapping(value="/getFriendsList", method=RequestMethod.POST)
	public CustomResponse getFriendsList(@RequestBody EmailRequest emailRequest) {
		List<String> friendsList = friendsService.getFriendsList(emailRequest.getEmail());
		return new CustomResponse(true, friendsList, friendsList.size());
	}
    
    /**
     * This API is used to get the common 
     * friends of two people
     * @param friends This parameter contains 2 email addresses
     * @return CustomResponse
     */
    @JsonView(Views.FriendList.class)
	@RequestMapping(value="/getMutualFriends", method=RequestMethod.POST)
	public CustomResponse getMutualFriends(@RequestBody FriendsRequest friends) {
		List<String> friendsList = friendsService.getMutualFriends(friends.getFriends().get(0),
				friends.getFriends().get(1));
		return new CustomResponse(true, friendsList, friendsList.size());
	}
    
    /**
     * This API is used to subscribe to the 
     * updates from a person
     * @param subscribeRequest This parameter contains 2 email addresses, the requestor and the target
     * @return CustomResponse
     */
    @JsonView(Views.Connect.class)
	@RequestMapping(value="/subscribe", method=RequestMethod.POST)
	public CustomResponse subscribe(@RequestBody SubscribeBlockRequest subscribeRequest) {
		friendsService.subscribe(subscribeRequest.getRequestor(), subscribeRequest.getTarget());
		return new CustomResponse();
	}
    
    /**
     * This API is used to block the 
     * updates from a person
     * @param blockRequest This parameter contains 2 email addresses, the requestor and the target
     * @return CustomResponse
     */
    @JsonView(Views.Connect.class)
	@RequestMapping(value="/block", method=RequestMethod.POST)
	public CustomResponse block(@RequestBody SubscribeBlockRequest blockRequest) {
		friendsService.block(blockRequest.getRequestor(), blockRequest.getTarget());
		return new CustomResponse();
	}
    
    /**
     * This API is used to get the list of 
     * people who will receive the updates from a given person
     * @param updateRequest This parameter contains the sender email and the text
     * @return CustomResponse
     */
    @JsonView(Views.RecipientList.class)
	@RequestMapping(value="/send", method=RequestMethod.POST)
	public CustomResponse send(@RequestBody UpdateRequest updateRequest) {
    	List<String> recipientsList = friendsService.getUpdateRecipients(updateRequest.getSender(), updateRequest.getText());
		return new CustomResponse(true, recipientsList);
	}
}
