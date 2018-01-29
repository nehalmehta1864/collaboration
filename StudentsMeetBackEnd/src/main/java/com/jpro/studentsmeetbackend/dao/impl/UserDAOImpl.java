package com.jpro.studentsmeetbackend.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jpro.studentsmeetbackend.dao.UserDAO;
import com.jpro.studentsmeetbackend.model.Friend;
import com.jpro.studentsmeetbackend.model.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {
	
	private static final Logger log=LoggerFactory.getLogger(UserDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	UserDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}

	public boolean createUser(User user) {
		try{
			user.setLastSeenOnline(new Date());
			user.setUserRole("ROLE_USER");
			sessionFactory.getCurrentSession().save(user);
			return true;
		}
		catch(Exception e){
			System.err.println(e);
			return false;
		}
	}

	public boolean updateUser(User user) {
		try{
			sessionFactory.getCurrentSession().update(user);
			return true;
		}
		catch(Exception e){
			System.err.println(e);
			return false;
		}
	}

	public User getUserById(String userID) {
		return  sessionFactory.getCurrentSession().get(User.class, userID);
	}

	public List<User> getAllUsers() {
		System.out.println("User list "+sessionFactory.getCurrentSession().createQuery("from User").list().size());
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	public List<Friend> getAllFriendsOfUser(String userID) {
		User user=(User) getUserById(userID);
		return user.getUserFriends();
	}
	
	public List<Friend> getFriendsOfUser(String userID){
		String sql="select * from STMT_USER_FRIENDS where USERID=:userID and FRIENDSTATUS='Friend'";
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("userID", userID);
		List<Object> queryList=query.list();
		List<Friend> returnList=new ArrayList<Friend>();
		Friend frd;
		for(int i=0;i<queryList.size();i++){
			frd=new Friend();
			Object[] obj=(Object[]) queryList.get(i);
			frd.setUserID((String) obj[0]);
			frd.setFriendId((String) obj[1]);
			frd.setFriendStatus((String)obj[2]);
			frd.setIsOnline(obj[3].toString().charAt(0));
			returnList.add(frd);
		}
		return returnList;
	}

	public List<Friend> getFriendsReqOfUser(String userID){
		String sql="select * from STMT_USER_FRIENDS where USERID=:userID and FRIENDSTATUS='Request received'";
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("userID", userID);
		List<Object> queryList=query.list();
		List<Friend> returnList=new ArrayList<Friend>();
		Friend frd;
		for(int i=0;i<queryList.size();i++){
			frd=new Friend();
			Object[] obj=(Object[]) queryList.get(i);
			frd.setUserID((String) obj[0]);
			frd.setFriendId((String) obj[1]);
			frd.setFriendStatus((String)obj[2]);
			frd.setIsOnline(obj[3].toString().charAt(0));
			returnList.add(frd);
		}
		return returnList;
	}
    
	
	
	public boolean userValidate(String userID, String userPassword) {
			User user=getUserById(userID);
			if(userPassword.equals(user.getUserPassword())){
				SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery("update STMT_USER_FRIENDS set ISONLINE='Y' where FRIENDID=:userID");
				query.setParameter("userID", user.getUserID());
				query.executeUpdate();
				user.setIsOnline('Y');
				sessionFactory.getCurrentSession().update(user);
				return true;
			}
			else{
				return false;
			}			
	}

	public boolean sendFriendRequest(String senderID, String receiverId) {
		try
		{
			
		User receiveUser=(User) sessionFactory.getCurrentSession().createQuery("from User where userID='"+receiverId+"'").uniqueResult();
		Friend friend=new Friend();
		friend.setFriendId(senderID);
		friend.setFriendStatus("Request received");
		friend.setIsOnline('N');
		receiveUser.getUserFriends().add(friend);
		sessionFactory.getCurrentSession().update(receiveUser);
	
		return true;
	 
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public boolean acceptFriendRequest(String receiverId, String senderID) {
		try {
			String sql="delete from STMT_USER_FRIENDS where USERID=:receiverID and FRIENDID=:senderId";
			SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameter("senderId", senderID);
			query.setParameter("receiverID", receiverId);
			query.executeUpdate();
		User receiveUser=(User) sessionFactory.getCurrentSession().createQuery("from User where userID='"+receiverId+"'").uniqueResult();
		User sendUser=(User) sessionFactory.getCurrentSession().createQuery("from User where userID='"+senderID+"'").uniqueResult();
	
		Friend sfriend=new Friend();
		sfriend.setFriendId(senderID);
		sfriend.setIsOnline('N');
	
		sfriend.setFriendStatus("Friend");
		receiveUser.getUserFriends().add(sfriend);
		Friend rfriend=new Friend();
		rfriend.setFriendId(receiverId);
		rfriend.setIsOnline('N');
		rfriend.setFriendStatus("Friend");
		sendUser.getUserFriends().add(rfriend);
	
		updateUser(receiveUser);
		updateUser(sendUser);
		return true;
		}
		catch(Exception e)
		{
			
			return false;
		}

	}

	public boolean rejectRequest(String senderId, String receiverID) {
		try
		{
			System.out.println("Testing "+senderId +" " + receiverID); 
			String sql="delete from STMT_USER_FRIENDS where USERID=:senderId and FRIENDID=:receiverID";
			SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameter("senderId", senderId);
			query.setParameter("receiverID", receiverID);
			query.executeUpdate();
	
		return true;
		}
		catch(Exception e){
			
			return false;
		}
	}

	public boolean setUserOffline(String userID) {
		try{
			User user=(User) sessionFactory.getCurrentSession().createQuery("from User where userID='"+userID+"'").uniqueResult();
			SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery("update STMT_USER_FRIENDS set ISONLINE='N' where FRIENDID=:userID");
			query.setParameter("userID", user.getUserID());
			query.executeUpdate();
			user.setIsOnline('N');
			user.setLastSeenOnline(new Date());
					updateUser(user);
			return true;
			}
			catch(Exception e){
			
				return false;
			}
	}


}
