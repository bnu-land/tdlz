package cn.edu.bnu.land.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class CardPieceDAOImpl
implements CardPieceDAO{
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(CardPiece cp){
		sessionFactory.getCurrentSession().save(cp);
	}
	
	public void update(CardPiece cp){
		sessionFactory.getCurrentSession().update(cp);
	}
	
	public void delete(Integer id){
		sessionFactory.getCurrentSession().delete(getCardPiece(id));
	}
	
	public CardPiece getCardPiece(Integer id){
		return (CardPiece)sessionFactory.getCurrentSession().get(CardPiece.class, id);
	}
	
	public List<CardPiece> findBySFZ(String card){
		
		String hql="from CardPiece as c where c.DYNHSFZ=" + card;
		List<CardPiece> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<CardPiece>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
	public List<CardPiece> findAll(){
		String hql="from CardPiece as c";
		List<CardPiece> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<CardPiece>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}

	public List<CardPiece> findByProjectID(String projectID) {
		// TODO Auto-generated method stub
		String hql="from CardPiece as c where c.projectID=" + "'" + projectID + "'";
		List<CardPiece> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<CardPiece>)query.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;

	}
}
