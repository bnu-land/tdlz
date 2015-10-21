package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class RecordDAOImpl 
implements RecordDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(Record record)
	{
		sessionFactory.getCurrentSession().save(record);
	}
	
	public void update(Record record)
	{
		sessionFactory.getCurrentSession().update(record);
	}
	
	public Record get(Integer id)
	{
		return (Record)sessionFactory.getCurrentSession().get(Record.class, id);
	}
	
	public List<Record> findAll()
	{
		String hql="from Record as r";
		List<Record> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Record>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}

	public List<Record> getByProject(String id) {
		// TODO Auto-generated method stub
		String hql="from Record as r where r.projectID =" + "'" + id + "'";
		List<Record> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<Record>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
		
	}

}
