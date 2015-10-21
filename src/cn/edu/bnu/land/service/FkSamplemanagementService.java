package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bnu.land.model.FkSamplemanagement;
import cn.edu.bnu.land.model.FkSamplemanagementHome;

@Service
public class FkSamplemanagementService {

	private FkSamplemanagementHome  fkSamplemanagementHome;
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Autowired
	public void setFkSamplemanagementHome(FkSamplemanagementHome  fkSamplemanagementHome)
	{
		this.fkSamplemanagementHome=fkSamplemanagementHome;
	}
	public void add_sample(FkSamplemanagement fkSamplemanagement)
	{		
		this.fkSamplemanagementHome.save(fkSamplemanagement);
	}
	/*
	 * select sample 20131105 @WW
	 */	
	public Map<String,Object> select(String start, String limit,String searchKeyword){
		
		return this.fkSamplemanagementHome.select(start,limit,searchKeyword);
	}
	/*edit project function service
	 * 2013-12-20 @WW
	 * */
	public FkSamplemanagement getSampleById(String sampleId)  {
		return this.fkSamplemanagementHome.getSampleById(sampleId);		
	}
	
	/*
	 *  编辑采样点信息 20140318 @WW
	 * 
	 * */
	public void updateSample(FkSamplemanagement fkSamplemanagement) {
		Session session = sessionFactory.getCurrentSession();
		session.update(fkSamplemanagement);
	}
		
	/*TIANjia pingjiazhibiao service
	 * 2013-12-20 @WW
	 * */
	public List getSampleById_E(String projectId)  {
		FkSamplemanagement fk=new FkSamplemanagement();
		fk.setProjectId(projectId);
		return this.fkSamplemanagementHome.findByExample(fk);//.getSampleById_E(projectId);		
	}
	
	
	/*
	 * delete sample 20131105 @WW
	 */	
	@Transactional
	public void del_sampleByIds(String[] sampleIds){
		this.fkSamplemanagementHome.del_sampleByIds(sampleIds);
	}	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getSampleManagement(String projectId, String limit){
		String totalConut = new String();
		String hql;
		if(!projectId.equals("") && !projectId.isEmpty()){
			hql = "from FkSamplemanagement as fksm where fksm.projectId like '%"+projectId+"%'";	
		}
		//查询关键词为空
		else {			
			hql="from FkSamplemanagement as fksm";
		}
		
		List<FkSamplemanagement> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			/*if(!query.list().isEmpty())
				totalConut = String.valueOf(query.list().size());
			    query.setFirstResult(Integer.parseInt(start));
			    query.setMaxResults(Integer.parseInt(limit));*/
			totalConut = String.valueOf(query.list().size());			
			//query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));	
			
			results=(List<FkSamplemanagement>)query.list();	
			for(FkSamplemanagement fk : results){
				System.out.println("抽样编号："+fk.getSampleId());
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
	
	
}
