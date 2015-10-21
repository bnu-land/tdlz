package cn.edu.bnu.land.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class DemoLogDAOImpl 
implements DemoLogDAO{
	
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(DemolitionLog log)
	{
		sessionFactory.getCurrentSession().save(log);
	}
	
	public void update(DemolitionLog log)
	{
		sessionFactory.getCurrentSession().update(log);
	}
	
	public DemolitionLog get(Integer id)
	{
		return (DemolitionLog)sessionFactory.getCurrentSession().get(DemolitionLog.class, id);
	}
	
	public void delete(Integer id)
	{
		sessionFactory.getCurrentSession().delete(get(id));
	}
	
	public List<DemolitionLog> findAll()
	{
		String hql="from DemolitionLog as d";
		List<DemolitionLog> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<DemolitionLog>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
	
	public List<DemolitionLog> findByDate(Date date)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String tempDate = format.format(date);
		String hql="from DemolitionLog as d where d.date=" + "'" + tempDate + "'";
		List<DemolitionLog> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<DemolitionLog>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}

	public List<DemolitionLog> findByProjectID(String projectID) {
		// TODO Auto-generated method stub
		String hql="from DemolitionLog as d where d.projectID =" + "'" + projectID + "'";
		List<DemolitionLog> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<DemolitionLog>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
}
