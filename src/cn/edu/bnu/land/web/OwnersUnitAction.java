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

import cn.edu.bnu.land.model.OwnersUnit;
import cn.edu.bnu.land.service.OwnersUnitService;

@Controller
public class OwnersUnitAction {

	OwnersUnitService os;
	
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
	List<OwnersUnit> datas;
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email=email;
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

	public List<OwnersUnit> getDatas() {
		return datas;
	}

	public void setDatas(List<OwnersUnit> datas) {
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
	
	
	public void setLevel(String level) {
		this.level =level;
	}

	public void setDepartment(String department) {
		this.department =department;
	}

	@Autowired
	public void setOs(OwnersUnitService os) {
		this.os = os;
	}

	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    jcf.setExcludes(new String[]{"suggest"});
	    return jcf;  
	} 

	
	public String deleteOwnersUnit()
	{
		os.deleteOwnersUnit(Integer.valueOf(id));
		
		return "success";
	}
	
	
	@RequestMapping(value = "/showOwnersUnit.action")
	@ResponseBody
	public Map<String, Object> showOwnersUnit(
			@RequestParam("start") int start,
			@RequestParam("limit") int limit
			
			)
			throws IOException {	
		List<OwnersUnit> finalData = new ArrayList<OwnersUnit>();

		totalCount = finalData.size();
		JSONArray array = JSONArray.fromObject(finalData, configJson());
		
		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"results\":[";
	
		for (int i = start; i < limit+start &&i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
		
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
	
	@RequestMapping(value = "/addOwnersUnit.action")
	@ResponseBody
	public Map<String, Object> addOwnersUnit(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("phone") String phone,
			@RequestParam("permission") String permission,
			@RequestParam("email") String email,
			@RequestParam("level") String level,
			@RequestParam("department") String department
			
			)
			throws IOException {		
		OwnersUnit OwnersUnit = new OwnersUnit();
		OwnersUnit.setUsername(username);
		OwnersUnit.setPassword(password);
		OwnersUnit.setPhone(phone);
		OwnersUnit.setPermission(permission);			
//		OwnersUnit.setIdnumber(idnumber);
		OwnersUnit.setEmail(email);
		OwnersUnit.setLevel(level);
		OwnersUnit.setDepartment(department);		
		System.out.println(OwnersUnit.getUsername());
		System.out.println(OwnersUnit.getPassword());
		System.out.println(OwnersUnit.getPhone());
		System.out.println(OwnersUnit.getPermission());		
//		System.out.println(OwnersUnit.getIdnumber());
		System.out.println(OwnersUnit.getEmail());
		System.out.println(OwnersUnit.getLevel());
		System.out.println(OwnersUnit.getDepartment());	
		os.addOwnersUnit(OwnersUnit);		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/updateOwnersUnit.action")
	@ResponseBody
	public Map<String, Object> updateOwnersUnit(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("phone") String phone,
			@RequestParam("permission") String permission,
			@RequestParam("email") String email,
			@RequestParam("level") String level,
			@RequestParam("department") String department,
			@RequestParam("uid") String uid
			)
			throws IOException {		

		OwnersUnit owner = os.getByID(Integer.valueOf(uid));
        owner.setUsername(username);
        owner.setPassword(password);
        owner.setPhone(phone);
        owner.setPermission(permission);
        owner.setEmail(email);
        owner.setLevel(level);
        owner.setDepartment(department);
        os.updateOwnersUnit(owner);
    	Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	
}


