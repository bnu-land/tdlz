package cn.edu.bnu.land.model;

// Generated 2013-9-8 16:59:35 by Hibernate Tools 4.0.0

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Home object for domain model class FkSamplemanagement.
 * @see dao.FkSamplemanagement
 * @author Hibernate Tools
 */
@Repository
public class FkSamplemanagementHome {

	private static final Log log = LogFactory
			.getLog(FkSamplemanagementHome.class);

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

	public void persist(FkSamplemanagement transientInstance) {
		log.debug("persisting FkSamplemanagement instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	public void save(FkSamplemanagement fkSamplemanagement) {
		Session session = sessionFactory.getCurrentSession();
		session.save(fkSamplemanagement);
	}
	/*select sample  
	 * @WW 2013-12-21
	 * */
	public Map<String, Object> select(String start, String limit,String searchKeyword){		
		String totalConut = new String();
		String hql;
		if(!searchKeyword.equals("") && !searchKeyword.isEmpty()){
			hql = "from FkSamplemanagement as fkSamplemanagement where fkSamplemanagement.projectId like '%"+searchKeyword+"%'";	
		}
		//查询关键词为空
		else {			
			hql="from FkSamplemanagement as fkSamplemanagement";
		}
		
		List<FkSamplemanagement> results=null;
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
			
			results=(List<FkSamplemanagement>)query.list();
			for(FkSamplemanagement fkSamplemanagement:results)
			{
				System.out.println(fkSamplemanagement.getProjectId());
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
	public List<FkSamplemanagement> select(){
		System.out.print("FkSamplemanagement home_before");
		String hql="from FkSamplemanagement as fkSamplemanagement";
		List<FkSamplemanagement> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<FkSamplemanagement>)query.list();
			for(FkSamplemanagement fkSamplemanagement:results)
			{
				System.out.println(fkSamplemanagement.getProjectId());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	*/
	
	public void attachDirty(FkSamplemanagement instance) {
		log.debug("attaching dirty FkSamplemanagement instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FkSamplemanagement instance) {
		log.debug("attaching clean FkSamplemanagement instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(FkSamplemanagement persistentInstance) {
		log.debug("deleting FkSamplemanagement instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FkSamplemanagement merge(FkSamplemanagement detachedInstance) {
		log.debug("merging FkSamplemanagement instance");
		try {
			FkSamplemanagement result = (FkSamplemanagement) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public FkSamplemanagement findById(int id) {
		log.debug("getting FkSamplemanagement instance with id: " + id);
		try {
			FkSamplemanagement instance = (FkSamplemanagement) sessionFactory
					.getCurrentSession().get("dao.FkSamplemanagement", id);
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

	public List findByExample(FkSamplemanagement instance) {
		log.debug("finding FkSamplemanagement instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.FkSamplemanagement")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	/*edit project function
	 * 2013-12-20 @WW
	 * */
	public FkSamplemanagement getSampleById(String sampleId) {
		FkSamplemanagement result = null;
		Session session = sessionFactory.getCurrentSession();

		try {
				result = (FkSamplemanagement) session.get(FkSamplemanagement.class,
						Integer.parseInt(sampleId));				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}	
	/*TIANjia pingjiazhibiao
	 * 2013-12-20 @WW
	 * */
	public FkSamplemanagement getSampleById_E(String projectId) {
		FkSamplemanagement result = null;
		Session session = sessionFactory.getCurrentSession();

		try {
				result = (FkSamplemanagement) session.get(FkSamplemanagement.class,
						Integer.parseInt("14"));				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}
	
	
	/*
	 * deleteprojectByIds
	 * 2013-12-20 @WW
	 * */	 
	@Transactional
	public void del_sampleByIds(String[] sampleIds) {
		FkSamplemanagement result = null;
		Session session = sessionFactory.getCurrentSession();
		System.out.println(sampleIds);
		try {

			for (int i = 0; i < sampleIds.length; i++) {
				result = (FkSamplemanagement) session.get(FkSamplemanagement.class,
						Integer.parseInt(sampleIds[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
