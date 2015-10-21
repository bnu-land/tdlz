package cn.edu.bnu.land.model;

// Generated 2014-5-15 17:53:12 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class BenefitFind.
 * @see cn.edu.bnu.land.model.BenefitFind
 * @author Hibernate Tools
 */

@Repository
public class BenefitFindHome {

	private static final Log log = LogFactory.getLog(BenefitFindHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(BenefitFind transientInstance) {
		log.debug("persisting BenefitFind instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(BenefitFind instance) {
		log.debug("attaching dirty BenefitFind instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BenefitFind persistentInstance) {
		log.debug("deleting BenefitFind instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BenefitFind merge(BenefitFind detachedInstance) {
		log.debug("merging BenefitFind instance");
		try {
			BenefitFind result = (BenefitFind) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BenefitFind findById(java.lang.Integer id) {
		log.debug("getting BenefitFind instance with id: " + id);
		try {
			BenefitFind instance = (BenefitFind) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.BenefitFind", id);
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

	public List<BenefitFind> findByExample(BenefitFind instance) {
		log.debug("finding BenefitFind instance by example");
		try {
			List<BenefitFind> results = (List<BenefitFind>) sessionFactory
					.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.BenefitFind")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
