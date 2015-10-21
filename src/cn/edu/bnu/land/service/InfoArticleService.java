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
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.InfoArticleHome;

@Service
public class InfoArticleService {
	private InfoArticleHome infoArticleHome;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setInfoArticleHome(InfoArticleHome infoArticleHome) {
		this.infoArticleHome = infoArticleHome;

	}

	/*
	 * �����б�����һ����¼ 20130905 @LF
	 */
	public void addInfoArticle(InfoArticle infoArticle) {
		Session session = sessionFactory.getCurrentSession();
		try{
		session.save(infoArticle);}
		catch(Exception er)
		{ 
			System.out.println(er.getMessage());
		}
		
	}

	/*
	 * listInfoArticle(String start, String limit,String channelId, String isrecycle, String isdraft)
	 * @LF 20131129
	 */
	public Map<String, Object> listInfoArticle(String start, String limit,
			String channelId, String isrecycle, String isdraft, String searchKeyword, String searchDate) {
		
		String hql = "";
		String hql1 = "from InfoArticle as infoArticle where channelId="
				+ channelId +" and articleIsrecycle='"+isrecycle+"' and articleIsdraft='"+isdraft+"'";
		//搜索日期不为空，搜索关键词空
		if(!searchDate.equals("")  && searchKeyword.equals("")){
			hql=hql1+" and infoArticle.articlePublishtime>='"+searchDate+"'";	
		}
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate.equals("")  && !searchKeyword.equals("")){
			String hql2 = " and ( infoArticle.articleName like '%"+searchKeyword+"%'"+
					" or infoArticle.articleContent like '%"+searchKeyword+"%'"+
					" or infoArticle.articleSource like '%"+searchKeyword+"%'"+
					" or infoArticle.articleAuthor like '%"+searchKeyword+"%'"+
					" and infoArticle.articlePublishtime>='"+searchDate+"')";
			hql=hql1+hql2;
		
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate.equals("")  && !searchKeyword.equals("")){
			String hql3 = " and ( infoArticle.articleName like '%"+searchKeyword+"%'"+
					" or infoArticle.articleContent like '%"+searchKeyword+"%'"+
					" or infoArticle.articleSource like '%"+searchKeyword+"%'"+
					" or infoArticle.articleAuthor like '%"+searchKeyword+"%')";
			hql=hql1+hql3;
		}
		//搜索日期为空，搜索关键词为空
		else if(searchDate.equals("")  && (searchKeyword.equals("")||searchKeyword.isEmpty())){ 
			hql = hql1;
			};
				
		String totalConut = null;
		List<InfoArticle> results = null;
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
	
//	/*
//	 * listInfoArticleComment(String start, String limit,String channelId, String isrecycle, String isdraft)
//	 * @LF 20131129
//	 */
//	public Map<String, Object> listInfoArticleComment(String start, String limit,
//			String channelId, String isrecycle, String isdraft, String searchKeyword, String searchDate) {
//		
//		String hql = "";
//		String hql1 = "from InfoArticle as infoArticle where channelId="
//				+ channelId +" and articleIsrecycle='"+isrecycle+"' and articleIsdraft='"+isdraft+"'";
//		//搜索日期不为空，搜索关键词空
//		if(!searchDate.equals("")  && searchKeyword.equals("")){
//			hql=hql1+" and infoArticle.articlePublishtime>='"+searchDate+"'";	
//		}
//		//搜索日期不为空，搜索关键词不为空
//		else if(!searchDate.equals("")  && !searchKeyword.equals("")){
//			String hql2 = " and ( infoArticle.articleName like '%"+searchKeyword+"%'"+
//					" or infoArticle.articleContent like '%"+searchKeyword+"%'"+
//					" or infoArticle.articleSource like '%"+searchKeyword+"%'"+
//					" or infoArticle.articleAuthor like '%"+searchKeyword+"%'"+
//					" and infoArticle.articlePublishtime>='"+searchDate+"')";
//			hql=hql1+hql2;
//		
//		}
//		//搜索日期空，搜索关键词不为空
//		else if(searchDate.equals("")  && !searchKeyword.equals("")){
//			String hql3 = " and ( infoArticle.articleName like '%"+searchKeyword+"%'"+
//					" or infoArticle.articleContent like '%"+searchKeyword+"%'"+
//					" or infoArticle.articleSource like '%"+searchKeyword+"%'"+
//					" or infoArticle.articleAuthor like '%"+searchKeyword+"%')";
//			hql=hql1+hql3;
//		}
//		//搜索日期不为空，搜索关键词不为空
//		else{ 
//			hql = hql1;
//			};
//				
//		String totalConut = null;
//		List<InfoArticle> results = null;
//		try {
//			org.hibernate.Query query = sessionFactory.getCurrentSession()
//					.createQuery(hql);
//			totalConut = String.valueOf(query.list().size());
//			System.out.println(totalConut);
//			query.setFirstResult(Integer.parseInt(start));
//			query.setMaxResults(Integer.parseInt(limit));
//			results = query.list();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Map<String, Object> myMapResult = new TreeMap<String, Object>();
//		myMapResult.put("total", totalConut);
//		myMapResult.put("root", results);
//		// System.out.println("myMapResult "+gridId+" "+myMapResult);
//
//		return myMapResult;
//
//	}
	

