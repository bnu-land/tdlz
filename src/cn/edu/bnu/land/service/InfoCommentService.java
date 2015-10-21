package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.InfoCommentHome;
import cn.edu.bnu.land.model.InfoComment;



@Service
public class InfoCommentService{
	private InfoCommentHome infoCommentHome;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setInfoCommentHome(InfoCommentHome infoCommentHome){
		this.infoCommentHome = infoCommentHome;
		
	}
	
	
	
	public Map<String, Object> listInfoComment(String start, String limit, String articleId) {
		//System.out.println("LF @InfoCommentService listInfoComment");
		String hql = null;
		if(articleId.equals("")){
			hql = "from InfoComment infoComment";
			}
		else{
		 hql = "from InfoComment infoComment where infoComment.articleId = "+articleId;
		}
		//System.out.println(hql);
		String totalConut = null;
		List<InfoComment> results = null;
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
	
	
	
	
	public void updateComment(String[] commentIds) {
		// TODO Auto-generated method stub
		InfoComment result = null;
		InfoArticle articleResult = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < commentIds.length; i++) {

				result = (InfoComment) session.get(InfoComment.class,
						Integer.parseInt(commentIds[i]));
				session.delete(result);
				int articleId = result.getArticleId();
				articleResult = (InfoArticle) session.get(InfoArticle.class,articleId);
				//System.out.println(articleResult.getCommentNum());
				int commentNum = articleResult.getCommentNum()-1;
				articleResult.setCommentNum(commentNum);

			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}