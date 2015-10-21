package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class WarmLogsDAOImpl 
implements WarmLogsDAO{
	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(WarmLogs wls) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(wls);
	}

	public void update(WarmLogs wls) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(wls);
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(get(id));
	}

	public WarmLogs get(Integer id) {
		// TODO Auto-generated method stub
		return (WarmLogs)sessionFactory.getCurrentSession().get(WarmLogs.class, id);
	}

	public List<WarmLogs> showAll() {
		// TODO Auto-generated method stub
		String hql="from WarmLogs as w";
		List<WarmLogs> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<WarmLogs>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
		
	}

	public List<WarmLogs> findByProjectID(String projectID) {
		// TODO Auto-generated method stub
		String hql="from WarmLogs as w where w.projectID =" + "'" + projectID + "'";
		List<WarmLogs> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<WarmLogs>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
		
	}

}
