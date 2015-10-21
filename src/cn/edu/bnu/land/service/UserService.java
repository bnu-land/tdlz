package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import cn.edu.bnu.land.model.Syncpush;
//import cn.edu.bnu.land.model.SyncpushHome;
import cn.edu.bnu.land.model.User;
import cn.edu.bnu.land.model.UserHome;

@Service
public class UserService {
	
	private UserHome userHome;
	//private SyncpushHome syncpushHome;
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Autowired
	public void setUserHome(UserHome userHome){
		this.userHome=userHome;
	}
	
	public void addUser(User user){
		this.userHome.save(user);
	}
	
//	@Autowired
//	public void setSyncpushHome(SyncpushHome syncpushHome){
//		this.syncpushHome=syncpushHome;
//	}
//	
//	public  Map<String,Object> selectSyncPushTb(String start,String limit,String searchField,String dateType,String searchDate){
//		return this.syncpushHome.selectTb(start,limit,searchField,dateType,searchDate);
//	}
//	
//	public  Map<String,Object> selectMailPushTb(String start,String limit,String searchField,String dateType,String searchDate){
//		return this.syncpushHome.selectMailPushTb(start,limit,searchField,dateType,searchDate);
//	}
//	
//	public Syncpush selectSpById(int id){
//		return this.syncpushHome.selectById(id);
//	}
	
	public List<User> getAllUser(){
	    return this.userHome.getAll();
	}
	
	
	public Map<String,Object> getSelectUsers(String start,String limit,String searchField){
	    return this.userHome.getSelectUsers(start,limit,searchField);
	}
	
	
	/*
	 * 根据id号将Syncpush表中的isMPush字段设为1
	 */
	public void updateMailPushTrue(String ids){
		String[] idsArray=ids.split(",");
		for (int i=0;i<idsArray.length;i++){
			String hql="update Syncpush set isMailPush=1 where id="+idsArray[i];
			Session session = sessionFactory.getCurrentSession();
			Query q=session.createQuery(hql);
			q.executeUpdate();
		}
	}

	/*
	 * 根据id号将Syncpush表中的isPush字段设为1
	 */
	public void updateSyncPushTrue(String ids){
		String[] idsArray=ids.split(",");
		for (int i=0;i<idsArray.length;i++){
			String hql="update Syncpush set isPush=1 where id="+idsArray[i];
			Session session = sessionFactory.getCurrentSession();
			Query q=session.createQuery(hql);
			q.executeUpdate();
		}
	}
	
}
