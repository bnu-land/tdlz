package cn.edu.bnu.land.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import cn.edu.bnu.land.model.Noticement;
import cn.edu.bnu.land.model.Suggestion;
import cn.edu.bnu.land.service.NoticementService;
import cn.edu.bnu.land.service.SuggestionService;

@Controller
public class SuggestAction{
	SuggestionService ss;
	NoticementService ns;
	
	int start;
	int limit;
	String id;
	String content;
	String publisher;
	String category;
	String date;
	String grade;
	String count;
	String noticeID;
	String projectID;
	List<Suggestion> datas;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	private String jsonString;//这个就是中转站了
	
	private int totalCount;//这个是extjs用来分页

	
	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public NoticementService getNs() {
		return ns;
	}
	@Autowired
	public void setNs(NoticementService ns) {
		this.ns = ns;
	}

	public String getNoticeID() {
		return noticeID;
	}

	public void setNoticeID(String noticeID) {
		this.noticeID = noticeID;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public SuggestionService getSs() {
		return ss;
	}
	@Autowired
	public void setSs(SuggestionService ss) {
		this.ss = ss;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Suggestion> getDatas() {
		return datas;
	}

	public void setDatas(List<Suggestion> datas) {
		this.datas = datas;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
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
	
	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    jcf.setExcludes(new String[]{"notice"});
	    return jcf;  
	} 
	
	@RequestMapping(value = "/deleteSuggest.action")
	@ResponseBody
	public Map<String, Object> deleteSuggestion(@RequestParam("id") String id)
			throws IOException {
		ss.deleteSuggestion(Integer.valueOf(id));
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
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
	
	@RequestMapping(value = "/showStatistic.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showStatistic()
			throws IOException {
		String gh = "{\"category\":" + "\"规划意见\"" + "," + ss.Statistic("规划意见") + "}";
		String az = "{\"category\":" + "\"安置意见\"" + "," + ss.Statistic("安置意见") + "}";
		String bc = "{\"category\":" + "\"补偿意见\"" + "," + ss.Statistic("补偿意见") + "}";
		String fk = "{\"category\":" + "\"拆迁意见\"" + "," + ss.Statistic("拆迁意见") + "}";
		
		jsonString = "{\"totalCount\":" + "4" + ",\"results\":[" + gh + ","
				+ az + "," + bc + "," + fk +"]}";
		
		
		System.out.println(jsonString);
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		results.put("results", jsonString);
		
		return (jsonString);
	}
	
	@RequestMapping(value = "/showSuggest.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showSuggestion(
			@RequestParam(value = "date", required = false) String date, 
			@RequestParam(value = "noticeID", required = false) String noticeID, 
			@RequestParam("limit") int limit,
			@RequestParam("start") int start
			)
			throws IOException {
		
		if (noticeID != null) {
			this.noticeID = noticeID;
		}else{
			noticeID = this.noticeID;
		}
		if (date != null) {
			this.date = date;
		}else{
			date = this.date;
		}
//		System.out.println("limit:" + limit);
		
		if (date.isEmpty()) {
//			System.out.println("进入if");
			datas = ss.showSuggestion();
		}else {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
			Date newDate = null;
					 
			try {
				newDate = format.parse(date);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				    
			   
			datas = ss.filterSuggestion(newDate);
		}
		
		
		List<Suggestion> finalData = new ArrayList<Suggestion>();
		JSONArray array;
		if (!noticeID.equals("1")) {
			for (Suggestion sug : datas) {
				if (noticeID.equals(
						sug.getNotice().getId().toString().trim())) {
					finalData.add(sug);
				}
			}
			totalCount = finalData.size();
			array = JSONArray.fromObject(finalData, configJson());
		}else {
			totalCount = datas.size();
			array = JSONArray.fromObject(datas, configJson());
			
		}
		
		
		System.out.println(array);
		System.out.println("意见日期是:" + date);	
		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":" + "true"+
		",\"results\":[";
	
		if (limit == -1) {
			limit = totalCount;
		}
		for (int i = start; i < limit+start &&i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
		
		
		System.out.println(jsonString);
		
//		System.out.println("进入show");
//		System.out.println(jsonString);

		return jsonString;
	}
	
	@RequestMapping(value = "/addSuggest.action")
	@ResponseBody
	public Map<String, Object> addSuggestion(@RequestParam("count") String count, 
			@RequestParam("noticeID") String noticeID, 
			@RequestParam(value = "category", required = false) String category, 
			@RequestParam("content") String content, 
			@RequestParam("grade") String grade, 
			@RequestParam("publisher") String publisher
	
			)
			throws IOException {
		
		if (ss.getNoticeByID(1) == null) {
			Noticement notice = new Noticement();				
			notice.setCategory("");
			notice.setDate(traslateDate(new Date()));
			notice.setContent("");
			notice.setPublisher("");
			notice.setTitle("");
			notice.setProjectID("");
			notice.setEnclosureFlag("无附件");
			notice.setEnclosurePath("");
			ns.addNoticement(notice);
		}
		

			if (noticeID.equals("1")) {
				
				String[] newCategory = category.split(",");
				String[] newContent = content.split("#");
				String[] newGrade = grade.split(",");
				Noticement notice = ss.getNoticeByID(1);
				
				for (int i = 0; i < Integer.valueOf(count); i++) {
					Suggestion suggest = new Suggestion();
					
//					System.out.println(newCategory[i]);
					
					suggest.setCategory(newCategory[i]);
					suggest.setDate(traslateDate(new Date()));
					suggest.setContent(newContent[i]);
					suggest.setPublisher(publisher);
					suggest.setProjectID(projectID);
					suggest.setGrade(Float.valueOf(newGrade[i]));
					suggest.setNotice(notice);
					
					ss.addSuggestion(suggest);
				}
				
			}else
			{
				
				
				String[] newContent = content.split("#");
				String[] newGrade = grade.split(",");
				Noticement notice = ss.getNoticeByID(Integer.valueOf(noticeID));
				for (int i = 0; i < Integer.valueOf(count); i++) {
					Suggestion suggest = new Suggestion();

					suggest.setCategory(notice.getCategory().replaceAll("方案", "意见"));
					suggest.setDate(traslateDate(new Date()));
					suggest.setContent(newContent[i]);
					suggest.setPublisher(publisher);
					suggest.setProjectID(projectID);
					suggest.setGrade(Float.valueOf(newGrade[i]));
					suggest.setNotice(notice);
					
					ss.addSuggestion(suggest);
				}
			}
			
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("success", true);
			results.put("msg", "1"
					+ ",successfully saved");
			
			return (results);
	}
}
