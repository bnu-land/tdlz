package cn.edu.bnu.land.model;

// Generated 2014-3-6 12:57:38 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class AbnmIndex.
 * @see cn.edu.bnu.land.model.AbnmIndex
 * @author Hibernate Tools
 */

@Repository
public class AbnmIndexHome {

	private static final Log log = LogFactory.getLog(AbnmIndexHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(AbnmIndex transientInstance) {
		log.debug("persisting AbnmIndex instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(AbnmIndex instance) {
		log.debug("attaching dirty AbnmIndex instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(AbnmIndex persistentInstance) {
		log.debug("deleting AbnmIndex instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AbnmIndex merge(AbnmIndex detachedInstance) {
		log.debug("merging AbnmIndex instance");
		try {
			AbnmIndex result = (AbnmIndex) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public AbnmIndex findById(java.lang.Integer id) {
		log.debug("getting AbnmIndex instance with id: " + id);
		try {
			AbnmIndex instance = (AbnmIndex) sessionFactory.getCurrentSession()
					.get("cn.edu.bnu.land.model.AbnmIndex", id);
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

	public List findByExample(AbnmIndex instance) {
		log.debug("finding AbnmIndex instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.AbnmIndex")
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
