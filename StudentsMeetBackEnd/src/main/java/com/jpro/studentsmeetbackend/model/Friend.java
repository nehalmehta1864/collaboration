package com.jpro.studentsmeetbackend.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Friend extends BaseDomain {
	
	@Transient
	private String userID;
	
	private String friendId;
	
	private String friendStatus;
	
	private char isOnline;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getFriendStatus() {
		return friendStatus;
	}

	public void setFriendStatus(String friendStatus) {
		this.friendStatus = friendStatus;
	}

	public char getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(char isOnline) {
		this.isOnline = isOnline;
	}
	
}
