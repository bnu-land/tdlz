package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bnu.land.model.FkGongcheng;
import cn.edu.bnu.land.model.FkGongchengHome;

@Service
public class FkGongchengService {

	private FkGongchengHome fkGongchengHome;
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setFkGongchengHome(FkGongchengHome fkGongchengHome){
		this.fkGongchengHome=fkGongchengHome;
	}
	/*
	 * 添加保存工程信息
	 * add_gongchengxinxi
	 * */
	public void saveGongchengxin(FkGongcheng fkGongcheng){
		
		Session session = sessionFactory.getCurrentSession();
		session.save(fkGongcheng);
	}
	/*
	 * 查询工程质量信息 2014-03-21
	 * */
	public Map<String,Object> selectGC(String start, String limit,String searchKeyword){
		String totalConut = new String();
		String hql;
		if(!searchKeyword.equals("") && !searchKeyword.isEmpty()){
			hql = "from FkGongcheng as fkGongcheng where fkGongcheng.projectId like '%"+searchKeyword+"%'";	
		}
		//查询关键词为空
		else {			
			hql="from FkGongcheng as fkGongcheng";
		}
		
		List<FkGongcheng> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));	
			
			results=(List<FkGongcheng>)query.list();
			for(FkGongcheng fkGongcheng:results)
			{
				System.out.println(fkGongcheng.getProjectId());
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
	/*edit Gongchengxinxi
	 * 2013-12-20 @WW
	 * */
	public FkGongcheng getGongchengDatail(String projectId){
		FkGongcheng result = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			result = (FkGongcheng) session.get(FkGongcheng.class,
					projectId);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
	}
	/*
	 * 更新工程质量信息
	 * */
	public void updateGongcheng(FkGongcheng fkGongcheng) {
		Session session = sessionFactory.getCurrentSession();
		session.update(fkGongcheng);
	}
	
	/*
	 * 删除工程质量信息 2014-03-21
	 * */
	@Transactional
	public void del_gongchengByIds(String[] sampleIds) {
		FkGongcheng result = null;
		Session session = sessionFactory.getCurrentSession();
		System.out.println(sampleIds);
		try {

			for (int i = 0; i < sampleIds.length; i++) {
				result = (FkGongcheng) session.get(FkGongcheng.class,
						sampleIds[i]);
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
