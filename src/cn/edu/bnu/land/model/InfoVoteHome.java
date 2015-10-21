package cn.edu.bnu.land.model;

// Generated 2013-12-12 16:33:02 by Hibernate Tools 4.0.0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class InfoVote.
 * @see cn.edu.bnu.land.model.InfoVote
 * @author Hibernate Tools
 */

@Repository
public class InfoVoteHome {

	private static final Log log = LogFactory.getLog(InfoVoteHome.class);

	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void persist(InfoVote transientInstance) {
		log.debug("persisting InfoVote instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(InfoVote instance) {
		log.debug("attaching dirty InfoVote instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(InfoVote persistentInstance) {
		log.debug("deleting InfoVote instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InfoVote merge(InfoVote detachedInstance) {
		log.debug("merging InfoVote instance");
		try {
			InfoVote result = (InfoVote) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public InfoVote findById(java.lang.Integer id) {
		log.debug("getting InfoVote instance with id: " + id);
		try {
			InfoVote instance = (InfoVote) sessionFactory.getCurrentSession()
					.get("cn.edu.bnu.land.model.InfoVote", id);
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

	public List findByExample(InfoVote instance) {
		log.debug("finding InfoVote instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.edu.bnu.land.model.InfoVote")
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
