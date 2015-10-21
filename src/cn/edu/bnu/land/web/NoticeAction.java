package cn.edu.bnu.land.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import cn.edu.bnu.land.model.Application;
import cn.edu.bnu.land.model.Noticement;
import cn.edu.bnu.land.model.Suggestion;

import cn.edu.bnu.land.service.NoticementService;

@Controller
public class NoticeAction {

	NoticementService ns;
	
	int start;
	int limit;
	String id;
	String title;
	String content;
	String publisher;
	String category;
	String date;
	String projectID;
	String path;
	String fileName;
	String enclosureFlag;
	List<Noticement> datas;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	private String jsonString;//这个就是中转站了

	private File upload;
    private String uploadContentType;

    private String uploadFileName; // fileName 前面必須和upload一致,不然找不到文件
	private int totalCount;//这个是extjs用来分页
	
	

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
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

	public String getEnclosureFlag() {
		return enclosureFlag;
	}

	public void setEnclosureFlag(String enclosureFlag) {
		this.enclosureFlag = enclosureFlag;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public List<Noticement> getDatas() {
		return datas;
	}

	public void setDatas(List<Noticement> datas) {
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

	public void setCategory(String category) {
		this.category = category;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Autowired
	public void setNs(NoticementService ns) {
		this.ns = ns;
	}

	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    jcf.setExcludes(new String[]{"suggest"});
	    return jcf;  
	} 
	
	
	
	
	@RequestMapping(value = "/deleteNotice.action")
	@ResponseBody
	public Map<String, Object> deleteNoticement(@RequestParam("id") String id)
			throws IOException {
		ns.deleteNoticement(Integer.valueOf(id));
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/showNotice.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showNoticement(
			@RequestParam(value = "category" , required = false) String category,
			@RequestParam("limit") int limit,
			@RequestParam("start") int start,
			@RequestParam(value = "date" , required = false) String date
			)
			throws IOException {
		if (category != null) {
			this.category = category;
		}else{
			category = this.category;
		}
		if (date != null) {
			this.date = date;
		}else{
			date = this.date;
		}
		if (date.isEmpty()) {
//			System.out.println("进入if");
			datas = ns.showNoticement();
		}else {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
			Date newDate = null;
					 
			try {
				newDate = format.parse(date);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				    
					   
			datas = ns.filterNoticement(newDate);
		}
		
		
//		System.out.println("date:" + date);
//		System.out.println("start:" + start);
//		System.out.println("limit:" + limit);
//		System.out.println("category:" + category);
		
		List<Noticement> finalData = new ArrayList<Noticement>();
	
		for (Noticement n : datas) {
//			System.out.println(n.getCategory());
			if (n.getCategory().trim().equals(category.trim()) ) {
				
				finalData.add(n);
			}
		}
		totalCount = finalData.size();
		JSONArray array = JSONArray.fromObject(finalData, configJson());
		
		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":true" +",\"results\":[";
	
		for (int i = start; i < limit+start &&i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
//		System.out.println("进入show");
		System.out.println(jsonString);
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

	@RequestMapping(value = "/downNotice.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String download(
			HttpServletResponse response, 
			HttpServletRequest request, 
			@RequestParam String path)
	{
		fileName = path.substring(path.lastIndexOf("/")+1, path.length());
		OutputStream os = null;  
        response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
        response.setContentType("application/octet-stream; charset=utf-8");  
        try {  
            os = response.getOutputStream();  
            os.write(FileUtils.readFileToByteArray(new File(path)));  
            os.flush();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (os != null) {  
                try {  
                    os.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
		return "{\"success\": true}";
		
	}


	
	private static final int BUFFER_SIZE = 16 * 1024;
	@RequestMapping(value = "/addNotice.action")
	@ResponseBody
	public Map<String, Object> addNoticement(
			HttpServletRequest request,
			@RequestParam("category") String category,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("publisher") String publisher,
			@RequestParam("enclosureFlag") String enclosureFlag,
			@RequestParam("projectID") String projectID,
			@RequestParam(value = "fileName", required = false) String fileName,
			@RequestParam(value = "filename", required = false) String uploadFileName,
			@RequestParam(value = "upload", required = false) MultipartFile file
			)
			throws IOException {
		
		if (ns.getByID(1) == null) {
			Noticement notice = new Noticement();				
			notice.setCategory("");
			notice.setDate(traslateDate(new Date()));
			notice.setContent("");
			notice.setPublisher("");
			notice.setTitle("");	
			notice.setEnclosureFlag("无附件");
			notice.setEnclosurePath("");
			notice.setProjectID("");
			ns.addNoticement(notice);
			
		}
		String toSrc = "";
		Noticement notice = new Noticement();

		notice.setTitle(title);
		notice.setCategory(category);
		notice.setDate(traslateDate(new Date()));
		notice.setContent(content);
		notice.setPublisher(publisher);
		notice.setEnclosurePath(toSrc);
		notice.setEnclosureFlag(enclosureFlag);
		notice.setProjectID(projectID);
		Serializable saveID = ns.addNoticement(notice);	    
		
		String fileEx = "";
	    String name = "";	    
	    
	    if (enclosureFlag.equals("有附件")) {
	    	uploadFileName = file.getOriginalFilename();
	    	fileEx = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
	    	toSrc = request.getSession().getServletContext().getRealPath("noticeEnclosure")+"/";
	    	File toFile = new File(toSrc);
	  	  
		    writeFile(file.getInputStream(), toSrc, fileName + saveID +fileEx);
	    } 
	    notice.setEnclosurePath(toSrc + fileName + saveID +fileEx);
	    ns.modifyNoticement(notice);
		
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
		
	}

	private void writeFile(InputStream in, String dir, String fileName) throws IOException {  
		  
		 System.out.println(" == 文件寫入 == ");
		   
	        
	        File file = new File(dir, fileName);  
	        if (!file.exists()) {  

	            file.createNewFile();  
	        }  
	        FileUtils.copyInputStreamToFile(in, file);  
	        
		   System.out.println("写入成功！");
	}
	
}
