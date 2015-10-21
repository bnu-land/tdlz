package cn.edu.bnu.land.model;

// Generated 2014-3-11 20:56:03 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class InfoArticle.
 * @see cn.edu.bnu.land.model.InfoArticle
 * @author Hibernate Tools
 */

@Repository
public class InfoArticleHome {

	private static final Log log = LogFactory.getLog(InfoArticleHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(InfoArticle transientInstance) {
		log.debug("persisting InfoArticle instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(InfoArticle instance) {
		log.debug("attaching dirty InfoArticle instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(InfoArticle persistentInstance) {
		log.debug("deleting InfoArticle instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InfoArticle merge(InfoArticle detachedInstance) {
		log.debug("merging InfoArticle instance");
		try {
			InfoArticle result = (InfoArticle) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public InfoArticle findById(java.lang.Integer id) {
		log.debug("getting InfoArticle instance with id: " + id);
		try {
			InfoArticle instance = (InfoArticle) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.InfoArticle", id);
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

	public List findByExample(InfoArticle instance) {
		log.debug("finding InfoArticle instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.InfoArticle")
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
