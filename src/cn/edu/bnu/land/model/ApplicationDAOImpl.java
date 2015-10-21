package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class ApplicationDAOImpl
implements ApplicationDAO{

	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(Application application)
	{
		sessionFactory.getCurrentSession().save(application);
	}
	
	public void update(Application application)
	{
		sessionFactory.getCurrentSession().update(application);
	}
	
	public Application getApplication(Integer id)
	{
		return (Application)sessionFactory.getCurrentSession().get(Application.class, id);
	}
	
	public void delete(Integer id)
	{
		sessionFactory.getCurrentSession().delete(getApplication(id));
	}
	
	public List<Application> findByFarmer(Integer id)
	{
		String hql="from Application as a where a.farmer.id=" + id;
		List<Application> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Application>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
	public List<Application> findAll()
	{
		String hql="from Application as a";
		List<Application> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Application>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
}
