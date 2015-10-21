package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.InfoChannel;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.model.PublicProjectHome;

@Service
public class PublicProjectService {
	private PublicProjectHome publicProjectHome;
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setPublicProjectHome(PublicProjectHome publicProjectHome){
		this.publicProjectHome=publicProjectHome;
	}	
	/*
	 * 添加保存项目 20130905 @WW
	 */	
	public void addProject(PublicProject publicProject){
		System.out.print("addproject");
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(publicProject);
	}
	/*
	 * 查询项目 20130905 @WW
	 */	
	public Map<String, Object> select(String start, String limit,String searchKeyword,String searchKeywordID, String searchKeywordSite,String searchKeywordStarttime){
		String totalConut = new String();
		String hql = "";
		String hql1="from PublicProject as publicProject";		
		if(searchKeywordStarttime.equals("") && searchKeyword.equals("") && searchKeywordID.equals("") && searchKeywordSite.equals(""))
		   hql=hql1;
		else 
		   hql=hql1+" where ( publicProject.projectId like '%"+searchKeywordID+"%'"				   
				   + " and publicProject.projectname like '%"+searchKeyword+"%'"
				   + " and publicProject.rlocation like '%"+searchKeywordSite+"%'"
				   + " and publicProject.starttime >='"+searchKeywordStarttime+"')";
		
		List<PublicProject> results=null;
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
			
			results=(List<PublicProject>)query.list();
			for(PublicProject public_project:results)
			{
				System.out.println(public_project.getProjectId());
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
	/*2013-12-19 @WW
	    * 验收申请选择项目名称
	    * ApplyListId
	    * */ 
	public List<PublicProject> applyListname() {
		String hql="from PublicProject as publicProject";
		List<PublicProject> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=query.list();
			//System.out.println("totalnoshow:"+String.valueOf(query.list().size()));
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		return results;
	}
	/*2013-12-19 @WW
	    * 验收申请选择项目名称
	    * ApplyListId
	    * */ 
	public List<PublicProject> applyListId(String projectId) {
		String hql="from PublicProject as publicProject where projectId='"+projectId+"'";
		List<PublicProject> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=query.list();
			//System.out.println("totalnoshow:"+String.valueOf(query.list().size()));
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		return results;
	}
	/*edit project function
	 * 2013-12-20 @WW
	 * */
	public PublicProject getProjectById (String projectId) {
		PublicProject result = null;
		Session session = sessionFactory.getCurrentSession();

		try {
				result = (PublicProject) session.get(PublicProject.class,
						Integer.parseInt(projectId));				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}
	
	/*
	 * delete project 20131105 @WW
	 */	
	@Transactional
	public void del_projectByIds(String[] projectIds){
		PublicProject result = null;
		Session session = sessionFactory.getCurrentSession();
		System.out.println("期望得到："+projectIds);
		try {

			for (int i = 0; i < projectIds.length; i++) {
				result = (PublicProject) session.get(PublicProject.class,
						Integer.parseInt(projectIds[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}			
