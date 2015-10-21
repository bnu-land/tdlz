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

import cn.edu.bnu.land.model.Rask;
import cn.edu.bnu.land.service.RaskService;
@Controller
public class RaskAction{
	
	RaskService rs;
	private String[] ids;
	private String rwId;
	private String projectId;
	private String projectName;
	private String rwContent;
	private String rwClass;
	private String rwGeneratetime;
	private String rwStarttime;
	private String rwEndtime;
	private String rwSubmittime;
	private String rwIsfinished;
	private String rwResponsiblePerson;

	private String jsonString;
	private int limit;
	private int start;
	
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public RaskService getRs() {
		return rs;
	}
	@Autowired
	public void setRs(RaskService rs) {
		this.rs = rs;
	}


	
	public String getRwId() {
		return rwId;
	}

	public void setRwId(String rwId) {
		this.rwId = rwId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRwContent() {
		return rwContent;
	}

	public void setRwContent(String rwContent) {
		this.rwContent = rwContent;
	}

	public String getRwClass() {
		return rwClass;
	}

	public void setRwClass(String rwClass) {
		this.rwClass = rwClass;
	}

	public String getRwGeneratetime() {
		return rwGeneratetime;
	}

	public void setRwGeneratetime(String rwGeneratetime) {
		this.rwGeneratetime = rwGeneratetime;
	}

	public String getRwStarttime() {
		return rwStarttime;
	}

	public void setRwStarttime(String rwStarttime) {
		this.rwStarttime = rwStarttime;
	}

	public String getRwEndtime() {
		return rwEndtime;
	}

	public void setRwEndtime(String rwEndtime) {
		this.rwEndtime = rwEndtime;
	}

	public String getRwSubmittime() {
		return rwSubmittime;
	}

	public void setRwSubmittime(String rwSubmittime) {
		this.rwSubmittime = rwSubmittime;
	}

	public String getRwIsfinished() {
		return rwIsfinished;
	}

	public void setRwIsfinished(String rwIsfinished) {
		this.rwIsfinished = rwIsfinished;
	}

	public String getRwResponsiblePerson() {
		return rwResponsiblePerson;
	}

	public void setRwResponsiblePerson(String rwResponsiblePerson) {
		this.rwResponsiblePerson = rwResponsiblePerson;
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
	
	
	@RequestMapping(value = "/addRask.action")
	@ResponseBody
	public Map<String, Object> addRask(
				@RequestParam("projectId") String projectId,
				@RequestParam("projectName") String projectName,
				@RequestParam("rwClass") String rwClass,
				@RequestParam("rwContent") String rwContent,
				@RequestParam("rwEndtime") String rwEndtime,
				@RequestParam("rwId") String rwId,
				@RequestParam("rwResponsiblePerson") String rwResponsiblePerson,
				@RequestParam("rwStarttime") String rwStarttime
				
			){
		Rask r = new Rask();
		r.setProjectId(projectId);
		r.setProjectName(projectName);
		r.setRwClass(rwClass);
		r.setRwContent(rwContent);
		r.setRwEndtime(traslateDate(rwEndtime));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		String tempDate = format.format(new Date());
		r.setRwGeneratetime(traslateDate(tempDate));
		r.setRwId(rwId);
		r.setRwIsfinished(false);
		r.setRwResponsiblePerson(rwResponsiblePerson);
		r.setRwStarttime(traslateDate(rwStarttime));
		rs.addRask(r);
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/deleteRask.action")
	@ResponseBody
	public Map<String, Object> deleteRask(
				@RequestParam("ids") String[] ids
			){
		
		for (int i = 0; i < ids.length; i++) {
			rs.removeRask(ids[i]);
		}
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    return jcf;  
	} 
	
	@RequestMapping(value = "/showRask.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showRask(){
		List<Rask> datas = rs.showAll();
		
		//根据flag来判断是加载一条还是加载全部
		
		JSONArray array;
		int totalCount = datas.size();
		array = JSONArray.fromObject(datas, configJson());


		jsonString = "{\"totalCount\":" + totalCount + ",\"success\":" + "true" +
		",\"results\":[";
	

		for (int i = 0; i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			jsonString += oj.toString() + ",";

        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
		
	
		return jsonString;
	}
}
