package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.FkQualityreport;
import cn.edu.bnu.land.model.FkQualityreportHome;

@Service
public class FkQualityreportService {

	private FkQualityreportHome fkQualityreportHome;
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setPublicProjectHome(FkQualityreportHome fkQualityreportHome){
		this.fkQualityreportHome=fkQualityreportHome;
	}	
	/*
	 * 保存项目土壤评价结果 20140218 @WW
	 */	
	public void saveSoilresult(String proJecTid,String proJecTname,String auditResults,String evaluateResult,String overallConclusion){
		List list= sessionFactory.getCurrentSession().createQuery("select recordId from FkQualityreport as f where f.projectId='"+proJecTid+"'").list();				
		if(list!=null && !list.isEmpty()){
			int recordId=(Integer) list.get(0);
			System.out.print("recordId:"+recordId);	
			//String hql="select recordId from FkQualityreport as a where a.projectId='"+proJecTid+"' ";			
			String hql= "update FkQualityreport f set f.soilAuditOpinion='"+auditResults+"',f.soilQualityComment='"+overallConclusion+"',f.soilQualityGrade='"+evaluateResult+"' where recordId="+recordId+"";			
			System.out.print("//r hql:"+hql);	
			try{
				org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
				query.executeUpdate();					
			}catch(Exception e){
				e.printStackTrace();
			}	 			
			}else{	
				
				FkQualityreport st=new FkQualityreport(); 
				st.setProjectId(proJecTid); 
				st.setProjectname(proJecTname);
				st.setSoilQualityGrade(evaluateResult);
				st.setSoilQualityComment(overallConclusion);
				st.setSoilAuditOpinion(auditResults);	
				//String hql = "insert into FkQualityreport f (f.projectId,f.projectname,f.soilQualityGrade,f.soilQualityComment) values('"+proJecTid+"','"+proJecTname+"','"+evaluateResult+"','"+auditResults+"')"; 
				try{			 
					sessionFactory.getCurrentSession().save(st);					
				}catch(Exception e){
					e.printStackTrace();
				}	
			}			 
	}
	/*save GongCheng quality
	 * 
	 * */
	public void saveGongChengresult(String PROJECTID,String projectnameG,String GongchengOpinion,String chgcid,String ntslid,String tjdlid,String tdpzid){
		List list= sessionFactory.getCurrentSession().createQuery("select recordId from FkQualityreport as f where f.projectId='"+PROJECTID+"'").list();
		int recordId=(Integer) list.get(0);
		System.out.print("recordId:"+recordId);			
		if(list!=null && !list.isEmpty()){	
			String hql= "update FkQualityreport f set f.gongResults='"+GongchengOpinion+"',f.gongCqgc='"+chgcid+"',f.gongNtsl='"+ntslid+"',f.gongTjdl='"+tjdlid+"',f.gongTdpz='"+tdpzid+"' where recordId="+recordId+"";			
			System.out.print("//r hql:"+hql);	
			try{
				org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
				query.executeUpdate();					
			}catch(Exception e){
				e.printStackTrace();
			}	 			
			}else{					
				FkQualityreport st=new FkQualityreport(); 
				st.setProjectId(PROJECTID); 
				st.setProjectname(projectnameG);
				st.setGongResults(GongchengOpinion);
				st.setGongCqgc(chgcid);
				st.setGongNtsl(ntslid);
				st.setGongTdpz(tdpzid);
				st.setGongTjdl(tjdlid);
				//String hql = "insert into FkQualityreport f (f.projectId,f.projectname,f.soilQualityGrade,f.soilQualityComment) values('"+proJecTid+"','"+proJecTname+"','"+evaluateResult+"','"+auditResults+"')"; 
				try{			 
					sessionFactory.getCurrentSession().save(st);	
				}catch(Exception e){
					e.printStackTrace();
				}	
			}			 
	}
	
	/*save Expert quality
	 * 
	 * */
	public void saveExpertresult(String PROJECTID,String projectnameE,
			String evaluationResults,String microreliefDescription,String landProportion,
			String waterStorageCapacity,String trafficConditions,String goukanQuality,
			String formingClodsDegree,String fertilityEvaluation, String evaluationExpert){
		List list= sessionFactory.getCurrentSession().createQuery("select recordId from FkQualityreport as f where f.projectId='"+PROJECTID+"'").list();					
		if(list!=null && !list.isEmpty()){	
			int recordId=(Integer) list.get(0);
			System.out.print("recordId:"+recordId);
			String hql= "update FkQualityreport f set f.microreliefDescription='"+microreliefDescription+"',f.trafficConditions='"+trafficConditions+"',f.goukanQuality='"+goukanQuality+"',f.landProportion='"+landProportion+"',f.fertilityEvaluation='"+fertilityEvaluation+"',f.formingClodsDegree='"+formingClodsDegree+"',f.expertResults='"+evaluationResults+"',f.expert='"+evaluationExpert+"',f.waterStorageCapacity='"+waterStorageCapacity+"' where recordId="+recordId+"";			
			System.out.print("//r hql:"+hql);	
			try{
				org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
				query.executeUpdate();					
			}catch(Exception e){
				e.printStackTrace();
			}	 			
			}else{	
				
				FkQualityreport st=new FkQualityreport(); 
				st.setProjectId(PROJECTID); 
				st.setProjectname(projectnameE);
				st.setExpert(evaluationExpert);
				st.setExpertResults(evaluationResults);
				st.setFertilityEvaluation(fertilityEvaluation);
				st.setFormingClodsDegree(formingClodsDegree);
				st.setGoukanQuality(goukanQuality);
				st.setLandProportion(landProportion);
				st.setMicroreliefDescription(microreliefDescription);
				st.setTrafficConditions(trafficConditions);
				st.setWaterStorageCapacity(waterStorageCapacity);
				//String hql = "insert into FkQualityreport f (f.projectId,f.projectname,f.soilQualityGrade,f.soilQualityComment) values('"+proJecTid+"','"+proJecTname+"','"+evaluateResult+"','"+auditResults+"')"; 
				try{			 
					sessionFactory.getCurrentSession().save(st);	
				}catch(Exception e){
					e.printStackTrace();
				}	
			}			 
	}
	
	/*
	 * 查询评价结果
	 * */
	public Map<String, Object> selectResults(String start, String limit,String searchKeyword){		
		String totalConut = new String();
		String hql;
		if(!searchKeyword.equals("") && !searchKeyword.isEmpty()){
			hql = "from FkQualityreport as fkQualityreport where fkQualityreport.projectId like '%"+searchKeyword+"%'";	
		}
		//查询关键词为空
		else {			
			hql="from FkQualityreport as fkQualityreport";
		}
		
		List<FkQualityreport> results=null;
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
			
			results=(List<FkQualityreport>)query.list();
			for(FkQualityreport FkQualityreport:results)
			{
				System.out.println(FkQualityreport.getProjectId());
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
