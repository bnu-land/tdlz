package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class SpatialDataDAOImpl
implements SpatialDataDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(SpatialData sd){
		sessionFactory.getCurrentSession().save(sd);
	}
	
	public void update(SpatialData sd){
		sessionFactory.getCurrentSession().update(sd);
	}
	
	public List<SpatialData> findAll(){
		String hql="from SpatialData as s";
		List<SpatialData> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<SpatialData>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
		
	}
}
