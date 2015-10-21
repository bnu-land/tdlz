package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.AbnmWholesupervision;
import cn.edu.bnu.land.model.AbnmWholesupervisionHome;

@Service
public class FkWholesupervisionService {
	private AbnmWholesupervisionHome  abnmWholesupervisionHome;
    private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
    @Autowired
    public void setPublicProjectHome(AbnmWholesupervisionHome  abnmWholesupervisionHome){
		this.abnmWholesupervisionHome=abnmWholesupervisionHome;
	}
    /*
	 * 查询预警信息 20140302 @WW
	 */	
	public Map<String, Object> select(String start, String limit,String searchKeyword){
		System.out.print("select");
		String totalConut = new String();
		String hql;
		if(!searchKeyword.equals("") && !searchKeyword.isEmpty()){
			hql = "from AbnmWholesupervision as abnmWholesupervision where abnmWholesupervision.abwsTitle like '%"+searchKeyword+"%' and abnmWholesupervision.abwsClass like '%复垦%'" ;	
		}	
		else {			
			hql="from AbnmWholesupervision as abnmWholesupervision where abnmWholesupervision.abwsClass like '%复垦%'";
		}		
		List<AbnmWholesupervision> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));				
			results=(List<AbnmWholesupervision>)query.list();
			for(AbnmWholesupervision abnmWholesupervision:results)
			{
				//System.out.println(abnmWholesupervision.getAbwsTitle());
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
