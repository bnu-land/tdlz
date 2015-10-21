package cn.edu.bnu.land.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.mail.MessagingException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.AttachmentMail;
import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.InfoLetter;
import cn.edu.bnu.land.model.InfoLetterHome;
import cn.edu.bnu.land.model.MailSenderInfo;


@Service
public class InfoLetterService{
	private InfoLetterHome infoLetterHome;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setInfoLetterHome(InfoLetterHome infoLetterHome){
		this.infoLetterHome = infoLetterHome;
		
	}
	
	
	
	public Map<String, Object> getInfoLetterList(String start, String limit, String searchKeyword, String searchDate) {
		//String hql = "from InfoLetter as infoLetter";
		//System.out.println(hql);
		
		String hql = "";
		String hql1 = "from InfoLetter as infoLetter where 1=1";
		//搜索日期不为空，搜索关键词空
		if(!searchDate.equals("")  && searchKeyword.equals("")){
			hql=hql1+" and infoLetter.letterWritetime>='"+searchDate+"'";	
		}
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate.equals("")  && !searchKeyword.equals("")){
			String hql2 = " and ( infoLetter.letterTitle like '%"+searchKeyword+"%'"+
					" or infoLetter.letterContent like '%"+searchKeyword+"%'"+
					" or infoLetter.letterAuthor like '%"+searchKeyword+"%'"+
					" or infoLetter.letterAddress like '%"+searchKeyword+"%'"+
					" or infoLetter.letterReplycontent like '%"+searchKeyword+"%'"+					
					" and infoLetter.letterWritetime>='"+searchDate+"')";
			hql=hql1+hql2;
		
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate.equals("")  && !searchKeyword.equals("")){
			String hql3 = " and ( infoLetter.letterTitle like '%"+searchKeyword+"%'"+
					" or infoLetter.letterContent like '%"+searchKeyword+"%'"+		
					" or infoLetter.letterAuthor like '%"+searchKeyword+"%'"+
					" or infoLetter.letterAddress like '%"+searchKeyword+"%'"+
					" or infoLetter.letterReplycontent like '%"+searchKeyword+"%')";
			hql=hql1+hql3;
		}
		//搜索日期不为空，搜索关键词不为空
		else{ 
			hql = hql1;
			};
		
		
		
		
		String totalConut = null;
		List<InfoLetter> results = null;
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
	
	
	/*
	 * deleteLetterByIds
	 * @LF  2013-12-02
	 */
	public void deleteLetterByIds(String[] letterIds) {
		// TODO Auto-generated method stub
		InfoLetter result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < letterIds.length; i++) {

				result = (InfoLetter) session.get(InfoLetter.class,
						Integer.parseInt(letterIds[i]));
				session.delete(result);

			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/*
	 * @LF 2013-12-02
	 */
	public void updateLetter(InfoLetter infoLetter) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();   	 
     	session.saveOrUpdate(infoLetter); 
     
     	
//     	测试回复同时发送邮件，测试成功
    	Boolean flag = true;
     	MailSenderInfo mailInfo = new MailSenderInfo();
        //mailInfo.setMailServerHost("smtp.qq.com");
     	mailInfo.setMailServerHost("smtp.163.com");
        //发送邮箱参数设置
        // qq，163,sina接收邮件测试通过
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("tudiliuzhuan2013");
        mailInfo.setPassword("tudiliuzhuan");
        mailInfo.setFromAddress("tudiliuzhuan2013@163.com");
      //构建邮件内容
        mailInfo.setToAddress(infoLetter.getLetterEmail());
        mailInfo.setSubject("关于“"+infoLetter.getLetterTitle()+"”的信息回复"); 
        mailInfo.setContent(infoLetter.getLetterReplycontent());
        AttachmentMail am = new AttachmentMail();
        //初始化mail信息
        try {
			if (am.sendAttachmentMail(mailInfo)==false) 
				flag=false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
     	
	}
	
	
	
	
}