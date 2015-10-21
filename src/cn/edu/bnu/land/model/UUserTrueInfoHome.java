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
 * Home object for domain model class UUserTrueInfo.
 * @see cn.edu.bnu.land.model.UUserTrueInfo
 * @author Hibernate Tools
 */

@Repository
public class UUserTrueInfoHome {

	private static final Log log = LogFactory.getLog(UUserTrueInfoHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(UUserTrueInfo transientInstance) {
		log.debug("persisting UUserTrueInfo instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(UUserTrueInfo instance) {
		log.debug("attaching dirty UUserTrueInfo instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UUserTrueInfo persistentInstance) {
		log.debug("deleting UUserTrueInfo instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UUserTrueInfo merge(UUserTrueInfo detachedInstance) {
		log.debug("merging UUserTrueInfo instance");
		try {
			UUserTrueInfo result = (UUserTrueInfo) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UUserTrueInfo findById(int id) {
		log.debug("getting UUserTrueInfo instance with id: " + id);
		try {
			UUserTrueInfo instance = (UUserTrueInfo) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.UUserTrueInfo", id);
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

	public List findByExample(UUserTrueInfo instance) {
		log.debug("finding UUserTrueInfo instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.UUserTrueInfo")
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
