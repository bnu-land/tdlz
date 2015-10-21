package cn.edu.bnu.land.model;

// Generated 2013-12-4 10:49:40 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class InfoReport.
 * @see cn.edu.bnu.land.model.InfoReport
 * @author Hibernate Tools
 */

@Repository
public class InfoReportHome {

	private static final Log log = LogFactory.getLog(InfoReportHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(InfoReport transientInstance) {
		log.debug("persisting InfoReport instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(InfoReport instance) {
		log.debug("attaching dirty InfoReport instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(InfoReport persistentInstance) {
		log.debug("deleting InfoReport instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InfoReport merge(InfoReport detachedInstance) {
		log.debug("merging InfoReport instance");
		try {
			InfoReport result = (InfoReport) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public InfoReport findById(java.lang.Integer id) {
		log.debug("getting InfoReport instance with id: " + id);
		try {
			InfoReport instance = (InfoReport) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.InfoReport", id);
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

	public List findByExample(InfoReport instance) {
		log.debug("finding InfoReport instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.InfoReport")
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
