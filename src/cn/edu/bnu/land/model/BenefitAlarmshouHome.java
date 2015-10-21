package cn.edu.bnu.land.model;

// Generated 2014-5-6 16:10:04 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class BenefitAlarmshou.
 * @see cn.edu.bnu.land.model.BenefitAlarmshou
 * @author Hibernate Tools
 */

@Repository
public class BenefitAlarmshouHome {

	private static final Log log = LogFactory
			.getLog(BenefitAlarmshouHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(BenefitAlarmshou transientInstance) {
		log.debug("persisting BenefitAlarmshou instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(BenefitAlarmshou instance) {
		log.debug("attaching dirty BenefitAlarmshou instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BenefitAlarmshou persistentInstance) {
		log.debug("deleting BenefitAlarmshou instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BenefitAlarmshou merge(BenefitAlarmshou detachedInstance) {
		log.debug("merging BenefitAlarmshou instance");
		try {
			BenefitAlarmshou result = (BenefitAlarmshou) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BenefitAlarmshou findById(java.lang.Integer id) {
		log.debug("getting BenefitAlarmshou instance with id: " + id);
		try {
			BenefitAlarmshou instance = (BenefitAlarmshou) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.BenefitAlarmshou", id);
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

	public List<BenefitAlarmshou> findByExample(BenefitAlarmshou instance) {
		log.debug("finding BenefitAlarmshou instance by example");
		try {
			List<BenefitAlarmshou> results = (List<BenefitAlarmshou>) sessionFactory
					.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.BenefitAlarmshou")
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
