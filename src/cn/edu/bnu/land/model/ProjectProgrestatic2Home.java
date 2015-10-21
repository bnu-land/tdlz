package cn.edu.bnu.land.model;

// Generated 2014-3-26 8:54:54 by Hibernate Tools 4.0.0

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
 * Home object for domain model class ProjectProgrestatic2.
 * @see cn.edu.bnu.land.model.ProjectProgrestatic2
 * @author Hibernate Tools
 */

@Repository
public class ProjectProgrestatic2Home {
	private static final Log log = LogFactory.getLog(ProjectProgrestatic2Home.class);
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
	public void persist(ProjectProgrestatic2 transientInstance) {
		log.debug("persisting ProjectProgrestatic2 instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ProjectProgrestatic2 instance) {
		log.debug("attaching dirty ProjectProgrestatic2 instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ProjectProgrestatic2 instance) {
		log.debug("attaching clean ProjectProgrestatic2 instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ProjectProgrestatic2 persistentInstance) {
		log.debug("deleting ProjectProgrestatic2 instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ProjectProgrestatic2 merge(ProjectProgrestatic2 detachedInstance) {
		log.debug("merging ProjectProgrestatic2 instance");
		try {
			ProjectProgrestatic2 result = (ProjectProgrestatic2) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProjectProgrestatic2 findById(java.lang.Integer id) {
		log.debug("getting ProjectProgrestatic2 instance with id: " + id);
		try {
			ProjectProgrestatic2 instance = (ProjectProgrestatic2) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.ProjectProgrestatic2", id);
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

	public List findByExample(ProjectProgrestatic2 instance) {
		log.debug("finding ProjectProgrestatic2 instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"cn.edu.bnu.land.model.ProjectProgrestatic2")
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
