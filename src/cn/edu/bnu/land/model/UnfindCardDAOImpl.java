package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class UnfindCardDAOImpl
implements UnfindCardDAO{

	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(UnfindCard c) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(c);
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(get(id));
	}

	public void update(UnfindCard c) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(c);
	}

	public UnfindCard get(Integer id) {
		// TODO Auto-generated method stub
		return (UnfindCard)sessionFactory.getCurrentSession().get(UnfindCard.class, id);
	}

	public List<UnfindCard> findAll() {
		// TODO Auto-generated method stub
		String hql="from UnfindCard as u";
		List<UnfindCard> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<UnfindCard>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}

	public List<UnfindCard> findByProject(String id) {
		// TODO Auto-generated method stub
		String hql="from UnfindCard as u where u.projectID =" + id;
		List<UnfindCard> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<UnfindCard>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}

}
