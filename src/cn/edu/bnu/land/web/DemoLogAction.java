package cn.edu.bnu.land.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


import cn.edu.bnu.land.model.CardPiece;
import cn.edu.bnu.land.model.DemolitionLog;
import cn.edu.bnu.land.service.CardPieceService;
import cn.edu.bnu.land.service.DemoLogService;

@Controller
public class DemoLogAction {
	
	DemoLogService logs;
	CardPieceService cps;
	
	//创建日志时用于接收前台值
	private String numberSFZ;
	private String content;
	private String date;
	private String person;
	private Integer photoNumber;
	private String photoPath;
	private String cardStatus;
	
	private File upload;
    private String uploadContentType;

    private String uploadFileName; // fileName 前面必須和upload一致,不然找不到文件

    private String fileName;
	
	//显示
	private String jsonString;//这个就是中转站了
	private int totalCount;//这个是extjs用来分页
	private String projectID;
	//分页
	int start;
	int limit;
	
	//照片基地址
	private String path;
	
	//照片后缀名
	private String photoExt = "";
	
	private String count;
	
	
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getNumberSFZ() {
		return numberSFZ;
	}
	public void setNumberSFZ(String numberSFZ) {
		this.numberSFZ = numberSFZ;
	}
	public CardPieceService getCps() {
		return cps;
	}
	@Autowired
	public void setCps(CardPieceService cps) {
		this.cps = cps;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public DemoLogService getLogs() {
		return logs;
	}
	@Autowired
	public void setLogs(DemoLogService logs) {
		this.logs = logs;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}

	public Integer getPhotoNumber() {
		return photoNumber;
	}
	public void setPhotoNumber(Integer photoNumber) {
		this.photoNumber = photoNumber;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
	@RequestMapping(value = "/addLog.action")
	@ResponseBody
	public Map<String, Object> addLog(
			@RequestParam("content") String content,
			@RequestParam("person") String person,
			@RequestParam("date") String date,
			@RequestParam("count") String count,
			@RequestParam("numberSFZ") String numberSFZ,
			@RequestParam("cardStatus") String cardStatus
			)
			throws IOException {
		
		DemolitionLog log = new DemolitionLog();
		List<CardPiece> cp = cps.showBySFZ(numberSFZ);
		if (cp.isEmpty()) {
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("success", false);
			results.put("msg", "1"
					+ ",successfully saved");
			return results;
		}
		
		cp.get(0).setDemolitionFlag(cardStatus);
		log.setContent(content);
		log.setPerson(person);

		log.setDate(traslateDate(date));

		log.setPhotoNumber(Integer.valueOf(count));
		if (!count.equals("0")) {

			log.setPhotoPath(path);
			log.setPhotoExt(photoExt);
		}else
		{
			log.setPhotoPath("");
			log.setPhotoExt("");
		}
		log.setCp(cp.get(0));
		log.setProjectID(cp.get(0).getProjectID());
		logs.addLog(log);
		
		cps.modifyCard(cp.get(0));
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	private static final int BUFFER_SIZE = 16 * 1024;
	@RequestMapping(value = "/uploadPhoto.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String upLoad(
			HttpServletRequest request,
			@RequestParam(value = "uploadFileName", required = false) String uploadFileName,
			@RequestParam("upload") MultipartFile file,
			@RequestParam("date") String date,
			@RequestParam("numberSFZ") String numberSFZ,
			@RequestParam("count") String count
			) {  
	
			uploadFileName = file.getOriginalFilename();
			try {
				uploadFileName = new String(uploadFileName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
		   if (logs.findByDate(traslateDate(date), numberSFZ)) {
			   jsonString =  "{\"success\": false}"; 
			   return jsonString;
		   }
		  //扩展名
		   String fileEx = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());

		   if (!count.equals("0")) {
			   if (count.equals("1")) {
				   photoExt = fileEx;
			}else 
			{
				photoExt = photoExt + "," + fileEx;
			}			  			   
		}
		   
		//   System.out.println("扩展名是"+ fileEx);
		//获得wapps的路径，uploaderapp 指的放在文件的地方
		  
		   String toSrc = request.getSession().getServletContext().getRealPath("uploadPhoto")+"/";
		   path = request.getSession().getServletContext().getRealPath("uploadPhoto")+"/" + numberSFZ + date;
		   System.out.println("toFile= "+toSrc);
		  
		   if (!file.isEmpty()) {  
	            try {  
	                this.writeFile(file.getInputStream(), toSrc, numberSFZ + date + count.toString() +fileEx);  

	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        } 
		  
		   
		   System.out.println("写入之后");
		   
		   return "{\"success\": true}";
		
		
	}
		
	
	
	private void writeFile(InputStream in, String dir, String fileName) throws IOException {  
		  
		   System.out.println(" == 文件寫入 == ");
		   
	        
	        File file = new File(dir, fileName);  
	        if (!file.exists()) {  
	            if (!file.getParentFile().exists()) {  
	                file.getParentFile().mkdirs();  
	            }  
	            file.createNewFile();  
	        }  
	        FileUtils.copyInputStreamToFile(in, file);  
	        
		   System.out.println("写入成功！");
		}
	
	
	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    jcf.setExcludes(new String[]{"dlogs"});
	    return jcf;  
	} 
	
	@RequestMapping(value = "/showLogs.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showLogs(
			@RequestParam("numberSFZ") String numberSFZ
			)
	{
		System.out.println("进入");
		List<DemolitionLog> temp = logs.showAll();
		JSONArray array;
		
		List<DemolitionLog> datas = new ArrayList<DemolitionLog>();
		for (DemolitionLog log : temp) {
			if (log.getCp().getHouseNumber().equals(numberSFZ)) {
				datas.add(log);
			}
		}
		totalCount = datas.size();
		array = JSONArray.fromObject(datas, configJson());


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
	
	
	@RequestMapping(value = "/showAllRecord.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showAllRecord()
			throws IOException {
		System.out.println("进入");
		List<DemolitionLog> datas = logs.showAll();
		JSONArray array;
		totalCount = datas.size();
		array = JSONArray.fromObject(datas, configJson());


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
}
