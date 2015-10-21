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


import cn.edu.bnu.land.model.ComForDemolition;
import cn.edu.bnu.land.model.FarmersInformation;
import cn.edu.bnu.land.service.ComForDemoService;
import cn.edu.bnu.land.service.FarmersInformationService;

@Controller
public class FarmersInformationAction {

	FarmersInformationService fs;
	
	//关联补偿拆迁表
	ComForDemoService cfds;
	
	
	int start;
	int limit;
	String uid;
	String id;	
	String username;	
	String password;
	String phone;
	String farmername;
	String sex;
	String idnumber;
	String address;
	String permission;
	String projectID;
	
	List<FarmersInformation> datas;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public ComForDemoService getCfds() {
		return cfds;
	}
	@Autowired
	public void setCfds(ComForDemoService cfds) {
		this.cfds = cfds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber=idnumber;
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

	public List<FarmersInformation> getDatas() {
		return datas;
	}

	public void setDatas(List<FarmersInformation> datas) {
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

	public void setFarmername(String farmername) {
		this.farmername = farmername;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}   	

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Autowired
	public void setFs(FarmersInformationService fs) {
		this.fs = fs;
	}

	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    jcf.setExcludes(new String[]{"suggest"});
	    return jcf;  
	} 

	
	public String deleteFarmersInformation()
	{
		fs.deleteFarmersInformation(Integer.valueOf(id));
		
		return "success";
	}
	
	
	@RequestMapping(value = "/showFarmersInformation.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showFarmersInformation(
			@RequestParam("start") int start,
			@RequestParam("limit") int limit
			)
			throws IOException {	
		datas = fs.showFarmersInformation();
		List<FarmersInformation> finalData = new ArrayList<FarmersInformation>();
		for (FarmersInformation us : datas) {
			finalData.add(us);
			}	
		totalCount = finalData.size();
		JSONArray array = JSONArray.fromObject(finalData, configJson());
		
		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":" +",\"results\":[";
	
		for (int i = start; i < limit+start &&i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
//		System.out.println("进入show");
//		System.out.println(jsonString);
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
	
	@RequestMapping(value = "/addFarmersInformation.action")
	@ResponseBody
	public Map<String, Object> addFarmersInformation(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("phone") String phone,
			@RequestParam("farmername") String farmername,
			@RequestParam("sex") String sex,
			@RequestParam("idnumber") String idnumber,
			@RequestParam("address") String address,
			@RequestParam("permission") String permission,
			@RequestParam("projectID") String projectID
			){			
		FarmersInformation FarmersInformation = new FarmersInformation();
		FarmersInformation.setUsername(username);		
		FarmersInformation.setPassword(password);
		FarmersInformation.setPhone(phone);
		FarmersInformation.setFarmername(farmername);
		FarmersInformation.setSex(sex);	
		FarmersInformation.setIdnumber(idnumber);
		FarmersInformation.setAddress(address);
		FarmersInformation.setPermission(permission);	
		FarmersInformation.setProjectID(projectID);
		
		fs.addFarmersInformation(FarmersInformation);
	
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/updateFarmersInformation.action")
	@ResponseBody
	public Map<String, Object> updateFarmersInformation(
			@RequestParam("username") String username,
			@RequestParam("uid") String uid,
			@RequestParam("password") String password,
			@RequestParam("phone") String phone,
			@RequestParam("farmername") String farmername,
			@RequestParam("sex") String sex,
			@RequestParam("idnumber") String idnumber,
			@RequestParam("address") String address,
			@RequestParam("permission") String permission,
			@RequestParam("projectID") String projectID
			)
			throws IOException {
		FarmersInformation farmer = fs.getByID(Integer.valueOf(uid));
		farmer.setUsername(username);
		farmer.setPassword(password);
		farmer.setPhone(phone);
		farmer.setFarmername(farmername);
		farmer.setSex(sex);	
		farmer.setIdnumber(idnumber);
		farmer.setAddress(address);
		farmer.setPermission(permission);		
		farmer.setProjectID(projectID);
		fs.updateFarmersInformation(farmer);
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	
	
}


