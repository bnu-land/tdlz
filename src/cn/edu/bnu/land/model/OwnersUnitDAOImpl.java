package cn.edu.bnu.land.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class OwnersUnitDAOImpl 
implements  OwnersUnitDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save( OwnersUnit  OwnersUnit)
	{
	
		sessionFactory.getCurrentSession().save( OwnersUnit);
	}
	
	public void update( OwnersUnit  OwnersUnit)
	{
		sessionFactory.getCurrentSession().update( OwnersUnit);
	}
	
	//根据ID获取对象
	public  OwnersUnit get(Integer id)
	{
		return ( OwnersUnit)sessionFactory.getCurrentSession().get( OwnersUnit.class, id);
	}
	
	public void delete(Integer id)
	{
		sessionFactory.getCurrentSession().delete(get(id));
	}
	
	public List< OwnersUnit> findAll()
	{
		String hql="from OwnersUnit as o";
		List<OwnersUnit> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<OwnersUnit>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
	
	public List< OwnersUnit> findByDate(Date date)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String tempDate = format.format(date);
		String hql="from OwnersUnit as n where n.date=" + "'" + tempDate + "'";
		List<OwnersUnit> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<OwnersUnit>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
	
	
}

