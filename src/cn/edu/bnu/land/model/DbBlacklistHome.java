package cn.edu.bnu.land.model;

// Generated 2014-9-10 9:12:10 by Hibernate Tools 4.0.0

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class DbBlacklist.
 * @see cn.edu.bnu.land.model.DbBlacklist
 * @author Hibernate Tools
 */
public class DbBlacklistHome {

	private static final Log log = LogFactory.getLog(DbBlacklistHome.class);

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

	public void persist(DbBlacklist transientInstance) {
		log.debug("persisting DbBlacklist instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DbBlacklist instance) {
		log.debug("attaching dirty DbBlacklist instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DbBlacklist instance) {
		log.debug("attaching clean DbBlacklist instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DbBlacklist persistentInstance) {
		log.debug("deleting DbBlacklist instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DbBlacklist merge(DbBlacklist detachedInstance) {
		log.debug("merging DbBlacklist instance");
		try {
			DbBlacklist result = (DbBlacklist) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DbBlacklist findById(java.lang.Integer id) {
		log.debug("getting DbBlacklist instance with id: " + id);
		try {
			DbBlacklist instance = (DbBlacklist) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.DbBlacklist", id);
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

	public List findByExample(DbBlacklist instance) {
		log.debug("finding DbBlacklist instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.DbBlacklist")
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
