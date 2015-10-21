package cn.edu.bnu.land.model;

// Generated 2014-5-6 9:39:29 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class URoleInfo.
 * @see cn.edu.bnu.land.model.URoleInfo
 * @author Hibernate Tools
 */

@Repository
public class URoleInfoHome {

	private static final Log log = LogFactory.getLog(URoleInfoHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(URoleInfo transientInstance) {
		log.debug("persisting URoleInfo instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(URoleInfo instance) {
		log.debug("attaching dirty URoleInfo instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(URoleInfo persistentInstance) {
		log.debug("deleting URoleInfo instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public URoleInfo merge(URoleInfo detachedInstance) {
		log.debug("merging URoleInfo instance");
		try {
			URoleInfo result = (URoleInfo) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public URoleInfo findById(java.lang.Integer id) {
		log.debug("getting URoleInfo instance with id: " + id);
		try {
			URoleInfo instance = (URoleInfo) sessionFactory.getCurrentSession()
					.get("cn.edu.bnu.land.model.URoleInfo", id);
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

	public List findByExample(URoleInfo instance) {
		log.debug("finding URoleInfo instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.URoleInfo")
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
