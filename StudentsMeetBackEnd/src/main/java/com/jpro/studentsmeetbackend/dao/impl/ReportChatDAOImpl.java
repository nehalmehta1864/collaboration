package com.jpro.studentsmeetbackend.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jpro.studentsmeetbackend.dao.ReportChatDAO;
import com.jpro.studentsmeetbackend.model.ReportUserChat;

@Transactional
@Repository("reportChatDAO")
public class ReportChatDAOImpl implements ReportChatDAO {
	
	private static final Logger log=LoggerFactory.getLogger(ReportChatDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	ReportChatDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}

	public boolean createReportChat(ReportUserChat reportUserChat) {
		try{
			sessionFactory.getCurrentSession().save(reportUserChat);
			return true;
		}
		catch(Exception e){
			System.err.println(e);
			return false;
		}
	}

	public boolean removeReportChat(ReportUserChat reportUserChat) {
		try{
			sessionFactory.getCurrentSession().delete(reportUserChat);
			return true;
		}
		catch(Exception e){
			System.err.println(e);
			return false;
		}
	}

	public boolean removeReportChat(long chat_reportId) {
		try{
			sessionFactory.getCurrentSession().save(getReportChatById(chat_reportId));
			return true;
		}
		catch(Exception e){
			System.err.println(e);
			return false;
		}
	}

	public List<ReportUserChat> getAllReportsChat() {
		return sessionFactory.getCurrentSession().createQuery("from ReportUserChat").list();
	}

	public ReportUserChat getReportChatById(long chat_reportId) {
		return (ReportUserChat) sessionFactory.getCurrentSession().createQuery("from ReportUserChat where chat_reportId").uniqueResult();
	}

	public boolean removeByReportChat(long chat_reportId) {
		ReportUserChat rep=(ReportUserChat) sessionFactory.getCurrentSession().createQuery("from ReportUserChat where chatID='"+chat_reportId+"'").list().get(0);
		try{
			removeReportChat(rep);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

}
