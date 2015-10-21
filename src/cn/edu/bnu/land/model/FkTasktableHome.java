package cn.edu.bnu.land.model;

// Generated 2013-9-8 16:59:35 by Hibernate Tools 4.0.0

import java.util.List;

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
 * Home object for domain model class FkTasktable.
 * @see dao.FkTasktable
 * @author Hibernate Tools
 */
@Repository
public class FkTasktableHome {

	private static final Log log = LogFactory.getLog(FkTasktableHome.class);

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
	/*Add task function
	 * 2013-12-21 @WW
	 * */
	public void save(FkTasktable fkTasktable) {
		Session session = sessionFactory.getCurrentSession();
		session.save(fkTasktable);
	}
	public List<FkTasktable> select(){
		System.out.print("FkTasktable home_before");
		String hql="from FkTasktable as fkTasktable";
		List<FkTasktable> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<FkTasktable>)query.list();
			for(FkTasktable fkTasktable:results)
			{
				System.out.println(fkTasktable.getTaskId());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}

	public void persist(FkTasktable transientInstance) {
		log.debug("persisting FkTasktable instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(FkTasktable instance) {
		log.debug("attaching dirty FkTasktable instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FkTasktable instance) {
		log.debug("attaching clean FkTasktable instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(FkTasktable persistentInstance) {
		log.debug("deleting FkTasktable instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FkTasktable merge(FkTasktable detachedInstance) {
		log.debug("merging FkTasktable instance");
		try {
			FkTasktable result = (FkTasktable) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public FkTasktable findById(java.lang.Integer id) {
		log.debug("getting FkTasktable instance with id: " + id);
		try {
			FkTasktable instance = (FkTasktable) sessionFactory
					.getCurrentSession().get("dao.FkTasktable", id);
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

	public List findByExample(FkTasktable instance) {
		log.debug("finding FkTasktable instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("dao.FkTasktable")
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
	 * deleteTaskByIds
	 * 2013-12-20 @WW
	 * */	 
	@Transactional
	public void del_taskByIds(String[] taskIds) {
		FkTasktable result = null;
		Session session = sessionFactory.getCurrentSession();
		System.out.println(taskIds);
		try {

			for (int i = 0; i < taskIds.length; i++) {
				result = (FkTasktable) session.get(FkTasktable.class,
						Integer.parseInt(taskIds[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
