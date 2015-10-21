package cn.edu.bnu.land.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


import cn.edu.bnu.land.model.WarmLogs;
import cn.edu.bnu.land.service.WarmLogsService;
import cn.edu.bnu.land.service.WarmRulesService;
@Controller
public class WarmLogsAction{
	private String id;
	private String warmTarget;
	private String warmContent;
	private String warmTime;
	private String logsFlag;
	private String  solveWay;
	private String solveTime;
	private String warmCategory;
	//对应规则
	private Integer rule_id;
	
	private String jsonString;
	
	private WarmLogsService wlss;
	private WarmRulesService wrs;
	
	public WarmRulesService getWrs() {
		return wrs;
	}
	@Autowired
	public void setWrs(WarmRulesService wrs) {
		this.wrs = wrs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWarmTarget() {
		return warmTarget;
	}

	public void setWarmTarget(String warmTarget) {
		this.warmTarget = warmTarget;
	}

	public String getWarmContent() {
		return warmContent;
	}

	public void setWarmContent(String warmContent) {
		this.warmContent = warmContent;
	}

	public String getWarmTime() {
		return warmTime;
	}

	public void setWarmTime(String warmTime) {
		this.warmTime = warmTime;
	}

	public String getLogsFlag() {
		return logsFlag;
	}

	public void setLogsFlag(String logsFlag) {
		this.logsFlag = logsFlag;
	}

	public String getSolveWay() {
		return solveWay;
	}

	public void setSolveWay(String solveWay) {
		this.solveWay = solveWay;
	}

	public String getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(String solveTime) {
		this.solveTime = solveTime;
	}

	public String getWarmCategory() {
		return warmCategory;
	}

	public void setWarmCategory(String warmCategory) {
		this.warmCategory = warmCategory;
	}

	public Integer getRule_id() {
		return rule_id;
	}

	public void setRule_id(Integer rule_id) {
		this.rule_id = rule_id;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public WarmLogsService getWlss() {
		return wlss;
	}
	@Autowired
	public void setWlss(WarmLogsService wlss) {
		this.wlss = wlss;
	}
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	  

	    return jcf;  
	} 
	
	@RequestMapping(value = "/showWarmLogs.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showAllLogs(){

		List<WarmLogs> datas = wlss.showAll();
		JSONArray array;
		int totalCount = datas.size();
		array = JSONArray.fromObject(datas, configJson());


		jsonString = "{\"totalCount\":" + totalCount + ",\"success\":" + "true" +",\"results\":[";
	

		for (int i = 0; i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
	
		return jsonString;
	}
	
	public Date traslateDate(Date d)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		String tempDate = format.format(d);
		Date newDate = null;
		try {
			newDate = format.parse(tempDate);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
	
	
	@RequestMapping(value = "/modifyWarmLogs.action")
	@ResponseBody
	public Map<String, Object> modifyWarmLogs(
				@RequestParam("id") String id,
				@RequestParam("solveWay") String solveWay
			){
		WarmLogs wls = wlss.getByID(Integer.valueOf(id));
		wls.setSolveWay(solveWay);
		wls.setSolveTime(traslateDate(new Date()));
		wls.setLogsFlag("已处理");
		wlss.modifyWarmLogs(wls);
		
		
		//此处作删除操作 预留 wrs
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
}
