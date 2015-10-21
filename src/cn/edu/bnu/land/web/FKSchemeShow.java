package cn.edu.bnu.land.web;
//Generated 2013-8-20 17:21:01 by Hibernate Tools 4.0.0
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.AutoSendingTask;
import cn.edu.bnu.land.common.SpringContextHolder;
import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.ProjectArticle;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.model.PublicProjectHome;
import cn.edu.bnu.land.model.DkMonitordata;
import cn.edu.bnu.land.model.DkMonitordataHome;
import cn.edu.bnu.land.model.ProjectProgrestatic2;
import cn.edu.bnu.land.model.ProjectYqProgress;
import cn.edu.bnu.land.service.ProdbService;
import cn.edu.bnu.land.model.RssAreaCode;
import cn.edu.bnu.land.model.RssAreaCodeHome;
import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.service.InfoArticleService;
import cn.edu.bnu.land.service.PublishService;
import cn.edu.bnu.land.service.TaskSendingService;
/**;
 * Home object for domain model class Prodb.
 * @see dao.Prodb
 * @author Hibernate Tools
 */

@Controller
public class FKSchemeShow {
	private boolean flagInit=false;
	@Autowired
	private TaskSendingService taskSendingService;
	private ProdbService prodbService;	

	@Autowired
	public FKSchemeShow(ProdbService prodbService) 
	{
		this.prodbService  = prodbService;
	}


	//1.0文章发布的项目筛选
	@RequestMapping (value = "/FKSchemeShow1") 
	@ResponseBody 
	public List<PublicProject> FKSchemeShow1()throws IOException
	{
		List<PublicProject> myList= this.prodbService.fkSchemeShow1();
		return (myList);
	}

	//1.1文章发布
	@RequestMapping(value = "/add_pubArticle_show",method=RequestMethod.POST)
	@ResponseBody
	public void addPubArticle(@RequestBody ProjectArticle projectArticle)
			throws IOException {
		Calendar ca = Calendar.getInstance();
		Date now = ca.getTime();
		System.out.println("add_pubArticle: setArticlePublishtime is  " + now);
		InfoArticle infoArticle=new InfoArticle();
		infoArticle.setArticleAuthor(projectArticle.getArticleAuthor());
		infoArticle.setArticleContent(projectArticle.getArticleContent());
		infoArticle.setArticleName(projectArticle.getArticleName());
		infoArticle.setArticleSource(projectArticle.getArticleSource());
		infoArticle.setChannelId(projectArticle.getChannelId());
		infoArticle.setArticlePublishtime(now);
		infoArticle.setArticleIsdraft("否");
		infoArticle.setArticleIsrecycle("否");
		//System.out.println("update_draftArticle: "+infoArticle.getArticlePublishtime());
		this.prodbService.updateOneArticleShow(infoArticle,projectArticle.getProjectId());
	}
	//1.2存入草稿箱
	@RequestMapping(value = "/add_draftArticle_show",method=RequestMethod.POST)
	@ResponseBody
	public void addDraftArticle(@RequestBody ProjectArticle projectArticle)
			throws IOException {
		InfoArticle infoArticle=new InfoArticle();
		infoArticle.setArticleAuthor(projectArticle.getArticleAuthor());
		infoArticle.setArticleContent(projectArticle.getArticleContent());
		infoArticle.setArticleName(projectArticle.getArticleName());
		infoArticle.setArticleSource(projectArticle.getArticleSource());
		infoArticle.setChannelId(projectArticle.getChannelId());
		infoArticle.setArticleIsdraft("是");
		infoArticle.setArticleIsrecycle("否");	
		System.out.println("update_draftArticle: "+infoArticle.getArticlePublishtime());
		this.prodbService.updateOneArticle(infoArticle,projectArticle.getProjectId());			
	}
	//1.3彻底删除公示
	@RequestMapping(value = "/del_articleByIds_show")
	@ResponseBody
	public void delArticleByIds(@RequestParam("articleIds") String[] articleIds)
			throws IOException {
		this.prodbService.deleteArticleByIds(articleIds);
	}
	//    //1.3移入回收站
	//	@RequestMapping(value = "/update_articleToRecycle_show")
	//	@ResponseBody
	//	public void updateToRecFromPub(@RequestParam("articleIds") String[] articleIds)
	//			throws IOException {
	//		this.prodbService.updateRecTo1(articleIds);			
	//	}
	//	
	//	//2.1草稿箱发布	
	//	@RequestMapping(value = "/update_articleToPubFromDraft_show")
	//	@ResponseBody
	//	public void updateToPubFromDraft(@RequestParam("articleIds") String[] articleIds,@RequestParam("prjIds") String[] prjIds)
	//			throws IOException {
	//		this.prodbService.updateDraftTo0(articleIds,prjIds);	
	//	}
	//	







