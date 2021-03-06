package cn.edu.bnu.land.model;

// Generated 2014-3-3 21:15:50 by Hibernate Tools 4.0.0

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
 * Home object for domain model class ProjectDk.
 * @see cn.edu.bnu.land.model.ProjectDk
 * @author Hibernate Tools
 */
@Repository
	public class ProjectDkHome {
		private static final Log log = LogFactory.getLog(ProjectAllHome.class);
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

	public void persist(ProjectDk transientInstance) {
		log.debug("persisting ProjectDk instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ProjectDk instance) {
		log.debug("attaching dirty ProjectDk instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ProjectDk instance) {
		log.debug("attaching clean ProjectDk instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ProjectDk persistentInstance) {
		log.debug("deleting ProjectDk instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ProjectDk merge(ProjectDk detachedInstance) {
		log.debug("merging ProjectDk instance");
		try {
			ProjectDk result = (ProjectDk) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProjectDk findById(java.lang.String id) {
		log.debug("getting ProjectDk instance with id: " + id);
		try {
			ProjectDk instance = (ProjectDk) sessionFactory.getCurrentSession()
					.get("cn.edu.bnu.land.model.ProjectDk", id);
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

	public List findByExample(ProjectDk instance) {
		log.debug("finding ProjectDk instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.ProjectDk")
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
