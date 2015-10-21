package cn.edu.bnu.land.model;

// Generated 2014-5-13 17:06:56 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class AbnmWholesupervision.
 * @see cn.edu.bnu.land.model.AbnmWholesupervision
 * @author Hibernate Tools
 */

@Repository
public class AbnmWholesupervisionHome {

	private static final Log log = LogFactory
			.getLog(AbnmWholesupervisionHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(AbnmWholesupervision transientInstance) {
		log.debug("persisting AbnmWholesupervision instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(AbnmWholesupervision instance) {
		log.debug("attaching dirty AbnmWholesupervision instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(AbnmWholesupervision persistentInstance) {
		log.debug("deleting AbnmWholesupervision instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AbnmWholesupervision merge(AbnmWholesupervision detachedInstance) {
		log.debug("merging AbnmWholesupervision instance");
		try {
			AbnmWholesupervision result = (AbnmWholesupervision) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public AbnmWholesupervision findById(java.lang.Integer id) {
		log.debug("getting AbnmWholesupervision instance with id: " + id);
		try {
			AbnmWholesupervision instance = (AbnmWholesupervision) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.AbnmWholesupervision", id);
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

	public List findByExample(AbnmWholesupervision instance) {
		log.debug("finding AbnmWholesupervision instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"cn.edu.bnu.land.model.AbnmWholesupervision")
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