	/*
	 * updateRecTo1
	 * 20131128 @LF
	 */
	@Transactional
	public void updateRecTo1(String[] articleIds) {
		//System.out.println("time to recycleInfoArticle @InfoArticleService");
		InfoArticle result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < articleIds.length; i++) {

				result = (InfoArticle) session.get(InfoArticle.class,
						Integer.parseInt(articleIds[i]));

				result.setArticleIsrecycle("是");
				session.saveOrUpdate(result);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * updateRecTo0
	 * 2013-11-28 @LF
	 */
	@Transactional
	public void updateRecTo0(String[] articleIds) {
		//System.out.println("time to recycleInfoArticle @InfoArticleService");
		InfoArticle result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < articleIds.length; i++) {

				result = (InfoArticle) session.get(InfoArticle.class,
						Integer.parseInt(articleIds[i]));
				result.setArticleIsrecycle("否");
				session.saveOrUpdate(result);

			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * updateDraftTo0
	 * 2013-11-28 @LF
	 */
	@Transactional
	public void updateDraftTo0(String[] articleIds) {
		//System.out.println("time to recycleInfoArticle @InfoArticleService");
		InfoArticle result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < articleIds.length; i++) {

				result = (InfoArticle) session.get(InfoArticle.class,
						Integer.parseInt(articleIds[i]));
				result.setArticleIsdraft("否");
				
				Calendar ca = Calendar.getInstance();
		     	Date now = ca.getTime();
		     	//System.out.println("add_pubArticle: setArticlePublishtime is  " + now);
		     	result.setArticlePublishtime(now);
				
				
				session.saveOrUpdate(result);

			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * deleteArticleByIds
	 * 2013-11-28 @LF
	 */
	@Transactional
	public void deleteArticleByIds(String[] articleIds) {
		InfoArticle result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < articleIds.length; i++) {

				result = (InfoArticle) session.get(InfoArticle.class,
						Integer.parseInt(articleIds[i]));
				session.delete(result);

			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void updateOneArticle(InfoArticle infoArticle) {
		Session session = sessionFactory.getCurrentSession();   	 
     	session.saveOrUpdate(infoArticle); 
     	//System.out.println("updateOneDraft: "+infoArticle.getArticlePublishtime());
	}

	public InfoArticle getArticleById(String articleId) {
		InfoArticle result = null;
		Session session = sessionFactory.getCurrentSession();

		try {
				result = (InfoArticle) session.get(InfoArticle.class,
						Integer.parseInt(articleId));
				//System.out.println(result.getArticleName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;

	
	}

	

}