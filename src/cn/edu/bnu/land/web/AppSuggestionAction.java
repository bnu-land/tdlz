package cn.edu.bnu.land.web;

import java.io.IOException;
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

import cn.edu.bnu.land.model.AppSuggestion;
import cn.edu.bnu.land.model.Application;
import cn.edu.bnu.land.service.AppSuggestionService;
import cn.edu.bnu.land.service.ApplicationService;
@Controller
public class AppSuggestionAction {
	private String content;
	private Date date;
	private String status;
	private AppSuggestionService apps;
	private ApplicationService as;
	
	//申请表的ID
	private String id;
	
	//显示
	private String jsonString;//这个就是中转站了
	private int totalCount;//这个是extjs用来分页
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ApplicationService getAs() {
		return as;
	}
	@Autowired
	public void setAs(ApplicationService as) {
		this.as = as;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public AppSuggestionService getApps() {
		return apps;
	}
	@Autowired
	public void setApps(AppSuggestionService apps) {
		this.apps = apps;
	}
	
	@RequestMapping(value = "/showAppSuggest.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showAppSuggest(
			@RequestParam("id") String id
			)
			throws IOException {
		System.out.println(id);
		List<AppSuggestion> datas = apps.showAppSuggestion(Integer.valueOf(id));
		
		
		totalCount = datas.size();
		JSONArray array = JSONArray.fromObject(datas, configJson());


		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":" + "true" +",\"results\":[";
	

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
	//屏蔽申请表
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  

	    jcf.setExcludes(new String[]{"application"});
	    return jcf;  
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
	
	@RequestMapping(value = "/addAppSuggest.action")
	@ResponseBody
	public Map<String, Object> addAppSuggest(
			@RequestParam("id") String id,
			@RequestParam("content") String content
			)
			throws IOException {
		
		System.out.println("进入action");
		
		AppSuggestion appSuggest = new AppSuggestion();
		appSuggest.setContent(content);
		appSuggest.setDate(traslateDate(new Date()));
		appSuggest.setStatus("新回复");
		
		Application app = as.findApplication(Integer.valueOf(id));
		
		appSuggest.setApplication(app);
		
		
		apps.addAppSuggestion(appSuggest);
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
}
