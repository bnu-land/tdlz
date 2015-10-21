package cn.edu.bnu.land.web;

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

import cn.edu.bnu.land.model.UnfindCard;
import cn.edu.bnu.land.service.UnfindCardService;

@Controller
public class UnfindCardAction{
	private String id;
	private String houseID;
	private String projectID;
	private String DYNHXM;
	private String DYNHSFZ;
	private String XZDL;
	private String housePhotoN;
	private String housePhotoY;
	private String warrantPhoto;
	private String comfire;
	private String jsonString;
	
	private UnfindCardService ucs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHouseID() {
		return houseID;
	}

	public void setHouseID(String houseID) {
		this.houseID = houseID;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getDYNHXM() {
		return DYNHXM;
	}

	public void setDYNHXM(String dYNHXM) {
		DYNHXM = dYNHXM;
	}

	public String getDYNHSFZ() {
		return DYNHSFZ;
	}

	public void setDYNHSFZ(String dYNHSFZ) {
		DYNHSFZ = dYNHSFZ;
	}

	public String getXZDL() {
		return XZDL;
	}

	public void setXZDL(String xZDL) {
		XZDL = xZDL;
	}

	public String getHousePhotoN() {
		return housePhotoN;
	}

	public void setHousePhotoN(String housePhotoN) {
		this.housePhotoN = housePhotoN;
	}

	public String getHousePhotoY() {
		return housePhotoY;
	}

	public void setHousePhotoY(String housePhotoY) {
		this.housePhotoY = housePhotoY;
	}

	public String getWarrantPhoto() {
		return warrantPhoto;
	}

	public void setWarrantPhoto(String warrantPhoto) {
		this.warrantPhoto = warrantPhoto;
	}

	public String getComfire() {
		return comfire;
	}

	public void setComfire(String comfire) {
		this.comfire = comfire;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public UnfindCardService getUcs() {
		return ucs;
	}

	@Autowired
	public void setUcs(UnfindCardService ucs) {
		this.ucs = ucs;
	}
	
	
	@RequestMapping(value = "/addUnfindCard.action")
	@ResponseBody
	public Map<String, Object> addUnfindCard(
				@RequestParam("houseID") String houseID,
				@RequestParam("DYNHXM") String DYNHXM,
				@RequestParam("DYNHSFZ") String DYNHSFZ,
				@RequestParam("XZDL") String XZDL,
				@RequestParam("projectID") String projectID
			){
		
		String[] houseIDM = houseID.split(",");
		//String[] projectIDM = projectID.split(","); 
		String[] DYNHXMM = DYNHXM.split(",");
		String[] DYNHSFZM = DYNHSFZ.split(",");
		String[] XZDLM = XZDL.split(",");
		//String[] housePhotoN;
		//String[] housePhotoY;
		//String[] warrantPhoto;
		//String[] comfire;
		
		for (int i = 0; i < houseIDM.length; i++) {
			UnfindCard c =new UnfindCard();
			c.setComfire("未确认");
			c.setDYNHSFZ(DYNHSFZM[i]);
			c.setDYNHXM(DYNHXMM[i]);
			c.setHouseID(houseIDM[i]);
			c.setHousePhotoN("");
			c.setHousePhotoY("");
			c.setProjectID(projectID);
			c.setWarrantPhoto("");
			c.setXZDL(XZDLM[i]);
			ucs.addUnfindCard(c);
		}
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/modifyUnfindCard.action")
	@ResponseBody
	public Map<String, Object> modifyUnfindCard(
			@RequestParam("id") String id
			){
		UnfindCard c = ucs.getByID(Integer.valueOf(id));
		c.setComfire("已确认");
		ucs.modifyUnfindCard(c);
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
	
	@RequestMapping(value = "/showUnfindCard.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showUnfindCard(){
		List<UnfindCard> data = ucs.showAll();
		int totalCount = data.size();
		JSONArray array = JSONArray.fromObject(data, configJson());
		
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
}
