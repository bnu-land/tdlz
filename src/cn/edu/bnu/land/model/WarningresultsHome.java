package cn.edu.bnu.land.model;

// Generated 2013-9-29 11:13:48 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Warningresults.
 * @see cn.edu.bnu.land.model.Warningresults
 * @author Hibernate Tools
 */

@Repository
public class WarningresultsHome {

	private static final Log log = LogFactory.getLog(WarningresultsHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(Warningresults transientInstance) {
		log.debug("persisting Warningresults instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(Warningresults instance) {
		log.debug("attaching dirty Warningresults instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Warningresults persistentInstance) {
		log.debug("deleting Warningresults instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Warningresults merge(Warningresults detachedInstance) {
		log.debug("merging Warningresults instance");
		try {
			Warningresults result = (Warningresults) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Warningresults findById(java.lang.Integer id) {
		log.debug("getting Warningresults instance with id: " + id);
		try {
			Warningresults instance = (Warningresults) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.Warningresults", id);
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

	public List findByExample(Warningresults instance) {
		log.debug("finding Warningresults instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.Warningresults")
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
