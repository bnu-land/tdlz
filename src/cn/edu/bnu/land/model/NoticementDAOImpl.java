package cn.edu.bnu.land.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class NoticementDAOImpl 
implements NoticementDAO{
	
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Serializable save(Noticement notice)
	{
	
		return sessionFactory.getCurrentSession().save(notice);
	}
	
	public void update(Noticement notice)
	{
		sessionFactory.getCurrentSession().update(notice);
	}
	
	//根据ID获取对象
	public Noticement get(Integer id)
	{
		return (Noticement)sessionFactory.getCurrentSession().get(Noticement.class, id);
	}
	
	public void delete(Integer id)
	{
		sessionFactory.getCurrentSession().delete(get(id));
	}
	
	public List<Noticement> findAll()
	{
		String hql="from Noticement as n";
		List<Noticement> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Noticement>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
	public List<Noticement> findByDate(Date date)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String tempDate = format.format(date);
		String hql="from Noticement as n where n.date=" + "'" + tempDate + "'";
		List<Noticement> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Noticement>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
	
}
