package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class FarmersInformationDAOImpl
implements FarmersInformationDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(FarmersInformation FarmersInformation)
	{
	
		sessionFactory.getCurrentSession().save(FarmersInformation);
	}
	
	public void update(FarmersInformation FarmersInformation)
	{
		sessionFactory.getCurrentSession().update(FarmersInformation);
	}
	
	//根据ID获取对象
	public FarmersInformation get(Integer id)
	{
		return (FarmersInformation)sessionFactory.getCurrentSession().get(FarmersInformation.class, id);
	}
	
	public void delete(Integer id)
	{
		sessionFactory.getCurrentSession().delete(get(id));
	}
	
	public List<FarmersInformation> findAll()
	{
		String hql="from FarmersInformation as f";
		List<FarmersInformation> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<FarmersInformation>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
	
	
}

