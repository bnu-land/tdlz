package cn.edu.bnu.land.model;

// Generated 2013-12-2 20:41:11 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class InfoLetter.
 * @see cn.edu.bnu.land.model.InfoLetter
 * @author Hibernate Tools
 */

@Repository
public class InfoLetterHome {

	private static final Log log = LogFactory.getLog(InfoLetterHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(InfoLetter transientInstance) {
		log.debug("persisting InfoLetter instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(InfoLetter instance) {
		log.debug("attaching dirty InfoLetter instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(InfoLetter persistentInstance) {
		log.debug("deleting InfoLetter instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InfoLetter merge(InfoLetter detachedInstance) {
		log.debug("merging InfoLetter instance");
		try {
			InfoLetter result = (InfoLetter) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public InfoLetter findById(java.lang.Integer id) {
		log.debug("getting InfoLetter instance with id: " + id);
		try {
			InfoLetter instance = (InfoLetter) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.InfoLetter", id);
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

	public List findByExample(InfoLetter instance) {
		log.debug("finding InfoLetter instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.InfoLetter")
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
