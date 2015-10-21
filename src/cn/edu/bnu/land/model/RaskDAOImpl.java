package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class RaskDAOImpl 
implements RaskDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(Rask r) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(r);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(get(id));
	}

	public void update(Rask r) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(r);
	}

	public List<Rask> findAll() {
		// TODO Auto-generated method stub
		String hql="from Rask as r";
		List<Rask> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Rask>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
		
	}

	public Rask get(String id) {
		// TODO Auto-generated method stub
		return (Rask)sessionFactory.getCurrentSession().get(Rask.class, id);
	}
	
}
