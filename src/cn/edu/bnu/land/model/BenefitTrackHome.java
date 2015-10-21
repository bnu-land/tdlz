package cn.edu.bnu.land.model;

// Generated 2014-3-26 15:08:04 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class BenefitTrack.
 * @see cn.edu.bnu.land.model.BenefitTrack
 * @author Hibernate Tools
 */

@Repository
public class BenefitTrackHome {

	private static final Log log = LogFactory.getLog(BenefitTrackHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void save(BenefitTrack benefitTrack) {
		Session session = sessionFactory.getCurrentSession();
		session.save(benefitTrack);
	}

	public void persist(BenefitTrack transientInstance) {
		log.debug("persisting BenefitTrack instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(BenefitTrack instance) {
		log.debug("attaching dirty BenefitTrack instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BenefitTrack persistentInstance) {
		log.debug("deleting BenefitTrack instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BenefitTrack merge(BenefitTrack detachedInstance) {
		log.debug("merging BenefitTrack instance");
		try {
			BenefitTrack result = (BenefitTrack) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BenefitTrack findById(java.lang.Integer id) {
		log.debug("getting BenefitTrack instance with id: " + id);
		try {
			BenefitTrack instance = (BenefitTrack) sessionFactory
					.getCurrentSession().get(
							"cn.edu.bnu.land.model.BenefitTrack", id);
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

	public List<BenefitTrack> findByExample(BenefitTrack instance) {
		log.debug("finding BenefitTrack instance by example");
		try {
			List<BenefitTrack> results = (List<BenefitTrack>) sessionFactory
					.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.BenefitTrack")
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
