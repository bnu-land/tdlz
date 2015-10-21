package cn.edu.bnu.land.model;

// Generated 2014-5-17 19:34:46 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class BenefitCanshutai.
 * @see cn.edu.bnu.land.model.BenefitCanshutai
 * @author Hibernate Tools
 */

@Repository
public class BenefitCanshutaiHome {

	private static final Log log = LogFactory
			.getLog(BenefitCanshutaiHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(BenefitCanshutai transientInstance) {
		log.debug("persisting BenefitCanshutai instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(BenefitCanshutai instance) {
		log.debug("attaching dirty BenefitCanshutai instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BenefitCanshutai persistentInstance) {
		log.debug("deleting BenefitCanshutai instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BenefitCanshutai merge(BenefitCanshutai detachedInstance) {
		log.debug("merging BenefitCanshutai instance");
		try {
			BenefitCanshutai result = (BenefitCanshutai) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BenefitCanshutai findById(int id) {
		log.debug("getting BenefitCanshutai instance with id: " + id);
		try {
			BenefitCanshutai instance = (BenefitCanshutai) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.BenefitCanshutai", id);
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

	public List<BenefitCanshutai> findByExample(BenefitCanshutai instance) {
		log.debug("finding BenefitCanshutai instance by example");
		try {
			List<BenefitCanshutai> results = (List<BenefitCanshutai>) sessionFactory
					.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.BenefitCanshutai")
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
