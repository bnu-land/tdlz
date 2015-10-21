package cn.edu.bnu.land.model;

// Generated 2014-5-17 21:50:16 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class BenefitCanshushou.
 * @see cn.edu.bnu.land.model.BenefitCanshushou
 * @author Hibernate Tools
 */

@Repository
public class BenefitCanshushouHome {

	private static final Log log = LogFactory
			.getLog(BenefitCanshushouHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(BenefitCanshushou transientInstance) {
		log.debug("persisting BenefitCanshushou instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(BenefitCanshushou instance) {
		log.debug("attaching dirty BenefitCanshushou instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BenefitCanshushou persistentInstance) {
		log.debug("deleting BenefitCanshushou instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BenefitCanshushou merge(BenefitCanshushou detachedInstance) {
		log.debug("merging BenefitCanshushou instance");
		try {
			BenefitCanshushou result = (BenefitCanshushou) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BenefitCanshushou findById(int id) {
		log.debug("getting BenefitCanshushou instance with id: " + id);
		try {
			BenefitCanshushou instance = (BenefitCanshushou) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.BenefitCanshushou", id);
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

	public List<BenefitCanshushou> findByExample(BenefitCanshushou instance) {
		log.debug("finding BenefitCanshushou instance by example");
		try {
			List<BenefitCanshushou> results = (List<BenefitCanshushou>) sessionFactory
					.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.BenefitCanshushou")
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
