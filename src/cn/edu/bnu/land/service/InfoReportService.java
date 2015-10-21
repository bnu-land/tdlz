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
import cn.edu.bnu.land.model.InfoReport;
import cn.edu.bnu.land.model.InfoReportHome;
import cn.edu.bnu.land.model.MailSenderInfo;


@Service
public class InfoReportService{
	private InfoReportHome infoReportHome;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setInfoReportHome(InfoReportHome infoReportHome){
		this.infoReportHome = infoReportHome;
		
	}
	public void addReport(InfoReport infoReport) {
		//System.out.println("now is in InfoReportService");
		Session session = sessionFactory.getCurrentSession();
		session.save(infoReport);
		
	}
	public Map<String, Object> getInfoReportList(String start, String limit, String searchKeyword, String searchDate) {
//		String hql = "from InfoReport as infoReport";
//		System.out.println(hql);
		
		
		String hql = "";
		String hql1 = "from InfoReport as infoReport where 1=1";
		//搜索日期不为空，搜索关键词空
		if(!searchDate.equals("")  && searchKeyword.equals("")){
			hql=hql1+" and infoReport.reportTime>='"+searchDate+"'";	
		}
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate.equals("")  && !searchKeyword.equals("")){
			String hql2 = " and ( infoReport.reporterName like '%"+searchKeyword+"%'"+
					" or infoReport.reportTitle like '%"+searchKeyword+"%'"+
					" or infoReport.reportClass like '%"+searchKeyword+"%'"+
					" or infoReport.reportContent like '%"+searchKeyword+"%'"+
					" or infoReport.reportedPlace like '%"+searchKeyword+"%'"+		
					" or infoReport.reportedDepartlevel like '%"+searchKeyword+"%'"+	
					" or infoReport.reportedDepartname like '%"+searchKeyword+"%'"+	
					" or infoReport.reportedName like '%"+searchKeyword+"%'"+	
					" or infoReport.reportReplyer like '%"+searchKeyword+"%'"+	
					" or infoReport.reportReplycontent like '%"+searchKeyword+"%'"+
					" and infoReport.reportTime>='"+searchDate+"')";
			hql=hql1+hql2;
		
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate.equals("")  && !searchKeyword.equals("")){
			String hql3 = "and ( infoReport.reporterName like '%"+searchKeyword+"%'"+
					" or infoReport.reportTitle like '%"+searchKeyword+"%'"+
					" or infoReport.reportClass like '%"+searchKeyword+"%'"+
					" or infoReport.reportContent like '%"+searchKeyword+"%'"+
					" or infoReport.reportedPlace like '%"+searchKeyword+"%'"+		
					" or infoReport.reportedDepartlevel like '%"+searchKeyword+"%'"+	
					" or infoReport.reportedDepartname like '%"+searchKeyword+"%'"+	
					" or infoReport.reportedName like '%"+searchKeyword+"%'"+
					" or infoReport.reportReplyer like '%"+searchKeyword+"%')";
			hql=hql1+hql3;
		}
		//搜索日期不为空，搜索关键词不为空
		else{ 
			hql = hql1;
			};
		
		
		
		
		
		
		
		
		String totalConut = null;
		List<InfoReport> results = null;
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
	
	
	
	public void updateReport(InfoReport infoReport) {
		Session session = sessionFactory.getCurrentSession();   	 
     	session.saveOrUpdate(infoReport); 
     	
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
        mailInfo.setToAddress(infoReport.getReporterEmail());
        mailInfo.setSubject("关于“"+infoReport.getReportTitle()+"”的信息回复"); 
        mailInfo.setContent(infoReport.getReportReplycontent());
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