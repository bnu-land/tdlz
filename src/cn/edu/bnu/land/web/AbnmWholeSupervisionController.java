package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.AbnmWholesupervision;
import cn.edu.bnu.land.model.InfoLetter;
import cn.edu.bnu.land.model.InfoReport;
import cn.edu.bnu.land.model.RwTable;
import cn.edu.bnu.land.service.AbnmDktranssupervisionService;
import cn.edu.bnu.land.service.AbnmWholeSupervisionService;
import cn.edu.bnu.land.service.InfoReportService;



@Controller
public class AbnmWholeSupervisionController{
	private AbnmWholeSupervisionService abnmWholeSupervisionService;
	private AbnmDktranssupervisionService abnmDktranssupervisionService;
	@Autowired
	public AbnmWholeSupervisionController(AbnmWholeSupervisionService abnmWholeSupervisionService) {
		this.abnmWholeSupervisionService = abnmWholeSupervisionService;
	}
	
	
	/*
	 * @Location: 面板：全过程异常监管任务表abnmWholeSupRWTab；store：abnmWholeSupervisionStore,按钮：“搜索”功能
	 * 
	 * @Description:得到异常监管任务列表，并且可以进行列表搜索也
	 * 
	 * @Params: String start, String limit, String searchKeyword, String searchDate
	 * 
	 * @Return:Map<String, Object> myAbwsList 
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	@RequestMapping(value = "/get_abwsList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getInfoReportList(@RequestParam("start") String start,
			@RequestParam("limit") String limit, @RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate") String searchDate ) throws IOException {

		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> myAbwsList = this.abnmWholeSupervisionService.getAbwsList(start, limit,searchKeyword,searchDate);
		return (myAbwsList);

	}
	

	/*
	 * @Location: 面板：全过程异常监管意见填写abnmWSUpdateTab
	 * 
	 * @Description:更新全过程异常监管意见
	 * 
	 * @Params:AbnmWholesupervision abws
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140225
	 */
	@RequestMapping(value = "/update_abws")//,method=RequestMethod.POST)
	@ResponseBody
	public void updateAbws(@RequestBody AbnmWholesupervision abws)
			throws IOException {
		
		Calendar ca = Calendar.getInstance();
     	Date now = ca.getTime();
     	abws.setAbwsIsSolved("是");
     	abws.setAbwsCollecttime(now);
		this.abnmWholeSupervisionService.updateAbws(abws);			
		
	}
	
	/*
	 * @Location: 面板：新增异常信息至全过程异常监管abnmWSAddTab；
	 * 
	 * @Description:从交易预警面板提交异常信息至全过程异常，同时更新地块交易预警信息中与提交异常信息相关的字段
	 * 
	 * @Params:AbnmWholesupervision abws
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140305
	 */
	@RequestMapping(value = "/add_abws")//,method=RequestMethod.POST)
	@ResponseBody
	public void addAbws(@RequestBody AbnmWholesupervision abws)
			throws IOException {
		
		Calendar ca = Calendar.getInstance();
     	Date now = ca.getTime();
     	//System.out.println("update_letter: setLetterReplytime is  " + now);
     	     	
     	abws.setAbwsDate(now);
     	abws.setAbwsIsTaskAssigned("否");
     	abws.setAbwsIsSolved("否");
		this.abnmWholeSupervisionService.addAbws(abws);	
		
	}

	
	/*
	 * @Location: 面板：全过程异常监管信息表abnmWholeSupervisionTab；
	 * 
	 * @Description:删除信息，点击“批量删除”按钮或者点击每条信息后的删除图标逐条删除
	 * 
	 * @Params:String[] ids
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	@RequestMapping(value = "/delete_abwsByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void deleteAbdkByIds(@RequestParam("ids") String[] ids)
			throws IOException {

		this.abnmWholeSupervisionService.delete_abwsByIds(ids);
		
	}
	
		
	/*
	 * @Location: 面板：全过程异常监管任务表abnmWholeSupRWTab；store：abnmWSRWstore
	 * 
	 * @Description:得到异常监管任务的列表
	 * 
	 * @Params:String start, String limit 
	 * 
	 * @Return:Map<String, Object> myAbwsList 
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	@RequestMapping(value = "/get_abwsRwList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAbwsRwList(@RequestParam("start") String start,
			@RequestParam("limit") String limit ) throws IOException {

		Map<String, Object> myAbwsList = this.abnmWholeSupervisionService.getAbwsRwList(start, limit);
		return (myAbwsList);

	}
	/*
	 * @Location: 现场巡查
	 * 
	 * @Description:
	 * 
	 * @Params:String start, String limit 
	 * 
	 * @Return:Map<String, Object> myAbwsList 
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140416
	 */
	@RequestMapping(value = "/get_xunchaRWList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getXunchaRWList(@RequestParam("start") String start,
			@RequestParam("limit") String limit ) throws IOException {

		Map<String, Object> myAbwsList = this.abnmWholeSupervisionService.getXunchaRWList(start, limit);
		return (myAbwsList);

	}
	
	
	@RequestMapping(value = "/add_xunchaRW")//,method=RequestMethod.POST)
	@ResponseBody
	public void addXunchaRW(@RequestBody RwTable rw)
			throws IOException {
		
		this.abnmWholeSupervisionService.addAbwsRW(rw);	
		
	}
	/*
	 * @Location: 面板：全过程异常监管任务表abnmWholeSupRWTab
	 * 
	 * @Description:包括批量删除按钮和逐条删除的删除图标
	 * 
	 * @Params:String[] ids
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	@RequestMapping(value = "/delete_abwsRWByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void deleteAbwsRWByIds(@RequestParam("ids") String[] ids)
			throws IOException {

		this.abnmWholeSupervisionService.deleteAbwsRWByIds(ids);
		
	}
	
	
	/*
	 * @Location: 面板：新增全过程异常监管任务abnmWSRWAddTab；
	 * 
	 * @Description:从全过程异常监管信息表面板增加新的异常监管任务，同时更新异常信息的rwId
	 * 
	 * @Params:RwTable rw, String abwsId
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	@RequestMapping(value = "/add_abwsRW")//,method=RequestMethod.POST)
	@ResponseBody
	public void addAbwsRW(@RequestBody RwTable rw,@RequestParam("abwsId") String abwsId)
			throws IOException {
		
		this.abnmWholeSupervisionService.addAbwsRW(rw);	
		this.abnmWholeSupervisionService.updateAbwsRwid(abwsId,rw.getRwId());
		
	}
	
	/*
	 * @Location: 面板：全过程异常监管任务详细信息abnmWSRWUpdateTab
	 * 
	 * @Description:更新全过程异常监管任务表的信息
	 * 
	 * @Params: RwTable rw
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	@RequestMapping(value = "/update_abwsRW")//,method=RequestMethod.POST)
	@ResponseBody
	public void updateAbwsRW(@RequestBody RwTable rw)
			throws IOException {
		
		this.abnmWholeSupervisionService.updateAbwsRW(rw);	
		
	}
}