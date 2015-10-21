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

/**
 * Home object for domain model class Evaluationindextable.
 * @see cn.edu.bnu.land.Evaluationindextable
 * @author Hibernate Tools
 */
@Repository
public class EvaluationindextableHome {

	private static final Log log = LogFactory
			.getLog(EvaluationindextableHome.class);

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
	//吧数据存入到评价指标表格
	public void save(Evaluationindextable evaluationindextable) {
		Session session = sessionFactory.getCurrentSession();
		session.save(evaluationindextable);
	}
	//查询函数列表，评价指标输入表格
	public List<Evaluationindextable> select(){
		System.out.print("Evaluationindextable called");
		String hql="from Evaluationindextable as evaluationindextable";
		List<Evaluationindextable> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Evaluationindextable>)query.list();
			for(Evaluationindextable evaluationindextable:results)
			{
				System.out.println(evaluationindextable.getProjectId());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
	
	public void persist(Evaluationindextable transientInstance) {
		log.debug("persisting Evaluationindextable instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Evaluationindextable instance) {
		log.debug("attaching dirty Evaluationindextable instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Evaluationindextable instance) {
		log.debug("attaching clean Evaluationindextable instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Evaluationindextable persistentInstance) {
		log.debug("deleting Evaluationindextable instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Evaluationindextable merge(Evaluationindextable detachedInstance) {
		log.debug("merging Evaluationindextable instance");
		try {
			Evaluationindextable result = (Evaluationindextable) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Evaluationindextable findById(java.lang.Integer id) {
		log.debug("getting Evaluationindextable instance with id: " + id);
		try {
			Evaluationindextable instance = (Evaluationindextable) sessionFactory
					.getCurrentSession().get("dao.Evaluationindextable", id);
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

	public List findByExample(Evaluationindextable instance) {
		log.debug("finding Evaluationindextable instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("dao.Evaluationindextable")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
