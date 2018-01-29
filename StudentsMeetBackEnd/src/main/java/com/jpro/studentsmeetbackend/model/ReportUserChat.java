package com.jpro.studentsmeetbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name="STMT_CHATREPORTS")
public class ReportUserChat extends BaseDomain {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long chat_reportId;
	
	private long chatID;
	
	private String userID;
	
	public long getChat_reportId() {
		return chat_reportId;
	}

	public void setChat_reportId(long chat_reportId) {
		this.chat_reportId = chat_reportId;
	}

	public long getChatID() {
		return chatID;
	}

	public void setChatID(long chatID) {
		this.chatID = chatID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}
