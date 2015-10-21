package cn.edu.bnu.land.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.InfoArticleHome;


@Service
public class PublishService {

	private SessionFactory sessionFactory;
	private InfoArticle infoArticle;
    private InfoArticleHome infoArticleHome;
    
    @Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
    
    @Autowired
	public void setInfoArticleHome(InfoArticleHome infoArticleHome){
		this.infoArticleHome=infoArticleHome;
		
	}
    
    /*
     * 在表info_artical中添加一条记录
     * 
     * 参数：channelid 频道对应的id 可以通过查看a_block_channel_id查看不同的频道对应的id
     * 
     * */
    public void addPubTicketIn(InfoArticle infoArticle,int channelid){	
     	Session session = sessionFactory.getCurrentSession(); 	
     	Calendar ca = Calendar.getInstance();
     	Date now = ca.getTime();
     	System.out.println(now);
     	infoArticle.setArticlePublishtime(now);
     	infoArticle.setChannelId(channelid);//频道“信息转入”代码是4 请查询表"a_block_channel_id"
     	infoArticle.setArticleIsrecycle("否");
     	session.save(infoArticle); 	
	}
	
	public void updatePubTicketIn(InfoArticle infoArticle,int channelid){	
     	Session session = sessionFactory.getCurrentSession();   	 
     	infoArticle.setArticleIsrecycle("否");
     	infoArticle.setChannelId(channelid);
     	session.update(infoArticle); 	
	}	
	
	/**
	 * 根据id删除info_article表中记录。多条id之间使用','分隔
	 * 
	**/
	public void deletePubTicketIn(String ids){
		String[] idsArray=ids.split(",");
		for (int i=0;i<idsArray.length;i++){
			String hql="update InfoArticle s  set s.articleIsrecycle=true where s.articleId="+idsArray[i];
			Session session = sessionFactory.getCurrentSession();
			Query q=session.createQuery(hql);
			q.executeUpdate();
		}
	}
	
	/*
	 * 先按照目标字段精确检索，若无结果，再进行模糊检索。
	 * 
	 * 参数start分页首记录数，limit分页每页记录数，searchField搜索关键词,searchDate搜索日期,channelid 频道对应的id 对应关系请查看a_block_channel_id
	 *
	 * */
	public  Map<String,Object> selectPubTicketInTb(String start,String limit,String searchField,String searchDate,int channelid){
		String text=searchField;
		String hql="";
		String hql1="from InfoArticle p where p.channelId="+channelid+" and (p.articleIsrecycle is null or p.articleIsrecycle<>true)  ";
		String totalConut=new String();
		//搜索关键词不为空
		if(!searchField.equals("") && !searchField.isEmpty())
			{
			String hql2="";
			
			hql2=" and ( p.articleName='"+text+"'"+
					" or p.articleContent='"+text+"'"+
					" or p.articleUrl='"+text+"')";
			if(!searchDate.equals("") && !searchDate.isEmpty())//日期搜索不为空
			{		
				 hql2+=" and p.articlePublishtime>='"+searchDate+"'";
			}
			
			hql=hql1+hql2;
		}
		//搜索关键词为空 搜索日期不为空
		else if((searchField.equals("") || searchField.isEmpty())&&!searchDate.equals("") && !searchDate.isEmpty())
		{
			    hql=hql1+" and p.articlePublishtime>='"+searchDate+"'";			
		}
		else
			hql=" from InfoArticle p where p.channelId="+channelid+" and (p.articleIsrecycle is null or p.articleIsrecycle<>true) ";	
		
		System.out.println(hql);
	    List<InfoArticle> results=null;
	    try{
		    org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
		    
		    if(!query.list().isEmpty())
		    totalConut=String.valueOf(query.list().size());

		    query.setFirstResult(Integer.parseInt(start));
		    query.setMaxResults(Integer.parseInt(limit));
		    
		    results=(List<InfoArticle>)query.list();
		    if (results.isEmpty())
		    {	 
		    	String hql3="";
		        //开始模糊查询
		    	//搜索关键词不为空
		    	if(!searchField.equals("") && !searchField.isEmpty())
		    	{
					text="'%"+text+"%'";	
                    hql3=" and ( p.articleName like "+text+
							" or p.articleContent like "+text+
							" or p.articleUrl like "+text+")";
		    	}
		    	//搜索日期不为空
		    	if(!searchDate.equals("") && !searchDate.isEmpty())
		    	{
						hql3+=" and p.articlePublishtime>='"+searchDate+"'";
		    	}
		    	hql=hql1+hql3;
				System.out.println(hql);				
			    org.hibernate.Query myquery=sessionFactory.getCurrentSession().createQuery(hql);
			    
			    if(!myquery.list().isEmpty())
				    totalConut=String.valueOf(myquery.list().size());
			    
			    myquery.setFirstResult(Integer.parseInt(start));
			    myquery.setMaxResults(Integer.parseInt(limit));
			    
			    results=(List<InfoArticle>)myquery.list();		    
		    }
	    }
	    catch(RuntimeException re)
	    {
	    }
	    
	    
	    Map<String,Object> myMapResult= new TreeMap<String,Object>();
	   
	    System.out.println("记录总数： "+totalConut);
	    myMapResult.put("total", new String(totalConut));
	    myMapResult.put("root",results);
	    
	    return myMapResult;
	}
	

}
