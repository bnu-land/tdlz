package cn.edu.bnu.land.model;

// Generated 2014-4-4 15:27:03 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class AbnmDktranssupervision.
 * @see cn.edu.bnu.land.model.AbnmDktranssupervision
 * @author Hibernate Tools
 */

@Repository
public class AbnmDktranssupervisionHome {

	private static final Log log = LogFactory
			.getLog(AbnmDktranssupervisionHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(AbnmDktranssupervision transientInstance) {
		log.debug("persisting AbnmDktranssupervision instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(AbnmDktranssupervision instance) {
		log.debug("attaching dirty AbnmDktranssupervision instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(AbnmDktranssupervision persistentInstance) {
		log.debug("deleting AbnmDktranssupervision instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AbnmDktranssupervision merge(AbnmDktranssupervision detachedInstance) {
		log.debug("merging AbnmDktranssupervision instance");
		try {
			AbnmDktranssupervision result = (AbnmDktranssupervision) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public AbnmDktranssupervision findById(java.lang.Integer id) {
		log.debug("getting AbnmDktranssupervision instance with id: " + id);
		try {
			AbnmDktranssupervision instance = (AbnmDktranssupervision) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.AbnmDktranssupervision", id);
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

	public List findByExample(AbnmDktranssupervision instance) {
		log.debug("finding AbnmDktranssupervision instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"cn.edu.bnu.land.model.AbnmDktranssupervision")
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
