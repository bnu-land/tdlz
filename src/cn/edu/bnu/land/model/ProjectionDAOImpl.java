package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class ProjectionDAOImpl 
implements ProjectionDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(Projection p) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(p);
	}

	public List<Projection> findAll() {
		// TODO Auto-generated method stub
		String hql="from Projection as p";
		List<Projection> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Projection>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}

	public void update(Projection p) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(p);
	}

	public List<Projection> findByNumber(String number) {
		// TODO Auto-generated method stub
		String hql="from Projection as p where p.projectNumber =" + "'" + number + "'";
		List<Projection> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Projection>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
	
}
