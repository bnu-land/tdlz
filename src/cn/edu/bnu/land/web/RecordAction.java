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

import cn.edu.bnu.land.model.Noticement;
import cn.edu.bnu.land.model.Record;
import cn.edu.bnu.land.service.RecordService;

@Controller
public class RecordAction {

	private String category;
	private String path;
	private String statusUp;
	private String statusAug;
	private Date date;
	private RecordService rs;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	private String jsonString;//这个就是中转站了
	private List<Record> datas;
	private int totalCount;//这个是extjs用来分页
	
	//文件上传
	private File upload;
    private String uploadContentType;

    private String uploadFileName; // fileName 前面必須和upload一致,不然找不到文件
	
    private String fileName;
    private String projectID;
    private String id;
	//////////////
	
    
	public File getUpload() {
		return upload;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	///////////////
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
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

	public List<Record> getDatas() {
		return datas;
	}

	public void setDatas(List<Record> datas) {
		this.datas = datas;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatusUp() {
		return statusUp;
	}

	public void setStatusUp(String statusUp) {
		this.statusUp = statusUp;
	}

	public String getStatusAug() {
		return statusAug;
	}

	public void setStatusAug(String statusAug) {
		this.statusAug = statusAug;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public RecordService getRs() {
		return rs;
	}
	@Autowired
	public void setRs(RecordService rs) {
		this.rs = rs;
	}
	
	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    jcf.setExcludes(new String[]{"suggest"});
	    return jcf;  
	} 
	

	
	@RequestMapping(value = "/download.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String download(
			HttpServletResponse response, 
			HttpServletRequest request, 
			@RequestParam String path
			)
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
	@RequestMapping(value = "/upload.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String upLoad(
			HttpServletRequest request,
			@RequestParam(value = "uploadFileName", required = false) String uploadFileName,
			@RequestParam("upload") MultipartFile file,
			@RequestParam("id") String id
			) {  
		
		uploadFileName = file.getOriginalFilename();
		Date d = new Date();
		 try {
				uploadFileName = new String(uploadFileName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
		
		   System.out.println("uploadFileName = "+ fileName); //要上传的文件名
		  //扩展名
		   String fileEx = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
		   
		//   System.out.println("扩展名是"+ fileEx);
		//获得wapps的路径，uploader 指的放在文件的地方
		   String toSrc = request.getSession().getServletContext().getRealPath("upload")+"/";

		   System.out.println("toFile= "+toSrc);
		  
		   if (!file.isEmpty()) {  
	            try {  
	                this.writeFile(file.getInputStream(), toSrc, id+fileEx);  

	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	       } 
		  
		   Record record = rs.findRecord(Integer.valueOf(id));
		   
		   record.setStatusUp("已上传");
		   record.setStatusAug("待审核");
		   record.setPath(toSrc + id + fileEx);
		   rs.changeStatus(record);
		   
		   System.out.println("写入之后");
		   jsonString = "{\"success\": true}";
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
	
	
	
	
	@RequestMapping(value = "/showRecord.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showRecord(
			)
			throws IOException {
		
		datas = rs.showRecord();
		totalCount = datas.size();
		
		JSONArray array = JSONArray.fromObject(datas, configJson());
		
		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":" + "true" +",\"results\":[";
	
		for (int i = 0; i < totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
		
//		System.out.println("进入show");
		System.out.println(jsonString);
		return (jsonString);
		

	}
	
	
	@RequestMapping(value = "/auditRecord.action")
	@ResponseBody
	public Map<String, Object> auditRecord(
			@RequestParam("id") String id,
			@RequestParam("statusAug") String statusAug
			)
			throws IOException {
		Record record = rs.findRecord(Integer.valueOf(id));
		record.setStatusAug(statusAug);
		rs.changeStatus(record);
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
	
	@RequestMapping(value = "/createRecord.action")
	@ResponseBody
	public Map<String, Object> createRecord(
			@RequestParam("projectID") String projectID
			)
			throws IOException {
		
		
		
		List<Record> data = rs.showRecordByProject(projectID);
		
		if (data == null || data.isEmpty()) {
			
			
			
			Record[] record = new Record[16];		
			for (int i = 0; i < record.length; i++) {
				record[i] = new Record();
			}
			
			record[0].setCategory("重庆市区县投资农村建设用地复垦项目入库核查备案申请表");		
			record[1].setCategory("农村集体建设用地复垦项目踏勘意见书");
			record[2].setCategory("农村建设用地复垦协议书");
			record[3].setCategory("农村集体建设用地复垦项目符合村镇建设规划和土地利用总体规划的证明");
			record[4].setCategory("林业证明");
			record[5].setCategory("补偿标准文件");
			record[6].setCategory("复垦片块远近景照片");
			record[7].setCategory("区县承诺书");
			record[8].setCategory("规划设计单位承诺书");
			record[9].setCategory("关于农村建设用地复垦项目投资来源的说明");
			record[10].setCategory("复垦证明");
			record[11].setCategory("农村建设用地复垦项目实施方案评审意见书");
			record[12].setCategory("农村建设用地复垦项目实施方案评审基本信息表");
			record[13].setCategory("关于实施方案的复核意见");
			record[14].setCategory("补偿信息表");
			record[15].setCategory("拆迁协议总表");
			
			
			
			for (int i = 0; i < record.length; i++) {
				record[i].setDate(traslateDate(new Date()));
				record[i].setPath("");
				record[i].setStatusAug("未审核");
				record[i].setStatusUp("未上传");
				record[i].setProjectID(projectID);
				rs.createRecord(record[i]);
			}
		
		}
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
}
