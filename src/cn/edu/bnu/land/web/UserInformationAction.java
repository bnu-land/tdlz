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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.model.Noticement;
import cn.edu.bnu.land.model.Suggestion;
import cn.edu.bnu.land.model.UserInformation;
import cn.edu.bnu.land.service.UserInformationService;

import cn.edu.bnu.land.model.FarmersInformation;
import cn.edu.bnu.land.service.FarmersInformationService;
import cn.edu.bnu.land.model.OwnersUnit;
import cn.edu.bnu.land.service.OwnersUnitService;
import cn.edu.bnu.land.model.AdministrationUnit;
import cn.edu.bnu.land.service.AdministrationUnitService;

@Controller
public class UserInformationAction {

	UserInformationService us;
	AdministrationUnitService as;
	FarmersInformationService fs;
	OwnersUnitService os;
	
	int start;
	int limit;
	String id;
	String username;
	String password;
	String phone;
	String permission;
	String sex;
	Long idnumber;
	String address;
	String level;
	String department;
	String email;
	List<UserInformation> datas;
	
	List<FarmersInformation> datasf;
	List<OwnersUnit> dataso;
	List<AdministrationUnit> datasa;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	private String jsonString;//这个就是中转站了
	private String jsonStringf;
	private String jsonStringo;
	private String jsonStringa;

	private int totalCount;//这个是extjs用来分页
	
	private int totalCountf;
	private int totalCounto;
	private int totalCounta;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Long getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(Long idnumber) {
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

	public List<UserInformation> getDatas() {
		return datas;
	}

	public void setDatas(List<UserInformation> datas) {
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


	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setLevel(String level) {
		this.level =level;
	}

	public void setDepartment(String department) {
		this.department =department;
	}

	@Autowired
	public void setUs(UserInformationService us) {
		this.us = us;
	}
	@Autowired
	public void setFs(FarmersInformationService fs) {
		this.fs = fs;
	}
	@Autowired
	public void setOs(OwnersUnitService os) {
		this.os = os;
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

	
	@RequestMapping(value = "/deleteUserInformation.action")
	@ResponseBody
	public Map<String, Object> deleteUserInformation(@RequestParam("id") String id)
			throws IOException {
		us.deleteUserInformation(Integer.valueOf(id));
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	
	@RequestMapping(value = "/showUserInformation.action")
	@ResponseBody
	public Map<String, Object> showUserInformation(
			@RequestParam("start") int start,
			@RequestParam("limit") int limit
			)
			throws IOException {	
		datas = us.showUserInformation();
		List<UserInformation> finalData = new ArrayList<UserInformation>();
		for (UserInformation us : datas) {
		finalData.add(us);
		}		
		datasf = fs.showFarmersInformation();
		List<FarmersInformation> finalDataf = new ArrayList<FarmersInformation>();
		for (FarmersInformation fs : datasf) {
		finalDataf.add(fs);
		}		
		dataso = os.showOwnersUnit();
		List<OwnersUnit> finalDatao = new ArrayList<OwnersUnit>();
		for (OwnersUnit os : dataso) {
		finalDatao.add(os);
		}	
		datasa = as.showAdministrationUnit();
		List<AdministrationUnit> finalDataa = new ArrayList<AdministrationUnit>();
		for (AdministrationUnit as : datasa) {
		finalDataa.add(as);
		}	
		totalCountf = finalDataf.size();
		totalCounta = finalDataa.size();
		totalCounto = finalDatao.size();
		totalCount = finalData.size();
		
		JSONArray array = JSONArray.fromObject(finalData, configJson());
		System.out.println(array);
		
		JSONArray arrayf = JSONArray.fromObject(finalDataf, configJson());
		System.out.println(arrayf);
		
		JSONArray arrayo = JSONArray.fromObject(finalDatao, configJson());
		System.out.println(arrayo);
		
		JSONArray arraya = JSONArray.fromObject(finalDataa, configJson());
		System.out.println(arraya);
		
		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"results\":[";
	
		for (int i = start; i < limit+start &&i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			jsonString += oj.toString() + ",";
			
        }
		for (int i = start; i < limit+start &&i <totalCountf; i++)
        {
			JSONObject oj = arrayf.getJSONObject(i);  
			jsonString += oj.toString() + ",";
			
        }
		for (int i = start; i < limit+start &&i <totalCounto; i++)
        {
			JSONObject oj = arrayo.getJSONObject(i);  
			jsonString += oj.toString() + ",";
			
        }
		for (int i = start; i < limit+start &&i <totalCounta; i++)
        {
			JSONObject oj = arraya.getJSONObject(i);  
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
	
	@RequestMapping(value = "/showUserInformationByID.action")
	@ResponseBody
	public Map<String, Object> showUserInformationByID(
			)
			{

		UserInformation user = us.getByID(1);
		JSONArray array = JSONArray.fromObject(user, configJson());
		jsonString = "{\"totalCount\": 1" + ",\"results\":[";		
		JSONObject oj = array.getJSONObject(0);  			
		jsonString += oj.toString() + ",";		
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");			
		System.out.println(jsonString);		
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
	
	@RequestMapping(value = "/addUserInformation.action")
	@ResponseBody
	public Map<String, Object> addUserInformation(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("phone") String phone,
			@RequestParam("permission") String permission
			
			)
			{
		
		if (us.getByID(1) == null) {
			UserInformation UserInformation = new UserInformation();				
			UserInformation.setUsername("");
			UserInformation.setPassword("");
			UserInformation.setPhone("");
			UserInformation.setPermission("");
//			UserInformation.setSex("");	
//			UserInformation.setIdnumber(1L);
//			UserInformation.setAddress("");
//			UserInformation.setLevel("");
//			UserInformation.setDepartment("");
			
			us.addUserInformation(UserInformation);
		}
	
		UserInformation UserInformation = new UserInformation();

		UserInformation.setUsername(username);
		UserInformation.setPassword(password);
		UserInformation.setPhone(phone);
		UserInformation.setPermission(permission);
//		UserInformation.setSex(sex);	
//		UserInformation.setIdnumber(idnumber);
//		UserInformation.setAddress(address);
//		UserInformation.setLevel(level);
//		UserInformation.setDepartment(department);
		
		
		us.addUserInformation(UserInformation);
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	public String modifyUserInformation()
	{
		return "success";
	}
	
	
}

