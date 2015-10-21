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

import cn.edu.bnu.land.model.AdministrationUnit;
import cn.edu.bnu.land.service.AdministrationUnitService;
@Controller
public class AdministrationUnitAction{

	AdministrationUnitService as;
	
	int start;
	int limit;
	String uid;
	String id;
	String username;
	String password;
	String phone;
	String permission;	
	String email;
	String level;
	String department;
	List<AdministrationUnit> datas;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	private String jsonString;//这个就是中转站了

	private int totalCount;//这个是extjs用来分页
	
	

	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<AdministrationUnit> getDatas() {
		return datas;
	}

	public void setDatas(List<AdministrationUnit> datas) {
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


	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public void setPermission(String permission) {
		this.permission = permission;
	}	

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setLevel(String level) {
		this.level =level;
	}

	public void setDepartment(String department) {
		this.department =department;
	}

	@Autowired
	public void setAs(AdministrationUnitService as) {
		this.as = as;
	}

	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    jcf.setExcludes(new String[]{"suggest"});
	    return jcf;  
	} 

	@RequestMapping(value = "/deleteAdministrationUnit.action")
	@ResponseBody
	public Map<String, Object> deleteAdministrationUnit(@RequestParam("id") String id)
			throws IOException {
		as.deleteAdministrationUnit(Integer.valueOf(id));
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/showAdministrationUnit.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showAdministrationUnit(
			@RequestParam("start") int start,
			@RequestParam("limit") int limit)
	{	
		List<AdministrationUnit> finalData = new ArrayList<AdministrationUnit>();

		totalCount = finalData.size();
		JSONArray array = JSONArray.fromObject(finalData, configJson());
		
		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":" + "true" + ",\"results\":[";
	
		for (int i = start; i < limit+start &&i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
//		System.out.println("进入show");
//		System.out.println(jsonString);
		return (jsonString);
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
	
	@RequestMapping(value = "/addAdministrationUnit.action")
	@ResponseBody
	public  Map<String, Object> addAdministrationUnit(
			@RequestParam("uid") String uid,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("phone") String phone,
			@RequestParam("permission") String permission,
			@RequestParam("email") String email,
			@RequestParam("level") String level,
			@RequestParam("department") String department
			)
			throws IOException {		
	
		AdministrationUnit AdministrationUnit = new AdministrationUnit();

		AdministrationUnit.setUsername(username);
		AdministrationUnit.setPassword(password);
		AdministrationUnit.setPhone(phone);
		AdministrationUnit.setPermission(permission);		
//		AdministrationUnit.setIdnumber(idnumber);
		AdministrationUnit.setEmail(email);
		AdministrationUnit.setLevel(level);
		AdministrationUnit.setDepartment(department);
		
		System.out.println(AdministrationUnit.getUsername());
		System.out.println(AdministrationUnit.getPassword());
		System.out.println(AdministrationUnit.getPhone());
		System.out.println(AdministrationUnit.getPermission());		
//		System.out.println(AdministrationUnit.getIdnumber());
		System.out.println(AdministrationUnit.getEmail());
		System.out.println(AdministrationUnit.getLevel());
		System.out.println(AdministrationUnit.getDepartment());	
		
		as.addAdministrationUnit(AdministrationUnit);
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/updateAdministrationUnit.action")
	@ResponseBody
	public Map<String, Object> updateAdministrationUnit(
			@RequestParam("uid") String uid,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("phone") String phone,
			@RequestParam("permission") String permission,
			@RequestParam("email") String email,
			@RequestParam("level") String level,
			@RequestParam("department") String department
			)
			throws IOException {
		AdministrationUnit admin = as.getByID(Integer.valueOf(uid));
		admin.setUsername(username);
		admin.setPassword(password);
		admin.setPhone(phone);
		admin.setPermission(permission);
		admin.setEmail(email);
		admin.setLevel(level);
		admin.setDepartment(department);
		as.updateAdministrationUnit(admin);
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
}


