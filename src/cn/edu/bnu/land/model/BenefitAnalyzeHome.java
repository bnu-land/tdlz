package cn.edu.bnu.land.model;

// Generated 2014-3-26 15:08:04 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class BenefitAnalyze.
 * @see cn.edu.bnu.land.model.BenefitAnalyze
 * @author Hibernate Tools
 */

@Repository
public class BenefitAnalyzeHome {

	private static final Log log = LogFactory.getLog(BenefitAnalyzeHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(BenefitAnalyze transientInstance) {
		log.debug("persisting BenefitAnalyze instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(BenefitAnalyze instance) {
		log.debug("attaching dirty BenefitAnalyze instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BenefitAnalyze persistentInstance) {
		log.debug("deleting BenefitAnalyze instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BenefitAnalyze merge(BenefitAnalyze detachedInstance) {
		log.debug("merging BenefitAnalyze instance");
		try {
			BenefitAnalyze result = (BenefitAnalyze) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BenefitAnalyze findById(cn.edu.bnu.land.model.BenefitAnalyzeId id) {
		log.debug("getting BenefitAnalyze instance with id: " + id);
		try {
			BenefitAnalyze instance = (BenefitAnalyze) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.BenefitAnalyze", id);
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

	public List<BenefitAnalyze> findByExample(BenefitAnalyze instance) {
		log.debug("finding BenefitAnalyze instance by example");
		try {
			List<BenefitAnalyze> results = (List<BenefitAnalyze>) sessionFactory
					.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.BenefitAnalyze")
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
