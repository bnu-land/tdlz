package cn.edu.bnu.land.model;

// Generated 2014-5-9 9:49:33 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class BenefitAssign.
 * @see cn.edu.bnu.land.model.BenefitAssign
 * @author Hibernate Tools
 */

@Repository
public class BenefitAssignHome {

	private static final Log log = LogFactory.getLog(BenefitAssignHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void save(BenefitAssign benefitAssign) {
		Session session = sessionFactory.getCurrentSession();
		session.save(benefitAssign);
	}

	public void persist(BenefitAssign transientInstance) {
		log.debug("persisting BenefitAssign instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(BenefitAssign instance) {
		log.debug("attaching dirty BenefitAssign instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BenefitAssign persistentInstance) {
		log.debug("deleting BenefitAssign instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BenefitAssign merge(BenefitAssign detachedInstance) {
		log.debug("merging BenefitAssign instance");
		try {
			BenefitAssign result = (BenefitAssign) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BenefitAssign findById(java.lang.Integer id) {
		log.debug("getting BenefitAssign instance with id: " + id);
		try {
			BenefitAssign instance = (BenefitAssign) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.BenefitAssign", id);
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

	public List<BenefitAssign> findByExample(BenefitAssign instance) {
		log.debug("finding BenefitAssign instance by example");
		try {
			List<BenefitAssign> results = (List<BenefitAssign>) sessionFactory
					.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.BenefitAssign")
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
