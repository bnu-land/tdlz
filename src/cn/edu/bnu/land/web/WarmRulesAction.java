package cn.edu.bnu.land.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import cn.edu.bnu.land.model.WarmRules;
import cn.edu.bnu.land.service.WarmRulesService;
@Controller
public class WarmRulesAction{
	//接收前台传参
	private String id;
	private String createTime;
	private String warmTarget;
	private String warmContent;
	private String endTime;
	private String ruleDescription;
	private String cycleTime;
	private String warmCategory;
	private String jsonString;
	private String projectID;
	private String warmTargetID;
	private String warmContentID;
	private String warmCategoryID;

	private WarmRulesService wrs;
	
	
	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getWarmTargetID() {
		return warmTargetID;
	}

	public void setWarmTargetID(String warmTargetID) {
		this.warmTargetID = warmTargetID;
	}

	public String getWarmContentID() {
		return warmContentID;
	}

	public void setWarmContentID(String warmContentID) {
		this.warmContentID = warmContentID;
	}

	public String getWarmCategoryID() {
		return warmCategoryID;
	}

	public void setWarmCategoryID(String warmCategoryID) {
		this.warmCategoryID = warmCategoryID;
	}

	public String getWarmCategory() {
		return warmCategory;
	}

	public void setWarmCategory(String warmCategory) {
		this.warmCategory = warmCategory;
	}

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}

	public String getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(String cycleTime) {
		this.cycleTime = cycleTime;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    return jcf;  
	} 
	
	
	//需修改
	@RequestMapping(value = "/showWarmRules.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showWarmRules(){
		
		List<WarmRules> datas = wrs.showRules();
		int totalCount = datas.size();
		JSONArray array = JSONArray.fromObject(datas, configJson());
		
		jsonString = "{\"totalCount\":" + totalCount + ",\"success\":" + "true" +",\"results\":[";
	
		for (int i = 0; i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
		System.out.println(jsonString);
		return jsonString;
	}
	
	public Date traslateDate(String d)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		
		Date newDate = null;
		try {
			newDate = format.parse(d);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
	
	@RequestMapping(value = "/addWarmRules.action")
	@ResponseBody
	public Map<String, Object> addWarmRules(
				@RequestParam("createTime") String createTime,
				@RequestParam(value = "cycleTime", required = false) String cycleTime,
				@RequestParam("endTime") String endTime,
				@RequestParam("ruleDescription") String ruleDescription,
				@RequestParam("warmContent") String warmContent,
				@RequestParam("warmTarget") String warmTarget,
				@RequestParam(value = "warmCategory", required = false) String warmCategory,
				@RequestParam("warmContentID") String warmContentID,
				@RequestParam("warmCategoryID") String warmCategoryID,
				@RequestParam("projectID") String projectID,
				@RequestParam("warmTargetID") String warmTargetID				
			){
		
		if (cycleTime != null) {
			this.cycleTime = cycleTime;
		}else{
			cycleTime = this.cycleTime;
		}
		if (warmCategory != null) {
			this.warmCategory = warmCategory;
		}else{
			warmCategory = this.warmCategory;
		}
		WarmRules wr = new WarmRules();

		wr.setCreateTime(traslateDate(createTime));
		wr.setCycleTime(Integer.valueOf(cycleTime));
		wr.setEndTime(traslateDate(endTime));
		wr.setRuleDescription(ruleDescription);
		wr.setWarmContent(warmContent);
		wr.setWarmTarget(warmTarget);
		wr.setWarmCategory(warmCategory);
		wr.setWarmContentID(Integer.valueOf(warmContentID));
		wr.setWarmCategoryID(Integer.valueOf(warmCategoryID));
		wr.setProjectID(projectID);
		wr.setWarmTargetID(Integer.valueOf(warmTargetID));
		wrs.addWarmRules(wr);
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/modifyWarmRules.action")
	@ResponseBody
	public Map<String, Object> modifyWarmRules(
				@RequestParam("createTime") String createTime,
				@RequestParam(value = "cycleTime", required = false) String cycleTime,
				@RequestParam("endTime") String endTime,
				@RequestParam("ruleDescription") String ruleDescription,
				@RequestParam("warmContent") String warmContent,
				@RequestParam("warmTarget") String warmTarget,
				@RequestParam(value = "warmCategory", required = false) String warmCategory,
				@RequestParam("warmContentID") String warmContentID,
				@RequestParam("warmCategoryID") String warmCategoryID,
				@RequestParam("projectID") String projectID,
				@RequestParam("warmTargetID") String warmTargetID,	
				@RequestParam("id") String id
			){
		
		if (cycleTime != null) {
			this.cycleTime = cycleTime;
		}else{
			cycleTime = this.cycleTime;
		}
		if (warmCategory != null) {
			this.warmCategory = warmCategory;
		}else{
			warmCategory = this.warmCategory;
		}
		WarmRules wr = wrs.getRule(Integer.valueOf(id));
		wr.setCreateTime(traslateDate(createTime));
		wr.setCycleTime(Integer.valueOf(cycleTime));
		wr.setEndTime(traslateDate(endTime));
		wr.setRuleDescription(ruleDescription);
		wr.setWarmContent(warmContent);
		wr.setWarmTarget(warmTarget);
		wr.setWarmCategory(warmCategory);
		wr.setWarmContentID(Integer.valueOf(warmContentID));
		wr.setWarmCategoryID(Integer.valueOf(warmCategoryID));
		wr.setProjectID(projectID);
		wr.setWarmTargetID(Integer.valueOf(warmTargetID));
		wrs.modifyRules(wr);
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/removeWarmRules.action")
	@ResponseBody
	public Map<String, Object> removeWarmRules(
			@RequestParam("id") String id
			){
		wrs.removeRules(Integer.valueOf(id));
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
}
