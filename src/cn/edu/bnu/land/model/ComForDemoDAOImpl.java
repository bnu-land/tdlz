package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class ComForDemoDAOImpl 
implements ComForDemoDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(ComForDemolition cfd)
	{
		sessionFactory.getCurrentSession().save(cfd);
	}
	
	public ComForDemolition get(Integer id)
	{
		return (ComForDemolition)sessionFactory.getCurrentSession().get(ComForDemolition.class, id);
	}
	
	public void delete(Integer id)
	{
		sessionFactory.getCurrentSession().delete(get(id));
	}
	
	public void update(ComForDemolition cfd)
	{
		sessionFactory.getCurrentSession().update(cfd);
	}
	
	public List<ComForDemolition> getByFarmerID(Integer id)
	{
		String hql="from ComForDemolition as c where c.farmer.id=" + id;
		List<ComForDemolition> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<ComForDemolition>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}	
	
	public List<ComForDemolition> findAll()
	{
		System.out.println("进入findAll");
		String hql="from ComForDemolition as c";
		List<ComForDemolition> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<ComForDemolition>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}

	public List<ComForDemolition> getByProject(String id) {
		// TODO Auto-generated method stub
		String hql="from ComForDemolition as c where c.farmer.projectID=" + "'" + id + "'";
		List<ComForDemolition> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<ComForDemolition>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}


}
