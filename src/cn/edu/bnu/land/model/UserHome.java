package cn.edu.bnu.land.model;

// Generated 2013-8-19 16:49:52 by Hibernate Tools 4.0.0

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class User.
 * @see cn.edu.bnu.land.model.User
 * @author Hibernate Tools
 */
@Repository
public class UserHome {

	private static final Log log = LogFactory.getLog(UserHome.class);

	
	private SessionFactory sessionFactory;
	@Autowired
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
	public void save(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}
	
	public void persist(User transientInstance) {
		log.debug("persisting User instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) sessionFactory.getCurrentSession().get(
					"cn.edu.bnu.land.model.User", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<User> findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List<User> results = sessionFactory
					.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.User")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public List<User> getAll(){
		String hql="from User u where 1=1 ";
		List<User> results=null;
		org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
		results=query.list();
		return results;
	}
	/*
	 * 函数功能：获取所有注册用户和搜索符合条件用户       //地票信息异步推送时选择接收对象时需要
	 * 参数说明:start 表格分页参数 起始记录号
	 * 参数说明：limit 表格分页参数 每页记录数
	 * 参数说明：searchField 搜索关键词
	 * 返回值说明： map键值对，total键，查询到的总记录数;root键，记录内容。键名与store中的reader设置相对应
	 * 函数逻辑：searchField为空，查询全部 ，否则按查询关键词先进行精确检索，无结果，再进行模糊检索
	 * */
	public Map<String,Object> getSelectUsers(String start,String limit,String searchField){
		String hql=" from User ";
		System.out.println(searchField);
		List<User> results=null;
		int count=0;
		//查询全部
		if(searchField.equals("")||searchField.isEmpty())
		{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			count=query.list().size();
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results=query.list();
		}
		else{
			//精确检索
			searchField="'"+searchField+"'";
			hql=" from User user where user.username="+searchField+
					" or user.name="+searchField+
					" or user.email="+searchField+
					" or user.phone="+searchField+
					" or user.cardId="+searchField+
					" or user.lawmanName="+searchField+
					" or user.companyName="+searchField;
			System.out.println(hql);
			org.hibernate.Query query1=sessionFactory.getCurrentSession().createQuery(hql);
			if(!query1.list().isEmpty()){
				query1.setFirstResult(Integer.parseInt(start));
				query1.setMaxResults(Integer.parseInt(limit));
				results=query1.list();
				count=query1.list().size();		
			}
			else{//模糊检索
				String text="'%"+searchField+"%'";
				hql=" from User user where user.username  like "+text+
						" or user.name like "+text+
						" or user.email like"+text+
						" or user.phone like"+text+
						" or user.cardId like"+text+
						" or user.lawmanName like"+text+
						" or user.companyName like"+text;
				System.out.println(hql);
				org.hibernate.Query query2=sessionFactory.getCurrentSession().createQuery(hql);
				count=query2.list().size();
				query2.setFirstResult(Integer.parseInt(start));
				query2.setMaxResults(Integer.parseInt(limit));
				results=query2.list();
			}
			
			
		}
		System.out.println("hql： "+hql);
		Map<String,Object> myMapResult= new TreeMap<String,Object>();   
	    System.out.println("记录总数： "+count);
	    myMapResult.put("total", count);
	    myMapResult.put("root",results);
		
		return myMapResult;
	}
	
}
