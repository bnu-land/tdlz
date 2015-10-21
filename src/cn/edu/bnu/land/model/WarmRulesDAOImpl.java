package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class WarmRulesDAOImpl 
implements WarmRulesDAO{
	private SessionFactory sessionFactory = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(WarmRules wr) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(wr);
	}

	public void update(WarmRules wr) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(wr);
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(get(id));
	}

	public WarmRules get(Integer id) {
		// TODO Auto-generated method stub
		return (WarmRules)sessionFactory.getCurrentSession().get(WarmRules.class, id);
	}

	public List<WarmRules> findAll() {
		// TODO Auto-generated method stub
		String hql="from WarmRules as w";
		List<WarmRules> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<WarmRules>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
		
	}
	
}
