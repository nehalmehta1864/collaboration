package com.jpro.studentsmeetbackend.dao;

import java.util.List;

import com.jpro.studentsmeetbackend.model.ReportUserChat;

public interface ReportChatDAO {
	
	public boolean createReportChat(ReportUserChat reportUserChat);
	
	public boolean removeReportChat(ReportUserChat reportUserChat);
	
	public boolean removeReportChat(long chat_reportId);
	
	public boolean removeByReportChat(long chat_reportId);
	
	public List<ReportUserChat> getAllReportsChat();
	
	public ReportUserChat getReportChatById(long chat_reportId);

}
