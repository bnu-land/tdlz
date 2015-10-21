package cn.edu.bnu.land.model;

// Generated 2015-4-17 17:01:56 by Hibernate Tools 4.0.0

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class DbBackuprecord.
 * @see cn.edu.bnu.land.model.DbBackuprecord
 * @author Hibernate Tools
 */
public class DbBackuprecordHome {

	private static final Log log = LogFactory.getLog(DbBackuprecordHome.class);

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

	public void persist(DbBackuprecord transientInstance) {
		log.debug("persisting DbBackuprecord instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DbBackuprecord instance) {
		log.debug("attaching dirty DbBackuprecord instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DbBackuprecord instance) {
		log.debug("attaching clean DbBackuprecord instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DbBackuprecord persistentInstance) {
		log.debug("deleting DbBackuprecord instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DbBackuprecord merge(DbBackuprecord detachedInstance) {
		log.debug("merging DbBackuprecord instance");
		try {
			DbBackuprecord result = (DbBackuprecord) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DbBackuprecord findById(java.lang.Integer id) {
		log.debug("getting DbBackuprecord instance with id: " + id);
		try {
			DbBackuprecord instance = (DbBackuprecord) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.DbBackuprecord", id);
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

	public List findByExample(DbBackuprecord instance) {
		log.debug("finding DbBackuprecord instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.DbBackuprecord")
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
