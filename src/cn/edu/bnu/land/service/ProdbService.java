package cn.edu.bnu.land.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bnu.land.model.AbnmWholesupervision;
import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.model.PublicProjectHome;
import cn.edu.bnu.land.model.DkMonitordata;
import cn.edu.bnu.land.model.DkMonitordataHome;
import cn.edu.bnu.land.model.ProjectProgrestatic2;
import cn.edu.bnu.land.model.ProjectYqProgress;
import cn.edu.bnu.land.model.RssAreaCode;
import cn.edu.bnu.land.model.RwTable;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.model.FkDikuai;
import cn.edu.bnu.land.model.AbnmWholesupervision;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javassist.bytecode.Descriptor.Iterator;

@Service
public class ProdbService {
	private static final Date[] Date = null;
	private PublicProjectHome publicProjectHome;
	private DkMonitordataHome DkMonitorDataHome;
	private SessionFactory sessionFactory;
	private Configuration configuration = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setPublicProjectHome(PublicProjectHome publicProjectHome){
		this.publicProjectHome=publicProjectHome;
	}
	@Autowired
	public void setDkMonitordataHome(DkMonitordataHome MonitorDataHome){
		this.DkMonitorDataHome=MonitorDataHome;
	}

	public List<PublicProject> fkSchemeShow1() {
		String hql="from PublicProject as pubprj where pubprj.showYesno=' '";
		List<PublicProject> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			results=query.list();
			System.out.println("totalnoshow:"+String.valueOf(query.list().size()));
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		return results;
	}

//	public Map<String, Object> fkSchemeShow2(String start, String limit,String projectname,String province,String city,String county,String showstarttime,String showendtime,String  statusId) {
//		String hql="from PublicProject as publicProject where statusId="+statusId;
//		String hql1="projectname like '%"+projectname+"%'";
//		String hql2="( showStarttime>='"+showstarttime+"' and showStarttime<='"+showendtime+"')";//作为一个语句，要加（），同时注意空格，细节！！！
//		//日期和时间是否为空，分4种
//		if(projectname.equals("") && showstarttime.equals("")){ 
//			hql="from PublicProject as publicProject where statusId="+statusId;}
//		else if(projectname.equals("") && !showstarttime.equals("")){ 
//			hql=hql+" and "+hql2;}//where 和 and  前后都要有空格
//		else if(!projectname.equals("") && showstarttime.equals("")){ 
//			hql=hql+" and "+hql1;}
//		else if(!projectname.equals("") && !showstarttime.equals("")){ 
//			hql=hql+" and "+hql1+" and "+hql2;}
//		System.out.println(hql); 
//		String totalConut=null;
//		List<PublicProject> results=null;
//		try{
//			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
//			totalConut=String.valueOf(query.list().size());//获得此次搜索的总记录数
//			System.out.println(totalConut);
//			query.setFirstResult(Integer.parseInt(start));//设置搜索结果的首记录和每页的记录数
//			query.setMaxResults(Integer.parseInt(limit));//设置搜索结果每页的记录数
//			results=query.list();
//		}
//		catch(RuntimeException e){	
//			e.printStackTrace();}
//		Map<String,Object> myMapResult=new TreeMap<String,Object>();
//		myMapResult.put("total", new String(totalConut));//将总记录数，放到Map的total结点中，
//		myMapResult.put("root",results);//将查询出来的list，放到Map的root结点中
//		return myMapResult;
//	}
	public Map<String, Object> fkSchemeShow3(String start, String limit,String projectId,String province,String city,String county,String town,String t1,String t2) {
		String hql= "select new map(pubprj.recordId  as recordId,"
				+ "pubprj.projectId as projectId,pubprj.projectname  as projectname,"
				+ "pubprj.rlocation   as rlocation,pubprj.rmapnumber  as rmapnumber,"
				+ "pubprj.rarea  as rarea,pubprj.rqualityFlag as rqualityFlag,"
				+ "pubprj.conUnit as conUnit,pubprj.starttime as starttime,"
				+ "pubprj.completiontime as completiontime,pubprj.rmoney as rmoney,"
				+ "pubprj.areaCode as areaCode,pubprj.showYesno as showYesno,"
				+ "pubprj.isfinished as isfinished,pubprj.latelyMonitortime as latelyMonitortime,"
				+ "pubprj.ppercentPz as ppercentPz,pubprj.ppercentSl as ppercentSl,"
				+ "pubprj.ppercentDl as ppercentDl,pubprj.isNormal as isNormal) from PublicProject as pubprj,"
				+ "InfoArticle as art where pubprj.showYesno=art.articleId and art.articleIsrecycle='否 ' and pubprj.isfinished='0'";
		String hql1=" ";
		String hql2=" ";
		String hql3=" ";
		//项目名称
		if(!projectId.equals(""))   {hql1=" and pubprj.projectId like '%"+projectId+"%'";}

		//行政区代码
		String p1="";
		if(!province.equals("") && city.equals(""))    { p1=province.substring(0,2); hql2=" and  pubprj.areaCode like '"+p1+"%'";}
		else if(!city.equals("") &&county.equals(""))  { p1=city.substring(0,4); hql2=" and pubprj.areaCode like '"+p1+"%'";}
		else if(!county.equals("") &&town.equals(""))  { p1=county.substring(0,6); hql2=" and pubprj.areaCode like '"+p1+"%'";}
		else if(!town.equals(""))                      { p1=town ; hql2=" and pubprj.areaCode="+p1;}

		//开始施工时间
		if(!t1.equals("") &&t2.equals(""))   hql3=" and pubprj.starttime>='"+t1+"'";	
		else if(t1.equals("") &&!t2.equals(""))  hql3=" and pubprj.starttime<='"+t2+"'";
		else if(!t1.equals("") &&!t2.equals("")) hql3=" and pubprj.starttime>='"+t1+"' and pubprj.starttime<='"+t2+"'";

		hql=hql+hql1+hql2+hql3;
		System.out.println(hql); 
		String totalConut=null;
		List<PublicProject> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			totalConut=String.valueOf(query.list().size());//获得此次搜索的总记录数
			System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));//设置搜索结果的首记录和每页的记录数
			query.setMaxResults(Integer.parseInt(limit));//设置搜索结果每页的记录数
			results=query.list();
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		Map<String,Object> myMapResult=new TreeMap<String,Object>();
		myMapResult.put("total", new String(totalConut));//将总记录数，放到Map的total结点中，
		myMapResult.put("root",results);//将查询出来的list，放到Map的root结点中
		return myMapResult;
	}
	public Map<String, Object> fkSchemeShow4(String start, String limit,String projectname,String province,String city,String county,String monitorstarttime,String monitorendtime) {
		String hql="from PublicProject as publicProject  ";
		String hql1="projectname like '%"+projectname+"%'";
		String hql2="( monitorFrequency>='"+monitorstarttime+"' and monitorFrequency<='"+monitorendtime+"')";//作为一个语句，要加（），同时注意空格，细节！！！
		//日期和时间是否为空，分4种
		if(projectname.equals("") && monitorstarttime.equals("")){ 
			hql=hql;}
		else if(projectname.equals("") && !monitorstarttime.equals("")){ 
			hql=hql+" and  "+hql2;}//where 和 and  前后都要有空格
		else if(!projectname.equals("") && monitorstarttime.equals("")){ 
			hql=hql+" and "+hql1;}
		else if(!projectname.equals("") && !monitorstarttime.equals("")){ 
			hql=hql+" and "+hql1+" and "+hql2;}
		System.out.println(hql); 
		String totalConut=null;
		List<PublicProject> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			totalConut=String.valueOf(query.list().size());//获得此次搜索的总记录数
			System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));//设置搜索结果的首记录和每页的记录数
			query.setMaxResults(Integer.parseInt(limit));//设置搜索结果每页的记录数
			results=query.list();
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		Map<String,Object> myMapResult=new TreeMap<String,Object>();
		myMapResult.put("total", new String(totalConut));//将总记录数，放到Map的total结点中，
		myMapResult.put("root",results);//将查询出来的list，放到Map的root结点中
		return myMapResult;
	}




	//监测任务计划显示与搜索
	public Map<String, Object> yqprojectmonitorInfo(String start, String limit,String projectId,String t1,String t2,String accuracy) {
		String hql="from ProjectYqProgress as yq ";
		String hql1="";
		String hql2="";
		List<ProjectYqProgress> results=null;
		String totalConut=null;
		//名称
		if(accuracy.equals("0")&& !projectId.equals(""))	 hql1=" where yq.projectId like '%"+projectId+"%'";
		if(accuracy.equals("1")&& !projectId.equals(""))	 hql1=" where yq.projectId = '"+projectId+"'";//精确搜索项目，用于跳转
		if(accuracy.equals("2")) hql1=" where yq.projectId = '"+projectId+"' and yq.isFinished='1' ";//仅显示已完成的任务情况
		//时间
		if(!t1.equals("") && t2.equals("")) hql2=" yq.monitorTime>='"+t1+"'";
		else if(t1.equals("") &&!t2.equals(""))  hql2=" yq.monitorTime<='"+t2+"'";
		else if(!t1.equals("") &&!t2.equals("")) hql2=" yq.monitorTime>='"+t1+"' and yq.monitorTime<='"+t2+"'";
		if(!hql1.equals("") && !hql2.equals(""))  hql2=" and "+hql2;
		else if(hql1.equals("") && !hql2.equals(""))   hql2=" where "+hql2;
		//合并：名称和时间
		hql=hql+hql1+hql2;
		System.out.println(hql);
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			totalConut=String.valueOf(query.list().size());//获得此次搜索的总记录数
			System.out.println("totalConut:"+totalConut); 
			query.setFirstResult(Integer.parseInt(start));//设置搜索结果的首记录和每页的记录数
			query.setMaxResults(Integer.parseInt(limit));//设置搜索结果每页的记录数
			results=query.list();
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		Map<String,Object> myMapResult=new TreeMap<String,Object>();
		myMapResult.put("total", new String(totalConut));//将总记录数，放到Map的total结点中，
		myMapResult.put("root",results);//将查询出来的list，放到Map的root结点中
		return myMapResult;
	}

	//PDA现场数据显示与搜索
	public Map<String, Object> syrMonitorData(String start, String limit,String projectname,String monitorstarttime,String monitorendtime,String accuracy) {
		String hql="from DkMonitordata as DkMonitordata ";
		String hql1="";
		String hql2="";
		String totalConut=null;
		List<DkMonitordata> results=null;
		//名称
		if(accuracy.equals("0")&&!projectname.equals(""))	 hql1=" where projectName like '%"+projectname+"%'";
		if(accuracy.equals("1")&&!projectname.equals(""))	 hql1=" where projectName ='"+projectname+"'";
		//时间
		if(!monitorstarttime.equals("") && monitorendtime.equals(""))      {hql2=" monitorTime>='"+monitorstarttime+"'";}
		else if(monitorstarttime.equals("") &&!monitorendtime.equals(""))  {hql2=" monitorTime<='"+monitorendtime+"'";}
		else if(!monitorstarttime.equals("") &&!monitorendtime.equals("")) {hql2=" monitorTime>='"+monitorstarttime+"' and monitorTime<='"+monitorendtime+"'";}
		if(!hql1.equals("") && !hql2.equals(""))  hql2=" and "+hql2;
		else if(hql1.equals("") && !hql2.equals(""))   hql2=" where "+hql2;
		//合并：名称和时间
		hql=hql+hql1+hql2;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			totalConut=String.valueOf(query.list().size());//获得此次搜索的总记录数
			System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));//设置搜索结果的首记录和每页的记录数
			query.setMaxResults(Integer.parseInt(limit));//设置搜索结果每页的记录数
			results=query.list();
		}
		catch(RuntimeException e){	
			e.printStackTrace();}

		Map<String,Object> myMapResult=new TreeMap<String,Object>();
		myMapResult.put("total", new String(totalConut));//将总记录数，放到Map的total结点中，
		myMapResult.put("root",results);//将查询出来的list，放到Map的root结点中
		return myMapResult;
	}



	public Map<String, Object> syrProgress(String projectname,String monitorstarttime,String monitorendtime) {
		String hql="from ProjectProgrestatic2 as ProjectProgrestatic2 where projectname = '" + projectname +"' and time>='"+monitorstarttime+"' and time<='"+monitorendtime+"'";
		String totalConut=null;
		List<ProjectProgrestatic2> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			totalConut=String.valueOf(query.list().size());//获得此次搜索的总记录数
			System.out.println(totalConut);
			//			query.setFirstResult(Integer.parseInt(start));//设置搜索结果的首记录和每页的记录数
			//			query.setMaxResults(Integer.parseInt(limit));//设置搜索结果每页的记录数
			results=query.list();
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		Map<String,Object> myMapResult=new TreeMap<String,Object>();
		myMapResult.put("total", new String(totalConut));//将总记录数，放到Map的total结点中，
		myMapResult.put("root",results);//将查询出来的list，放到Map的root结点中
		return myMapResult;
	}




	//1.1文章发布
	public void updateOneArticleShow(InfoArticle infoArticle,String projectId) {
		Session session = sessionFactory.getCurrentSession();   	 
		session.saveOrUpdate(infoArticle); 

		//更新public_project字段
		org.hibernate.Query query=session.createQuery("update PublicProject projectall set projectall.showYesno ='"+infoArticle.getArticleId()+"' WHERE projectId = '"+projectId+"'");
		query.executeUpdate();  
	}
	//1.2存入草稿箱
	public void updateOneArticle(InfoArticle infoArticle,String projectId) {
		Session session = sessionFactory.getCurrentSession();   	 
		session.saveOrUpdate(infoArticle); 
		//更新public_project字段
		org.hibernate.Query query=session.createQuery("update PublicProject projectall set projectall.showYesno ='"+infoArticle.getArticleId()+"' WHERE projectId = '"+projectId+"'");
		query.executeUpdate();  
		}
	//	//1.3移入回收站
	//	@Transactional
	//	public void updateRecTo1(String[] articleIds) {
	//		System.out.println("time to recycleInfoArticle @InfoArticleService");
	//		InfoArticle result = null;
	//		Session session = sessionFactory.getCurrentSession();
	//		try {
	//			for (int i = 0; i < articleIds.length; i++) {
	//				result = (InfoArticle) session.get(InfoArticle.class,
	//						Integer.parseInt(articleIds[i]));
	//				result.setArticleIsrecycle("是");
	//				session.saveOrUpdate(result);
	//				org.hibernate.Query query=session.createQuery("from PublicProject projectall WHERE showYesno = '"+result.getArticleId()+"'");
	//				PublicProject pubprj=(PublicProject) query.list().get(0);//列表
	//				pubprj.setShowYesno(" ");
	//				sessionFactory.getCurrentSession().update(pubprj);  
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//
	//	}
	//	//2.1草稿箱发布
	//	@Transactional
	//	public void updateDraftTo0(String[] articleIds,String[] prjIds) {
	//		System.out.println("time to recycleInfoArticle @InfoArticleService");
	//		InfoArticle result = null;
	//		try {
	//
	//			for (int i = 0; i < articleIds.length; i++) {
	//				result = (InfoArticle) sessionFactory.getCurrentSession().get(InfoArticle.class,
	//						Integer.parseInt(articleIds[i]));
	//				result.setArticleIsdraft("否");		
	//				Calendar ca = Calendar.getInstance();
	//				java.util.Date now =ca.getTime();
	//				result.setArticlePublishtime(now);
	//				sessionFactory.getCurrentSession().saveOrUpdate(result);
	//				//public_project更新字段
	//				org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery("from PublicProject projectall WHERE showYesno = '"+result.getArticleId()+"'");
	//				PublicProject pubprj=(PublicProject) query.list().get(0);//列表
	//				System.out.println("size--------"+query.list().size());
	//				System.out.println("项目名称"+pubprj.getProjectname());
	//				pubprj.setShowYesno("true");
	//				sessionFactory.getCurrentSession().update(pubprj); 
	//			}
	//
	//
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//
	//	}
	//1.2彻底删除公示

	@Transactional
	public void deleteArticleByIds(String[] articleIds) {
		InfoArticle result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < articleIds.length; i++) {

				result = (InfoArticle) session.get(InfoArticle.class,
						Integer.parseInt(articleIds[i]));
				session.delete(result);
				org.hibernate.Query query=session.createQuery("from PublicProject projectall WHERE showYesno = '"+result.getArticleId()+"'");
				PublicProject pubprj=(PublicProject) query.list().get(0);//列表
				pubprj.setShowYesno(" ");
				sessionFactory.getCurrentSession().update(pubprj);  
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public  void delMonitorStatic(ProjectProgrestatic2 projectProgrestatic2){
		try{
			org.hibernate.Query query1=sessionFactory.getCurrentSession().createQuery("delete  from ProjectProgrestatic2 as pp");
		}
		catch(RuntimeException e){
			e.printStackTrace();}
	}





	public  void saveMonitorStatic(String projectname,String monitorstarttime,String monitorendtime){

		Session session =sessionFactory.getCurrentSession();
		//最新算法  	
		// 
		String hql="select pp.monitorTime,pp.projectname,SUM(sc.dkArea*md.tdpzPercent)/SUM(sc.dkArea),"
				+ "SUM(sc.dkArea*md.ntslPercent)/SUM(sc.dkArea),"
				+ "SUM(sc.dkArea*md.tjdlPercent)/SUM(sc.dkArea),"
				+ "pp.ppercentPz,pp.ppercentSl,pp.ppercentDl"
				+ " from  DkMonitordata AS md,FkDikuai AS sc,ProjectYqProgress as pp "
				+ " WHERE md.dkId=sc.dkId AND md.projectname=sc.projectname AND md.rwId=pp.rwId AND pp.projectname='"+projectname+"' and pp.monitorTime<='"+monitorendtime+"' and pp.monitorTime >='"+monitorstarttime+"'"
				+ " GROUP BY pp.rwId  ORDER BY pp.monitorTime ASC";
		System.out.println(hql); 
		try{
			org.hibernate.Query query=session.createQuery(hql);
			List result = query.list();
			System.out.println(result.size()); 
			Object[] prj=null;
			ProjectProgrestatic2 prjyq= new ProjectProgrestatic2();
			for(int i = 0; i < result.size();i++){
				prj=(Object[]) result.get(i);
				prjyq= new ProjectProgrestatic2();
				prjyq.setTime((java.util.Date) prj[0]);
				prjyq.setProjectName((String) prj[1]);		
				prjyq.setPpercentPz(new Float(prj[3].toString()));
				prjyq.setPpercentSl(new Float(prj[3].toString()));
				prjyq.setPpercentDl(new Float(prj[3].toString()));
				prjyq.setYpercentPz(new Float(prj[3].toString()));
				prjyq.setYpercentSl(new Float(prj[3].toString()));
				prjyq.setYpercentDl(new Float(prj[3].toString()));  
				sessionFactory.getCurrentSession().saveOrUpdate(prjyq);
			}
		}
		catch(RuntimeException e){	
			e.printStackTrace();}

	}

	// 多行！ues freemarker+xml to doc
	public void updateuseFreemarker2doc() {
		// 要填入模本的数据文件
		String hql = "from ProjectProgrestatic2 as projectProgrestatic2 ";
		List<ProjectProgrestatic2> results=null;
		try {
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			System.out.println(hql+"  2"); 
			results=query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		// 这里我们的模板是放在com.havenliu.document.template包下面
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(),
				"/cn/edu/bnu/land/common");
		Template t = null;

		try {			
			t = configuration.getTemplate("freemarker2doc22.ftl");// test.ftl为要装载的模板
			t.setEncoding("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}



		// 输出文档路径及名称
		Calendar ca = Calendar.getInstance();
		java.util.Date date = ca.getTime();
		java.text.DateFormat format2 = new java.text.SimpleDateFormat(
				"yyyyMMddhhmmss");
		String dateString = format2.format(date);
		//name不同
		for(int i = 0; i < results.size();i++){
			//文件名称
			String m_fileName = (results).get(i).getTime()+ "_" + dateString + ".doc";
			System.out.println("m_fileName:"+m_fileName);
			//文件路径
			String realPath=this.getClass().getClassLoader().getResource("/").getPath();
			System.out.println("realPath:"+realPath);
			int pos=realPath.indexOf("/springsource2");
			System.out.println("pos:"+pos);
			realPath=realPath.substring(0, pos);
			//realPath=realPath+"/Upload/log_Monitor/"; 
			//System.out.println("realPath:"+realPath);
			String filePath = realPath +"/"+ m_fileName;
			System.out.println("filePath:"+filePath);
			results.get(i).setReport(("WebContent/download/"+m_fileName));

			File outFile = new File(filePath);
			Writer out = null;
			try {
				//			out = new BufferedWriter(new OutputStreamWriter(
				//					new FileOutputStream(outFile), "utf-8"));
				out = new OutputStreamWriter(new FileOutputStream(outFile), "utf-8");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				t.process((results).get(i), out);
				out.close();
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//将结果用HashMap封装起来，然后遍历输出word
	//		HashMap map=new HashMap();
	//		try {
	//			map=new HashMap();
	//			map.put("prjList",results);
	//			t.process(map, out);
	//			out.close();
	//		} catch (TemplateException e) {
	//			e.printStackTrace();
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}


	//单记录存储
	public void updateuseFreemarker2docOne() throws ClassNotFoundException, SQLException {
		// 要填入模本的数据文件
		//		//Date类型转换为String
		//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//		String time = sdf.format(monitortime);
		String hql = "from ProjectProgrestatic2 as projectProgrestatic2 ";
		ProjectProgrestatic2 myPp=null;
		try {			
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			List<ProjectProgrestatic2> results=null;
			results=query.list();
			myPp=results.get(0);
			String totalConut=String.valueOf(query.list().size());//获得此次搜索的总记录数
			System.out.println("totalConut:"+totalConut);
			System.out.println(myPp.getProjectName());

		} catch (Exception e) {
			e.printStackTrace();
		}


		// 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		// 这里我们的模板是放在com.havenliu.document.template包下面
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(),
				"/cn/edu/bnu/land/common");
		Template t = null;
		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate("freemarker2doc22.ftl");
			t.setEncoding("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}


		// 输出文档路径及名称
		Calendar ca = Calendar.getInstance();
		java.util.Date date = ca.getTime();
		java.text.DateFormat format2 = new java.text.SimpleDateFormat(
				"yyyyMMddhhmmss");
		String dateString = format2.format(date);
		//文件名称
		//String m_fileName = myPp.getProjectName()+"_"+myPp.getTime()+"_"+ dateString + ".doc";
		System.out.println("totalConut:"+myPp.getTime());
		String m_fileName = myPp.getTime()+"_"+ dateString + ".doc";

		System.out.println("realPath:"+m_fileName);

		//文件路径
		String realPath=this.getClass().getClassLoader().getResource("/").getPath();
		System.out.println("realPath:"+realPath);
		int pos=realPath.indexOf("/WEB");
		//System.out.println("pos:"+pos);
		realPath=realPath.substring(0, pos);
		//realPath=realPath+"/Upload/log_Monitor/"; 
		//System.out.println("realPath:"+realPath);
		String filePath = realPath +m_fileName;
		//String filePath = "d:/" +m_fileName;
		System.out.println("filePath:"+filePath);

		//存储最后一个字段
		myPp.setReport("/tdlzjg/Upload/log_Monitor/"+m_fileName);
		//		myPp.setReport(m_fileName);
		sessionFactory.getCurrentSession().saveOrUpdate(myPp);

		//		Connection conyc;
		//		conyc = dataSource.getConnection();
		//		Statement stmtyc = conyc.createStatement();
		//		String usqyc="insert into project_progrestatic2(report) values ('" + m_fileName+ "')";
		//		stmtyc.executeUpdate(usqyc);


		File outFile = new File(filePath);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			t.process(myPp,out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	///添加项目预期监测信息
	public void addYqprojectmonitorInfo(ProjectYqProgress projectYqProgress) {
		Session session = sessionFactory.getCurrentSession();	
		session.save(projectYqProgress);

	}




	@Transactional
	public void deleteyqdatabyIds(String[] recordIds) 
	{

		ProjectYqProgress result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < recordIds.length; i++) {

				result = (ProjectYqProgress) session.get(ProjectYqProgress.class,
						Integer.parseInt(recordIds[i]));
				session.delete(result);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//进度异常（慢），写入刘方的异常表
	public  void  saveAbnormalprogrss(){
		Session session =sessionFactory.getCurrentSession();
		try{ 
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery
					("select yq.projectname,yq.time from ProjectProgrestatic2 as yq where yq.ppercentPz<yq.ypercentPz or yq.ppercentSl<yq.ypercentSl or yq.ppercentDl<yq.ypercentDl");
			List results=query.list();//结果列表results
			Object[] prj=null;
			AbnmWholesupervision Abnormalprogrss=new AbnmWholesupervision();
			for(int i=0;i<results.size();i++){
				prj=(Object[]) results.get(i);	//prj是第i条查询结果
				Abnormalprogrss.setAbwsTitle(prj[0]+"进度异常");
				Abnormalprogrss.setAbwsClass("复垦动态监测");
				Abnormalprogrss.setAbwsDate((java.util.Date) prj[1]);
				Abnormalprogrss.setAbwsContent("复垦进度落后于预期进度");
				sessionFactory.getCurrentSession().saveOrUpdate(Abnormalprogrss);
			}
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
	}



	//1、四级联动---省列表
	public List<RssAreaCode> listProvince(){
		String hql="from RssAreaCode as rssAreaCode where areaCode like '%0000000'";
		List<RssAreaCode> results=null;
		System.out.println(hql+"  1"); 
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			System.out.println(hql+"  2"); 
			results=query.list();
			for(RssAreaCode rssAreaCode:results){
				System.out.println(rssAreaCode.getAreaName()+"you"); 
			}
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		return results;
	}


	//2、四级联动---市列表
	public List<RssAreaCode> listCity(String codeHead){
		String code12=null;
		List<RssAreaCode> results=null;
		if(codeHead==null ||codeHead.length()<2) return results;
		else {
			code12=codeHead.substring(0,2);
			String hql="from RssAreaCode as rssAreaCode where areaCode like '"+code12+"%00000' and areaCode !='"+code12+"0000000'";
			System.out.println(hql+"  1"); 
			try{
				org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
				results=query.list();
				System.out.println("results.size:"+results.size()); 
				for(RssAreaCode rssAreaCode:results){
					System.out.println(rssAreaCode.getAreaName()+"you"); 
				}
				//System.out.println(hql+"  2"); 
			}
			catch(RuntimeException e){	
				e.printStackTrace();}
			return results;}
	}
	//3、四级联动---县列表
	public List<RssAreaCode> listCounty(String codeHead){
		String code12=null;
		List<RssAreaCode> results=null;
		if(codeHead==null ||codeHead.length()<4)  return results;
		else {
			code12=codeHead.substring(0,4);		
			String hql="from RssAreaCode as rssAreaCode where areaCode like '"+code12+"%000' and areaCode !='"+code12+"00000'";
			System.out.println(hql+"  1"); 
			try{
				org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
				System.out.println(hql+"  2"); 
				results=query.list();
				for(RssAreaCode rssAreaCode:results){
					System.out.println(rssAreaCode.getAreaName()+"you"); 
				}
			}
			catch(RuntimeException e){	
				e.printStackTrace();}
			return results;
		}
	}
	//4、四级联动---乡镇街道列表
	public List<RssAreaCode> listTown(String codeHead){
		String code12=null;
		if(codeHead==null ||codeHead.length()<6)  codeHead="500115000";
		code12=codeHead.substring(0,6);
		String hql="from RssAreaCode as rssAreaCode where areaCode like '"+code12+"%' and areaCode !='"+code12+"000'";
		List<RssAreaCode> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			System.out.println(hql+"  2"); 
			results=query.list();
			for(RssAreaCode rssAreaCode:results){
				System.out.println(rssAreaCode.getAreaName()+"you"); 
			}
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		return results;
	}

	//5、项目名称得到项目ID列表
	public Map<String, Object> listprojectID(String projectName){
		String hql;
		String totalConut = new String();
		if(projectName.equals("")) hql="from PublicProject as publicProject";
		else  hql="from PublicProject as publicProject where projectname ='"+projectName+"'";
		List<PublicProject> results=null;
		String res="";
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			System.out.println(hql); 
			results=query.list();
			for(PublicProject p:results){
				System.out.println("idList:"+p.getProjectId()); 
				res=p.getProjectId();
			}
		}
		catch(RuntimeException e){	
			e.printStackTrace();}
		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("success", true);
		myMapResult.put("projectId", res);
		return myMapResult;
	}	


	public Map<String, Object>  resultselect(String start, String limit,String projectname){
		String totalConut = new String();
		String hql="from FkDikuai as fkDikuai";
		if(!projectname.equals("")){hql = "from FkDikuai as fkDikuai where fkDikuai.projectname ='"+projectname+"'";}
		List<FkDikuai> results=null;
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));	
			results=(List<FkDikuai>)query.list();		
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
	public void updateYqtable(ProjectYqProgress yq) {
		Session session = sessionFactory.getCurrentSession();   	 
		session.saveOrUpdate(yq); 
	}
}

