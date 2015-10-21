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
public class UserInformationDAOImpl
implements UserInformationDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(UserInformation UserInformation)
	{
	
		sessionFactory.getCurrentSession().save(UserInformation);
	}
	
	public void update(UserInformation UserInformation)
	{
		sessionFactory.getCurrentSession().update(UserInformation);
	}
	
	//根据ID获取对象
	public UserInformation get(Integer id)
	{
		return (UserInformation)sessionFactory.getCurrentSession().get(UserInformation.class, id);
	}
	
	public void delete(Integer id)
	{
		sessionFactory.getCurrentSession().delete(get(id));
	}
	
	public List<UserInformation> findAll()
	{
		String hql="from UserInformation as n";
		List<UserInformation> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<UserInformation>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
	
	public List<UserInformation> findByDate(Date date)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String tempDate = format.format(date);
		String hql="from UserInformation as n where n.date=" + "'" + tempDate + "'";
		List<UserInformation> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<UserInformation>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
	
	
}
