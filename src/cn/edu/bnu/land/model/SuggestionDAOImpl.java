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
public class SuggestionDAOImpl 
implements SuggestionDAO{
	
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(Suggestion suggest)
	{
		sessionFactory.getCurrentSession().save(suggest);
	}
	
	public void update(Suggestion suggest)
	{
		sessionFactory.getCurrentSession().update(suggest);
	}
	
	public Noticement getNotice(Integer id)
	{
		return (Noticement)sessionFactory.getCurrentSession().get(Noticement.class, id);
	}
		
	public Suggestion get(Integer id)
	{
		return (Suggestion)sessionFactory.getCurrentSession().get(Suggestion.class, id);
	}
	
	public void delete(Integer id)
	{
		Suggestion s = get(id);
		s.getNotice().getSuggest().remove(s);;
		s.setNotice(null);
		
		sessionFactory.getCurrentSession().delete(s);
	}
	
	public List<Suggestion> findAll()
	{
		String hql="from Suggestion as s";
		List<Suggestion> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Suggestion>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	public List<Suggestion> findByDate(Date date)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String tempDate = format.format(date);
		String hql="from Suggestion as s where s.date=" + "'" + tempDate + "'";
		List<Suggestion> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Suggestion>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	public List<Suggestion> findByCategory(String category)
	{
		String hql="from Suggestion as s where s.category=" + "'"+category+"'";
		List<Suggestion> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Suggestion>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}

	public List<Suggestion> findByProjectID(String projectID) {
		// TODO Auto-generated method stub
		String hql="from Suggestion as n where n.projectID=" + "'" + projectID + "'";
		List<Suggestion> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Suggestion>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
	
}
