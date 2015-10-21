package cn.edu.bnu.land.model;

// Generated 2014-3-5 10:07:58 by Hibernate Tools 4.0.0

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
 * Home object for domain model class RssAreaCode.
 * @see cn.edu.bnu.land.model.RssAreaCode
 * @author Hibernate Tools
 */
	@Repository
	public class RssAreaCodeHome {
		private static final Log log = LogFactory.getLog(RssAreaCodeHome.class);
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


	public void persist(RssAreaCode transientInstance) {
		log.debug("persisting RssAreaCode instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RssAreaCode instance) {
		log.debug("attaching dirty RssAreaCode instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RssAreaCode instance) {
		log.debug("attaching clean RssAreaCode instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RssAreaCode persistentInstance) {
		log.debug("deleting RssAreaCode instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RssAreaCode merge(RssAreaCode detachedInstance) {
		log.debug("merging RssAreaCode instance");
		try {
			RssAreaCode result = (RssAreaCode) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RssAreaCode findById(java.lang.Integer id) {
		log.debug("getting RssAreaCode instance with id: " + id);
		try {
			RssAreaCode instance = (RssAreaCode) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.RssAreaCode", id);
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

	public List findByExample(RssAreaCode instance) {
		log.debug("finding RssAreaCode instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.RssAreaCode")
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