	//复垦动态监测主界面项目表
	@RequestMapping (value = "/FKSchemeShow3") 
	@ResponseBody 
	public Map<String,Object> handle3(@RequestParam("start") String start,
			@RequestParam ("limit") String  limit,
			@RequestParam ("projectId") String  projectId,
			@RequestParam ("province") String  province,
			@RequestParam ("city") String  city,
			@RequestParam ("county") String  county,
			@RequestParam ("town") String  town,
			@RequestParam ("contructstarttime") String  contructstarttime,
			@RequestParam ("contructendtime") String  contructendtime)throws IOException
			{
		projectId=Encoder.encode(projectId);
		System.out.println(projectId); 
		Map<String,Object> myList= this.prodbService.fkSchemeShow3(start,limit,projectId,province,city,county,town,contructstarttime,contructendtime);
		return (myList);
			}


	//	@RequestMapping (value = "/FKSchemeShow4") 
	//	@ResponseBody 
	//	public Map<String,Object> handle4(@RequestParam("start") String start,
	//			@RequestParam ("limit") String  limit,
	//			@RequestParam ("projectName") String  projectName,
	//			@RequestParam ("province") String  province,
	//			@RequestParam ("city") String  city,
	//			@RequestParam ("county") String  county,
	//			@RequestParam ("monitorstarttime") String  monitorstarttime,
	//			@RequestParam ("monitorendtime") String  monitorendtime)throws IOException
	//			{
	//		System.out.println("1234"); 
	//		projectName=Encoder.encode(projectName);
	//		System.out.println(projectName); 
	//		Map<String,Object> myList= this.prodbService.fkSchemeShow4(start,limit,projectName,province,city,county,monitorstarttime,monitorendtime);
	//		return (myList);
	//			}

	//监测任务计划显示与查询以及最后的结果
	@RequestMapping (value = "/yqprojectmonitorInfo") 
	@ResponseBody 
	public Map<String,Object> handle8(@RequestParam("start") String start,
			@RequestParam ("limit") String  limit,
			@RequestParam("projectId") String projectId,
			@RequestParam("monitorstarttime") String t1,
			@RequestParam("monitorendtime") String t2,
			@RequestParam("accuracy") String accuracy)throws IOException
			{

		//定时器生成xml,定时下发任务
		System.out.println("start timer1................."); 
		if(! flagInit)
		{
			AutoSendingTask	autoSendingTask=new AutoSendingTask();
			autoSendingTask.setTaskSendingService(taskSendingService);
			Timer timer = new Timer();	
			timer.schedule(autoSendingTask, 1000, 1000*60*60);
			flagInit=true;
		}
		System.out.println("ready to start timer:"+projectId); 
		Map<String,Object> myList= this.prodbService.yqprojectmonitorInfo(start,limit,projectId,t1,t2,accuracy);
		return (myList);
			}


	//PDA 复垦监测数据管理显示与搜索
	@RequestMapping (value = "/get_MonitorData") 
	@ResponseBody 
	public Map<String,Object> handle5(@RequestParam("start") String start,
			@RequestParam ("limit") String  limit,
			@RequestParam("projectName") String projectName,
			@RequestParam("monitorstarttime") String monitorstarttime,
			@RequestParam("monitorendtime") String monitorendtime,
			@RequestParam("accuracy") String accuracy)throws IOException
			{
		projectName=Encoder.encode(projectName);
		System.out.println(projectName); 
		System.out.println(monitorstarttime); 
		System.out.println(monitorendtime); 
		Map<String,Object> myList= this.prodbService.syrMonitorData(start,limit,projectName,monitorstarttime,monitorendtime,accuracy);
		return (myList);
			}


