package cn.edu.bnu.land.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.InfoReport;
import cn.edu.bnu.land.model.InfoVote;
import cn.edu.bnu.land.model.InfoVoteHome;
import cn.edu.bnu.land.model.InfoVoteoption;
import cn.edu.bnu.land.model.InfoVoteoptionHome;

@Service
public class InfoVoteService{
	private InfoVoteHome infoVoteHome;
	private InfoVoteoptionHome infoVoteoptionHome;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setInfoVoteHome(InfoVoteHome infoVoteHome){
		this.infoVoteHome = infoVoteHome;
		
	}
	@Autowired
	public void setInfoVoteoptionHome(InfoVoteoptionHome infoVoteoptionHome){
		this.infoVoteoptionHome = infoVoteoptionHome;
		
	}
//	public void addInfoArticle(InfoVote infoVote){
//		this.infoVoteHome.save(infoVote);
//	}
//	public Map<String ,Object> getInfoVoteList(String start,String limit) {
//		
//		return this.infoVoteHome.selectInfoVoteList(start,limit);
//	}
	
	
	/*
	 * ͶƱ�����ʾ
	 * ������getVoteResult()
	 * ���ز���ͶƱѡ������List<InfoVoteoption> result
	 * 20130903 @LF
	 */
	public List<InfoVoteoption> getVoteResult(String voteId) {
		//System.out.println("LF @InfoVoteService getVoteResult() and voteId="+voteId);
		List<InfoVoteoption> result = null;
		if(voteId != ""){
		String hql = "from InfoVoteoption as infoVoteoption where voteId="+voteId;
		try{
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			result = query.list();
			//System.out.println("LF @InfoVoteService getVoteResult() and result="+result);
		//	for(InfoVoteoption infoVoteoption : result){
		//		System.out.println("//*********"+infoVoteoption.getVoptionId()+"************");
		//	}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return result;
		
	}
	
	
	
	public Map<String, Object> listInfoVote(String start, String limit, String searchKeyword) {

		
		String hql = "";
		if(searchKeyword.equals("")){
			hql = "from InfoVote as infoVote ";
		}else{
			hql = "from InfoVote as infoVote where "
					+ "infoVote.voteTitle like '%"+searchKeyword+"%'"
					+ "or infoVote.voteVoter like '%"+searchKeyword+"%'"
					+ "or infoVote.voteIntroduction like '%"+searchKeyword+"%'"
					+ "or infoVote.voteClass like '%"+searchKeyword+"%'"
					+ "or infoVote.voteState like '%"+searchKeyword+"%'";
		}
		
		
		
		
		
		String totalConut = null;
		List<InfoVote> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			//System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);
		// System.out.println("myMapResult "+gridId+" "+myMapResult);

		return myMapResult;
	}
	
	
	
	
	public void updateVoteStateTo2(String[] voteIds) {

		
		InfoVote result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < voteIds.length; i++) {

				result = (InfoVote) session.get(InfoVote.class,
						Integer.parseInt(voteIds[i]));

				result.setVoteState("正在进行");
				
				Calendar ca = Calendar.getInstance();
		     	Date now = ca.getTime();
		     	result.setVoteStarttime(now);
		     	session.saveOrUpdate(result);
				

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
public void updateVoteStateTo3(String[] voteIds) {

		
		InfoVote result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < voteIds.length; i++) {

				result = (InfoVote) session.get(InfoVote.class,
						Integer.parseInt(voteIds[i]));

				result.setVoteState("已停止");
				
				Calendar ca = Calendar.getInstance();
		     	Date now = ca.getTime();
		     	result.setVoteEndtime(now);
		     	session.saveOrUpdate(result);
				

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}




public void deleteVoteByIds(String[] voteIds) {

	InfoVote result = null;
	Session session = sessionFactory.getCurrentSession();

	try {

		for (int i = 0; i < voteIds.length; i++) {

			result = (InfoVote) session.get(InfoVote.class,
					Integer.parseInt(voteIds[i]));
			session.delete(result);

		}


	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
}
public void addVoteoption(InfoVoteoption infoVoteoption) {
	Session session = sessionFactory.getCurrentSession();   	 
 	session.saveOrUpdate(infoVoteoption); 
}



public void delVoptionIds(String[] voptionIds) {
	InfoVoteoption result = null;
	Session session = sessionFactory.getCurrentSession();

	try {

		for (int i = 0; i < voptionIds.length; i++) {

			result = (InfoVoteoption) session.get(InfoVoteoption.class,
					Integer.parseInt(voptionIds[i]));
			session.delete(result);

		}


	} catch (Exception e) {
		e.printStackTrace();
	}
}


public void addVote(InfoVote infoVote) {
	Session session = sessionFactory.getCurrentSession();   	 
 	session.saveOrUpdate(infoVote); 
 	//System.out.println("addVote(InfoVote infoVote) infoVote."+infoVote.getVoteId());
 	
	
 	
	String hql = "update InfoVoteoption set voteId ="+infoVote.getVoteId()+" where voteId='0' ";
	try{
		org.hibernate.Query query = session.createQuery(hql);
		int result = query.executeUpdate();
				
	}catch(Exception e){
		e.printStackTrace();
	}
 	
 	
}
	
	
}