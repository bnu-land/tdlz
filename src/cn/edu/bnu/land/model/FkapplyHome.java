package cn.edu.bnu.land.model;

// Generated 2013-9-8 16:59:35 by Hibernate Tools 4.0.0

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Home object for domain model class Fkapply.
 * @see dao.Fkapply
 * @author Hibernate Tools
 */
@Repository
public class FkapplyHome {

	private static final Log log = LogFactory.getLog(FkapplyHome.class);

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
	public void save(String projectId,String projectName,String applyConUnit,String fileCheck,String applyNote) {
		System.out.print("projectId:"+projectId+"projectName:"+projectName);
		Calendar ca=Calendar.getInstance();
		int s=ca.get(Calendar.DATE);
		//DateFormat format = new SimpleDateFormat("yyyyMMdd");
		// 转换为字符串
		//String formatDate = format.format(new Date());
		//String S=new StringBuffer().append(formatDate).toString();
		String applyId=projectId+s;		
     	Date applyDate = ca.getTime();
		//Date applyDate = new Date();// new Date()为获取当前系统时间
		
		//SessionFactory sf = new Configuration().configure().buildSessionFactory(); 
		//Session session = sf.openSession(); 
		//Transaction tx = session.beginTransaction(); 
		Fkapply st=new Fkapply(); 
		st.setApplyId(applyId);
		st.setProjectId(projectId);
		st.setProjectname(projectName);
		st.setFileCheck(fileCheck);
		st.setCheckResult("未审核");
		st.setConUnit(applyConUnit);
		st.setApplyDate(applyDate);
		st.setNote(applyNote);
		try{			 
			sessionFactory.getCurrentSession().save(st);
		}catch(Exception e){
			e.printStackTrace();
		}			
		//Session session = sessionFactory.getCurrentSession();
		//session.save(fkapply);
	}
	public List<Fkapply> select(){
		System.out.print("Fkapply is called");
		String hql="from Fkapply as fkapply";
		List<Fkapply> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Fkapply>)query.list();
			for(Fkapply fkapply:results)
			{
				System.out.println(fkapply.getApplyId());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
	
	public void persist(Fkapply transientInstance) {
		log.debug("persisting Fkapply instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Fkapply instance) {
		log.debug("attaching dirty Fkapply instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fkapply instance) {
		log.debug("attaching clean Fkapply instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Fkapply persistentInstance) {
		log.debug("deleting Fkapply instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fkapply merge(Fkapply detachedInstance) {
		log.debug("merging Fkapply instance");
		try {
			Fkapply result = (Fkapply) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Fkapply findById(java.lang.Integer id) {
		log.debug("getting Fkapply instance with id: " + id);
		try {
			Fkapply instance = (Fkapply) sessionFactory.getCurrentSession()
					.get("dao.Fkapply", id);
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

	public List findByExample(Fkapply instance) {
		log.debug("finding Fkapply instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("dao.Fkapply")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	/*
	 *Fk apply select function 
	 * 
	 * */
	public Map<String, Object> select(String start, String limit,String searchKeyword){		
		String totalConut = new String();
		String hql;
		if(!searchKeyword.equals("") && !searchKeyword.isEmpty()){
			hql = "from Fkapply as fkapply where fkapply.applyId like '%"+searchKeyword+"%' and checkResult='未审核'";	
		}
		//查询关键词为空
		else {			
			hql="from Fkapply as fkapply where checkResult='未审核'";
		}
		
		List<Fkapply> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			/*if(!query.list().isEmpty())
				totalConut = String.valueOf(query.list().size());
			    query.setFirstResult(Integer.parseInt(start));
			    query.setMaxResults(Integer.parseInt(limit));*/
			totalConut = String.valueOf(query.list().size());
			System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));	
			
			results=(List<Fkapply>)query.list();
			for(Fkapply fkapply:results)
			{
				System.out.println(fkapply.getApplyId());
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);
		return myMapResult;
	}
	
	/*
	 *Fk apply select check function 
	 * 
	 * */
	public Map<String, Object> selectCheck(String start, String limit,String searchKeyword){		
		String totalConut = new String();
		String hql;
		if(!searchKeyword.equals("") && !searchKeyword.isEmpty()){
			hql = "from Fkapply as fkapply where fkapply.applyId like '%"+searchKeyword+"%' and (checkResult='同意验收' or checkResult='不同意验收')";	
		}
		//查询关键词为空
		else {			
			hql="from Fkapply as fkapply where checkResult='同意验收' or checkResult='不同意验收' ";
		}
		
		List<Fkapply> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			/*if(!query.list().isEmpty())
				totalConut = String.valueOf(query.list().size());
			    query.setFirstResult(Integer.parseInt(start));
			    query.setMaxResults(Integer.parseInt(limit));*/
			totalConut = String.valueOf(query.list().size());
			System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));	
			
			results=(List<Fkapply>)query.list();
			for(Fkapply fkapply:results)
			{
				System.out.println(fkapply.getApplyId());
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);
		return myMapResult;
	}
	
	
	/*
	 * delete applyByIds
	 * 2013-12-20 @WW
	 * */	 
	@Transactional
	public void del_applyByIds(String[] applyIds) {
		Fkapply result = null;
		Session session = sessionFactory.getCurrentSession();
		System.out.println("期望得到："+applyIds);
		try {

			for (int i = 0; i < applyIds.length; i++) {
				result = (Fkapply) session.get(Fkapply.class,
						applyIds[i]);
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
