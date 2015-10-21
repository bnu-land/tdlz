package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.InitialContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bnu.land.model.FkDikuai;
import cn.edu.bnu.land.model.FkDikuaiHome;
import cn.edu.bnu.land.model.InfoVote;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.model.RwTable;

@Service
public class fkDikuaiService {

	private FkDikuaiHome fkDikuaiHome;
   private SessionFactory sessionFactory;
		@Autowired
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
	  
	    protected SessionFactory getSessionFactory() {
	        try {
	            return (SessionFactory) new InitialContext().lookup("SessionFactory");
	        }
	        catch (Exception e) {	        
	            throw new IllegalStateException("Could not locate SessionFactory in JNDI");
	        }
	    }
	@Autowired
	public void setFkDikuaiHome(FkDikuaiHome fkDikuaiHome){
		this.fkDikuaiHome=fkDikuaiHome;
	}
	/*
	 * 查询地块 20130905 @WW
	 */	
	public Map<String, Object> select(String start, String limit,String searchKeyword){
		String totalConut = new String();
		String hql;
		if(!searchKeyword.equals("") && !searchKeyword.isEmpty()){
			hql = "from FkDikuai as fkDikuai where fkDikuai.projectId like '%"+searchKeyword+"%'";	
		}
		//查询关键词为空
		else {			
			hql="from FkDikuai as fkDikuai";
		}
		
		List<FkDikuai> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			/*if(!query.list().isEmpty())
				totalConut = String.valueOf(query.list().size());
			    query.setFirstResult(Integer.parseInt(start));
			    query.setMaxResults(Integer.parseInt(limit));*/
			totalConut = String.valueOf(query.list().size());
			System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));	
			
			results=(List<FkDikuai>)query.list();
			for(FkDikuai fkDikuai:results)
			{
				System.out.println(fkDikuai.getLandId());
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);
		return myMapResult;

	}
	/*
	 * 保存地块 20140318 @WW
	 * */
	public void saveDikuai(FkDikuai fkDikuai) {
		Session session = sessionFactory.getCurrentSession();
		session.save(fkDikuai);
	}
	/*
	 *  更新地块信息 20140318 @WW
	 * 
	 * */
	public void updateDikuai(FkDikuai fkDikuai) {
		Session session = sessionFactory.getCurrentSession();
		session.update(fkDikuai);
	}
	/*
	 * 获取地块信息 20140318 @WW
	 * */
	public FkDikuai getdikuaiById(String landId) {
		
		System.out.print("函数运行吧");
		FkDikuai result = null;
		Session session = sessionFactory.getCurrentSession();

		try {
				result = (FkDikuai) session.get(FkDikuai.class,
						Integer.parseInt(landId));				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}
	/*
	 * delete dikuai 20131221 @WW
	 */	
	@Transactional
	public void del_dikuaiByIds(String[] rwIds){
		
		FkDikuai result = null;
		Session session = sessionFactory.getCurrentSession();
		System.out.println(rwIds);
		try {

			for (int i = 0; i < rwIds.length; i++) {
				result = (FkDikuai) session.get(FkDikuai.class,
						Integer.parseInt(rwIds[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
}
