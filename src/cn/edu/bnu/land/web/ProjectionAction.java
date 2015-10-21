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

import cn.edu.bnu.land.model.Projection;
import cn.edu.bnu.land.service.ProjectionService;

@Controller
public class ProjectionAction{
	private String projectNumber;
	private String projectAddress;
	private String createTime;
	private String projectStatus;
	private String endTime;
	private String projectDescription;
	
	private ProjectionService ps;
	private String jsonString;
	
	
	public ProjectionService getPs() {
		return ps;
	}
	@Autowired
	public void setPs(ProjectionService ps) {
		this.ps = ps;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getProjectAddress() {
		return projectAddress;
	}
	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
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
	
	@RequestMapping(value = "/addProject.action")
	@ResponseBody
	public Map<String, Object> addProject(
			@RequestParam("createTime") String createTime,
			@RequestParam("projectAddress") String projectAddress,
			@RequestParam("projectDescription") String projectDescription,
			@RequestParam("projectNumber") String projectNumber,
			@RequestParam("projectStatus") String projectStatus
			
			){
		Projection p = new Projection();

		p.setCreateTime(traslateDate(createTime));
		p.setEndTime(null);
		p.setProjectAddress(projectAddress);
		p.setProjectDescription(projectDescription);
		p.setProjectNumber(projectNumber);
		p.setProjectStatus(projectStatus);
		ps.addProjection(p);
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/modifyProject.action")
	@ResponseBody
	public Map<String, Object> modifyProject(
			@RequestParam("projectNumber") String projectNumber,
			@RequestParam("projectStatus") String projectStatus
			
			){
		List<Projection> pp = ps.showByNumber(projectNumber);
		if (pp.size() != 0) {
			Projection p = pp.get(0);
			
			switch (Integer.valueOf(projectStatus)) {
			case 1:
				p.setProjectStatus("规划阶段");
				break;
			case 2:
				p.setProjectStatus("入库备案");
				break;
			case 3:
				p.setProjectStatus("拆迁阶段");
				break;
			case 4:
				p.setProjectStatus("项目完成");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
				String tempDate = format.format(new Date());
				p.setEndTime(traslateDate(tempDate));
				break;
			default:
				break;
			}
			
			ps.modifyProjection(p);
		}
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	
	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    return jcf;  
	} 
	
	@RequestMapping(value = "/showProject.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showProject(){
		List<Projection> datas = ps.showAll();
		int totalCount = datas.size();
		JSONArray array = JSONArray.fromObject(datas, configJson());
		
		jsonString = "{\"totalCount\":" + totalCount + ",\"success\":" + "true"+
		",\"results\":[";

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
	
	
	@RequestMapping(value = "/showCurrentProject.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showCurrentProject(
			@RequestParam("projectNumber") String projectNumber
			){
		List<Projection> datas = ps.showByNumber(projectNumber);
		int totalCount = datas.size();
		JSONArray array = JSONArray.fromObject(datas, configJson());
		
		jsonString = "{\"totalCount\":" + totalCount + ",\"results\":[";

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
}
