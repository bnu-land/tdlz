package cn.edu.bnu.land.model;

// Generated 2014-12-6 21:41:55 by Hibernate Tools 4.0.0

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class ABlockChannelId.
 * @see model.ABlockChannelId
 * @author Hibernate Tools
 */
public class ABlockChannelIdHome {

	private static final Log log = LogFactory.getLog(ABlockChannelIdHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ABlockChannelId transientInstance) {
		log.debug("persisting ABlockChannelId instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ABlockChannelId instance) {
		log.debug("attaching dirty ABlockChannelId instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ABlockChannelId instance) {
		log.debug("attaching clean ABlockChannelId instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ABlockChannelId persistentInstance) {
		log.debug("deleting ABlockChannelId instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ABlockChannelId merge(ABlockChannelId detachedInstance) {
		log.debug("merging ABlockChannelId instance");
		try {
			ABlockChannelId result = (ABlockChannelId) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ABlockChannelId findById(java.lang.Integer id) {
		log.debug("getting ABlockChannelId instance with id: " + id);
		try {
			ABlockChannelId instance = (ABlockChannelId) sessionFactory
					.getCurrentSession().get("model.ABlockChannelId", id);
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

	public List findByExample(ABlockChannelId instance) {
		log.debug("finding ABlockChannelId instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("model.ABlockChannelId")
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
