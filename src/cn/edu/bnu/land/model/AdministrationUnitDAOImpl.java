package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class AdministrationUnitDAOImpl 
implements AdministrationUnitDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(AdministrationUnit AdministrationUnit)
	{
	
		sessionFactory.getCurrentSession().save(AdministrationUnit);
	}
	
	public void update(AdministrationUnit AdministrationUnit)
	{
		sessionFactory.getCurrentSession().update(AdministrationUnit);
	}
	
	//根据ID获取对象
	public AdministrationUnit get(Integer id)
	{
		return (AdministrationUnit)sessionFactory.getCurrentSession().get(AdministrationUnit.class, id);
	}
	
	public void delete(Integer id)
	{
		sessionFactory.getCurrentSession().delete(get(id));
	}
	
	public List<AdministrationUnit> findAll()
	{
		String hql="from AdministrationUnit as a";
		List<AdministrationUnit> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<AdministrationUnit>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
		
	}
	
	
}


