package cn.edu.bnu.land.model;

// Generated 2014-4-24 21:36:10 by Hibernate Tools 4.0.0

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Home object for domain model class DkMonitordata.
 * @see cn.edu.bnu.land.model.DkMonitordata
 * @author Hibernate Tools
 */
@Repository
public class DkMonitordataHome {
	private static final Log log = LogFactory.getLog(DkMonitordataHome.class);
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		}
		catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}
	public void persist(DkMonitordata transientInstance) {
		log.debug("persisting DkMonitordata instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DkMonitordata instance) {
		log.debug("attaching dirty DkMonitordata instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DkMonitordata instance) {
		log.debug("attaching clean DkMonitordata instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DkMonitordata persistentInstance) {
		log.debug("deleting DkMonitordata instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DkMonitordata merge(DkMonitordata detachedInstance) {
		log.debug("merging DkMonitordata instance");
		try {
			DkMonitordata result = (DkMonitordata) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DkMonitordata findById(java.lang.Integer id) {
		log.debug("getting DkMonitordata instance with id: " + id);
		try {
			DkMonitordata instance = (DkMonitordata) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.DkMonitordata", id);
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

	public List findByExample(DkMonitordata instance) {
		log.debug("finding DkMonitordata instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.DkMonitordata")
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
