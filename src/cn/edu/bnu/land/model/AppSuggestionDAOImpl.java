package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class AppSuggestionDAOImpl 
implements AppSuggestionDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(Application application)
	{
		sessionFactory.getCurrentSession().save(application);
	}
	public void save(AppSuggestion suggest)
	{
		sessionFactory.getCurrentSession().save(suggest);
	}
	
	public void update(AppSuggestion suggest)
	{
		sessionFactory.getCurrentSession().update(suggest);
	}
	
	public void delete(Integer id)
	{
		sessionFactory.getCurrentSession().delete(getAppSuggest(id));
	}
	
	public AppSuggestion getAppSuggest(Integer id)
	{
		return (AppSuggestion)sessionFactory.getCurrentSession().get(AppSuggestion.class, id);
	}
	
	public List<AppSuggestion> findByID(Integer id)
	{
		String hql="from AppSuggestion as as where app_id=" + id;
		List<AppSuggestion> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<AppSuggestion>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
		
	}
	
	
	public List<AppSuggestion> findAll()
	{
		String hql="from AppSuggestion as as";
		List<AppSuggestion> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<AppSuggestion>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
		
	}
}
