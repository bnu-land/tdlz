package cn.edu.bnu.land.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bnu.land.model.FkTasktable;
import cn.edu.bnu.land.model.FkTasktableHome;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.model.RwTable;
import cn.edu.bnu.land.model.RwTableHome;

@Service
public class RwTableService {
	
	private RwTableHome rwTableHome;
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setRwTableHome(RwTableHome rwTableHome){
		this.rwTableHome=rwTableHome;
	}
	/*Add task function
	 * 2013-12-21 @WW
	 * */
	public void addtask(RwTable rwTable){
		Session session = sessionFactory.getCurrentSession();
		session.save(rwTable);
	}
	
	/*Select task function
	 * 2013-12-21 @WW
	 * */
	public List<RwTable> select(){
		String hql="from RwTable as rwTable where rwTable.rwClass='复垦验收'";
		List<RwTable> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=(List<RwTable>)query.list();
			for(RwTable rwTable:results)
			{
				System.out.println(rwTable.getProjectId());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
        		
	}
	/*edit RwTable function
	 * 2013-12-20 @WW
	 * */
	public RwTable getRwTableById (String rwId) {
		RwTable result = null;
		Session session = sessionFactory.getCurrentSession();

		try {
				result = (RwTable) session.get(RwTable.class,
						rwId);				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}
	
	/*
	 * delete task 20131221 @WW
	 */	
	@Transactional
	public void del_projectByIds(String[] rwIds){
		
		RwTable result = null;
		Session session = sessionFactory.getCurrentSession();
		System.out.println(rwIds);
		try {

			for (int i = 0; i < rwIds.length; i++) {
				result = (RwTable) session.get(RwTable.class,
						rwIds[i]);
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
		
	
}