	//
	//	@RequestMapping (value = "/syrProgress") 
	//	@ResponseBody 
	//	public Map<String,Object> handle6(@RequestParam ("projectname") String  projectname,
	//			@RequestParam ("monitorstart") String  monitorstart,
	//			@RequestParam ("monitorend")String  monitorend)throws IOException
	//			{
	//		projectname=Encoder.encode(projectname);
	//		System.out.println(projectname);
	//		System.out.println(monitorstart);
	//
	//		Map<String,Object> myList= this.prodbService.syrProgress(projectname,monitorstart,monitorend);
	//		return (myList);
	//			}
	//
	//
	//	//动态监测统计分析计算:统计结果表格和监测报告生成+进度异常写入异常数据表
	//	@RequestMapping (value = "/analysis_static") 
	//	@ResponseBody 
	//	public void handle7(@RequestParam ("projectname") String  projectname,
	//			@RequestParam ("monitorstart") String  monitorstart,
	//			@RequestParam ("monitorend") String  monitorend)throws IOException, ClassNotFoundException, SQLException
	//			{
	//		System.out.println(projectname); 
	//		System.out.println(monitorstart); 
	//		System.out.println(monitorend); 
	//		//统计分析
	//		ProjectProgrestatic2 projectProgrestatic2=new ProjectProgrestatic2();
	//		//this.prodbService.delMonitorStatic(projectProgrestatic2);
	//		this.prodbService.saveMonitorStatic(projectname,monitorstart,monitorend);
	//		this.prodbService.saveAbnormalprogrss();
	//		this.prodbService.updateuseFreemarker2doc();
	//
	//			}
	//
	//
	//	@RequestMapping(value = "/useFreemarker222doc")
	//	@ResponseBody
	//	public void useFreemarker2doc()	throws IOException {
	//		this.prodbService.updateuseFreemarker2doc();	
	//	}
	//

	//2项目类：添加预期任务计划
	@RequestMapping(value = "/add_yqprojectmonitorInfo",method=RequestMethod.POST)
	@ResponseBody
	public void addYqprojectmonitorInfo(@RequestBody ProjectYqProgress projectYqProgress)
			throws IOException {
		System.out.println("syr"+projectYqProgress.getProjectName());
		this.prodbService.addYqprojectmonitorInfo(projectYqProgress);
	}



	//删除预期监测数据
	@RequestMapping(value = "/del_yqmonitorInfodataByIds")
	@ResponseBody
	public void deleteMonitordataByIds(@RequestParam("recordIds") String[] recordIds)
			throws IOException {

		this.prodbService.deleteyqdatabyIds(recordIds);		

	}


	//1、四级联动---省列表
	@RequestMapping (value = "/get_infoAreaProvinceList") 
	@ResponseBody 
	public List<RssAreaCode> get_infoAreaProvinceList() throws IOException
	{
		System.out.println("areaCode:"); 
		List<RssAreaCode> myProvinceList=this.prodbService.listProvince();
		return myProvinceList;
	}

	//2、四级联动---市列表
	@RequestMapping (value = "/get_infoAreaCityList") 
	@ResponseBody 
	public List<RssAreaCode> get_infoAreaCityList(@RequestParam ("codeHead") String  codeHead) throws IOException
	{
		//System.out.println("areaCode:"); 
		List<RssAreaCode> myProvinceList=this.prodbService.listCity(codeHead);
		return myProvinceList;
	}

	//3、四级联动---县列表
	@RequestMapping (value = "/get_infoAreaCountyList") 
	@ResponseBody 
	public List<RssAreaCode> get_infoAreaCountyList(@RequestParam ("codeHead") String  codeHead) throws IOException
	{
		System.out.println("areaCode:"); 
		List<RssAreaCode> myProvinceList=this.prodbService.listCounty(codeHead);
		return myProvinceList;
	}

	//4、四级联动---乡镇街道列表
	@RequestMapping (value = "/get_infoAreaTownList") 
	@ResponseBody 
	public List<RssAreaCode> get_infoAreaTownList(@RequestParam ("codeHead") String  codeHead) throws IOException
	{
		System.out.println("areaCode:"); 
		List<RssAreaCode> myProvinceList=this.prodbService.listTown(codeHead);
		return myProvinceList;
	}



	//5、项目名称得到项目ID列表get_projectIDList
	@RequestMapping (value = "/get_projectIDList") 
	@ResponseBody 
	public  Map<String, Object>  get_projectIDList(@RequestParam ("projectName") String  projectName) throws IOException
	{
		//projectName=Encoder.encode(projectName);
		System.out.println("__________:::::"+projectName); 
		Map<String, Object> myprojectIDList=this.prodbService.listprojectID(projectName);
		return myprojectIDList;
	}


	@RequestMapping(value = "/result_selectDikuai")//,method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String, Object> handle1(@RequestParam("start") String start,
			@RequestParam("limit") String limit,			
			@RequestParam ("projectname") String projectname) throws IOException 
			{ 	
		projectname = Encoder.encode(projectname);
		Map<String, Object> mylist=this.prodbService.resultselect(start, limit, projectname);		 
		return (mylist); 
			}	
	@RequestMapping(value = "/update_yqtable",method=RequestMethod.POST)
	@ResponseBody
	public void addPubArticle(@RequestBody ProjectYqProgress projectYqProgress)
			throws IOException {
		this.prodbService.updateYqtable(projectYqProgress);			

	}

}






