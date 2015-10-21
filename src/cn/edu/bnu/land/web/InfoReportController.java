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
import cn.edu.bnu.land.model.InfoLetter;
import cn.edu.bnu.land.model.InfoReport;
import cn.edu.bnu.land.service.InfoReportService;



@Controller
public class InfoReportController{
	private InfoReportService infoReportService;
	@Autowired
	public InfoReportController(InfoReportService infoReportService) {
		this.infoReportService = infoReportService;
	}
	
	@RequestMapping(value = "/get_infoReportList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getInfoReportList(@RequestParam("start") String start,
			@RequestParam("limit") String limit, @RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate") String searchDate ) throws IOException {

		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> myInfoReportList = this.infoReportService.getInfoReportList(start, limit,searchKeyword,searchDate);
		//System.out.println(myInfoReportList.get("total"));
		return (myInfoReportList);

	}
	

	@RequestMapping(value = "/add_infoReport")//,method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String, Object> addInfoReport(@RequestBody InfoReport infoReport) throws IOException 
	{ 	
		//System.out.println(infoReport.getReportReplycontent());
		Date now = new Date(); 
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
		//System.out.println("currentTime:"+now);
		infoReport.setReportTime(now);
		this.infoReportService.addReport(infoReport);
		Map<String, Object> infoReportResults = new HashMap<String, Object>();
		infoReportResults.put("success", true);
		infoReportResults.put("msg","successfully saved!");

		return infoReportResults;
		 
	} 
	
	
	/*
	 *update_infoReport
	 *  @LF 2013-12-04
	 */
	@RequestMapping(value = "/update_infoReport")//,method=RequestMethod.POST)
	@ResponseBody
	public void updatereport(@RequestBody InfoReport infoReport)
			throws IOException {
		
		Calendar ca = Calendar.getInstance();
     	Date now = ca.getTime();
     	//System.out.println("update_letter: setLetterReplytime is  " + now);
     	infoReport.setReportReplytime(now);
     	infoReport.setReportIsreply("是");
     	
		//System.out.println("updateReport: "+infoReport.getReportReplytime());
		this.infoReportService.updateReport(infoReport);			
		
	}
	
}