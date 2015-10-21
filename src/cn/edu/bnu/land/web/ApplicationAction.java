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


import cn.edu.bnu.land.model.Application;
import cn.edu.bnu.land.model.ComForDemolition;
import cn.edu.bnu.land.model.FarmersInformation;
import cn.edu.bnu.land.model.Record;
import cn.edu.bnu.land.model.Suggestion;
import cn.edu.bnu.land.model.UserInformation;
import cn.edu.bnu.land.service.AppSuggestionService;
import cn.edu.bnu.land.service.ApplicationService;
import cn.edu.bnu.land.service.ComForDemoService;
import cn.edu.bnu.land.service.FarmersInformationService;
import cn.edu.bnu.land.service.UserInformationService;

@Controller
public class ApplicationAction {
	ApplicationService as;
	UserInformationService us;
	FarmersInformationService fs;
	ComForDemoService cds;
	private String name;
	private String card;
	private String address;
	private String applicationAddress;
	private String phone;
	private String productID;
	private String totalArea;
	private String bulidArea;
	private String productDate;
	private String applicationDate;
	
	//四至范围
	private String north;
	private String south;
	private String west;
	private String east;
	
	private String work;
	private String afterUse;
	private String belongUse;
	private String reword;
	private String isAgreeSelf;
	private String isAgreeVillage;
	private String isAgreeTown;
	
	private String content;

	//权利证明
	private String pathBuilding;
	private String pathDirection;
	private String pathProtocol;
	private String pathHomestead;
	
	//审核状态与时间
	private String statusList;
	private String statusProvement;
	private Date dateList;
	private Date dateProvement;
	
	
	private File upload;
    private String uploadContentType;

    private String uploadFileName; // fileName 前面必須和upload一致,不然找不到文件

    private String fileName;
	
	//显示
	private String jsonString;//这个就是中转站了
	private int totalCount;//这个是extjs用来分页
	
	//分页
	int start;
	int limit;
	
	private String path;
	
	//申请ID
	private String id;
	
	
	
	
	public ComForDemoService getCds() {
		return cds;
	}
	@Autowired
	public void setCds(ComForDemoService cds) {
		this.cds = cds;
	}
	public FarmersInformationService getFs() {
		return fs;
	}
	@Autowired
	public void setFs(FarmersInformationService fs) {
		this.fs = fs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public UserInformationService getUs() {
		return us;
	}
	@Autowired
	public void setUs(UserInformationService us) {
		this.us = us;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public Date getDateList() {
		return dateList;
	}
	public void setDateList(Date dateList) {
		this.dateList = dateList;
	}
	public Date getDateProvement() {
		return dateProvement;
	}
	public void setDateProvement(Date dateProvement) {
		this.dateProvement = dateProvement;
	}
	public ApplicationService getAs() {
		return as;
	}
	@Autowired
	public void setAs(ApplicationService as) {
		this.as = as;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getApplicationAddress() {
		return applicationAddress;
	}
	public void setApplicationAddress(String applicationAddress) {
		this.applicationAddress = applicationAddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(String totalArea) {
		this.totalArea = totalArea;
	}
	public String getBulidArea() {
		return bulidArea;
	}
	public void setBulidArea(String bulidArea) {
		this.bulidArea = bulidArea;
	}
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getNorth() {
		return north;
	}
	public void setNorth(String north) {
		this.north = north;
	}
	public String getSouth() {
		return south;
	}
	public void setSouth(String south) {
		this.south = south;
	}
	public String getWest() {
		return west;
	}
	public void setWest(String west) {
		this.west = west;
	}
	public String getEast() {
		return east;
	}
	public void setEast(String east) {
		this.east = east;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getAfterUse() {
		return afterUse;
	}
	public void setAfterUse(String afterUse) {
		this.afterUse = afterUse;
	}
	public String getBelongUse() {
		return belongUse;
	}
	public void setBelongUse(String belongUse) {
		this.belongUse = belongUse;
	}
	public String getReword() {
		return reword;
	}
	public void setReword(String reword) {
		this.reword = reword;
	}
	public String getIsAgreeSelf() {
		return isAgreeSelf;
	}
	public void setIsAgreeSelf(String isAgreeSelf) {
		this.isAgreeSelf = isAgreeSelf;
	}
	public String getIsAgreeVillage() {
		return isAgreeVillage;
	}
	public void setIsAgreeVillage(String isAgreeVillage) {
		this.isAgreeVillage = isAgreeVillage;
	}
	public String getIsAgreeTown() {
		return isAgreeTown;
	}
	public void setIsAgreeTown(String isAgreeTown) {
		this.isAgreeTown = isAgreeTown;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPathBuilding() {
		return pathBuilding;
	}
	public void setPathBuilding(String pathBuilding) {
		this.pathBuilding = pathBuilding;
	}
	public String getPathDirection() {
		return pathDirection;
	}
	public void setPathDirection(String pathDirection) {
		this.pathDirection = pathDirection;
	}
	public String getPathProtocol() {
		return pathProtocol;
	}
	public void setPathProtocol(String pathProtocol) {
		this.pathProtocol = pathProtocol;
	}
	public String getPathHomestead() {
		return pathHomestead;
	}
	public void setPathHomestead(String pathHomestead) {
		this.pathHomestead = pathHomestead;
	}
	public String getStatusList() {
		return statusList;
	}
	public void setStatusList(String statusList) {
		this.statusList = statusList;
	}
	public String getStatusProvement() {
		return statusProvement;
	}
	public void setStatusProvement(String statusProvement) {
		this.statusProvement = statusProvement;
	}
	
	//日期转换函数
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	  
	    jcf.setExcludes(new String[]{"suggest"});
	    return jcf;  
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
	
	@RequestMapping(value = "/showApp.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showAppStatus()
			throws IOException {
	
		List<Application> datas = as.showApplication();
		JSONArray array;
		totalCount = datas.size();
		array = JSONArray.fromObject(datas, configJson());


		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":" + "true" 
		+",\"results\":[";
	

		for (int i = 0; i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
		


		return (jsonString);
	}
	
	@RequestMapping(value = "/showAppByID.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showAppByID()
	{
		
		
		//List<Application> applications = as.findAppByFarmer(LoginRegistServiceImpl.userID);
		List<Application> applications = as.findAppByFarmer(1);
		
		Application app = applications.get(0);
		JSONArray array = JSONArray.fromObject(app, configJson());
		jsonString = "{\"totalCount\": 1" + ",\"success\":" + "true" +",\"results\":[";
		
		JSONObject oj = array.getJSONObject(0);  
			
		jsonString += oj.toString() + ",";
			

		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");
		
		
		System.out.println(jsonString);
		
		return (jsonString);
	}
	
	@RequestMapping(value = "/deleteApplication.action")
	@ResponseBody
	public Map<String, Object> deleteApplication(){
	
		
		//List<Application> applications = as.findAppByFarmer(LoginRegistServiceImpl.userID);
		List<Application> applications = as.findAppByFarmer(1);
		Application app = applications.get(0);
		as.deleteApplication(app.getId());
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	
	@RequestMapping(value = "/modifyApplication.action")
	@ResponseBody
	public Map<String, Object> modifyApplication(
			
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("afterUse") String afterUse,
			@RequestParam("applicationAddress") String applicationAddress,
			@RequestParam("applicationDate") String applicationDate,
			@RequestParam("belongUse") String belongUse,
			@RequestParam("bulidArea") String bulidArea,
			@RequestParam("card") String card,
			@RequestParam("content") String content,
			@RequestParam("east") String east,
			@RequestParam("isAgreeSelf") String isAgreeSelf,
			@RequestParam("isAgreeTown") String isAgreeTown,
			@RequestParam("isAgreeVillage") String isAgreeVillage,
			@RequestParam("north") String north,
			@RequestParam("phone") String phone,
			@RequestParam("productDate") String productDate,
			@RequestParam("productID") String productID,
			@RequestParam("reword") String reword,
			@RequestParam("south") String south,
			@RequestParam("totalArea") String totalArea,
			@RequestParam("west") String west,
			@RequestParam("work") String work
			
			){


		
		//List<Application> applications = as.findAppByFarmer(LoginRegistServiceImpl.userID);
		List<Application> applications = as.findAppByFarmer(1);
		Application app = applications.get(0);
		app.setName(name);
		app.setAddress(address);
		app.setAfterUse(afterUse);
		app.setApplicationAddress(applicationAddress);
		app.setApplicationDate(traslateDate(applicationDate));
		app.setBelongUse(belongUse);
		app.setBulidArea(Double.parseDouble(bulidArea));
		app.setCard(card);
		app.setContent(content);
		app.setEast(Double.parseDouble(east));
		app.setIsAgreeSelf(isAgreeSelf);
		app.setIsAgreeTown(isAgreeTown);
		app.setIsAgreeVillage(isAgreeVillage);
		app.setNorth(Double.parseDouble(north));
		app.setPhone(phone);
		app.setProductDate(traslateDate(productDate));
		app.setProductID(productID);
		app.setReword(reword);
		app.setSouth(Double.parseDouble(south));
		app.setStatusList("待审核");
		app.setTotalArea(Double.parseDouble(totalArea));
		app.setWest(Double.parseDouble(west));
		app.setWork(work);

		as.changeApplication(app);
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/addApplication.action")
	@ResponseBody
	public Map<String, Object> addApplication(
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("afterUse") String afterUse,
			@RequestParam("applicationAddress") String applicationAddress,
			@RequestParam("applicationDate") String applicationDate,
			@RequestParam("belongUse") String belongUse,
			@RequestParam("bulidArea") String bulidArea,
			@RequestParam("card") String card,
			@RequestParam("content") String content,
			@RequestParam("east") String east,
			@RequestParam("isAgreeSelf") String isAgreeSelf,
			@RequestParam("isAgreeTown") String isAgreeTown,
			@RequestParam("isAgreeVillage") String isAgreeVillage,
			@RequestParam("north") String north,
			@RequestParam("phone") String phone,
			@RequestParam("productDate") String productDate,
			@RequestParam("productID") String productID,
			@RequestParam("reword") String reword,
			@RequestParam("south") String south,
			@RequestParam("totalArea") String totalArea,
			@RequestParam("west") String west,
			@RequestParam("work") String work
			)throws IOException {
		Application app = new Application();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		
		app.setName(name);
		app.setAddress(address);
		app.setAfterUse(afterUse);
		app.setApplicationAddress(applicationAddress);
		app.setApplicationDate(traslateDate(applicationDate));
		app.setBelongUse(belongUse);
		app.setBulidArea(Double.parseDouble(bulidArea));
		app.setCard(card);
		app.setContent(content);
		app.setEast(Double.parseDouble(east));
		app.setIsAgreeSelf(isAgreeSelf);
		app.setIsAgreeTown(isAgreeTown);
		app.setIsAgreeVillage(isAgreeVillage);
		app.setNorth(Double.parseDouble(north));
		app.setPathBuilding("");
		app.setPathDirection("");
		app.setPathHomestead("");
		app.setPathProtocol("");
		app.setPhone(phone);
		app.setProductDate(traslateDate(productDate));
		app.setProductID(productID);
		app.setReword(reword);
		app.setSouth(Double.parseDouble(south));
		app.setStatusList("待审核");
		app.setStatusProvement("未上传");
		app.setTotalArea(Double.parseDouble(totalArea));
		app.setWest(Double.parseDouble(west));
		app.setWork(work);
		app.setDateList(dateList);
		app.setDateProvement(dateProvement);
		
		
		//UserInformation user = us.getByID(LoginRegistServiceImpl.userID);
		UserInformation user = us.getByID(1);
		
		ComForDemolition cfd = new ComForDemolition();
		cfd.setFarmer(fs.getByID(1));
		cfd.setStatusComfire("未确认");
		cds.addComForDemolition(cfd);
		
		app.setFarmer(user);
		as.addApplication(app);
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	

	private static final int BUFFER_SIZE = 16 * 1024;
	@RequestMapping(value = "/uploadApp.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String upLoad(
			HttpServletRequest request,
			@RequestParam(value = "uploadFileName", required = false) String uploadFileName,
			@RequestParam("upload") MultipartFile file,
			@RequestParam(value = "fileName", required = false) String fileName

			) {  
		
		Date d = new Date();
		uploadFileName = file.getOriginalFilename();
		   try {
			uploadFileName = new String(uploadFileName.getBytes("ISO-8859-1"), "UTF-8");
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  		   
		  //扩展名
		   String fileEx = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
		   String name = "";
		   int flag = 0;
		   //转换中文字体
		   if (fileName.equals("上传房产证")) {
			name = "fangchanzheng";
			flag = 1;
		}
		   if (fileName.equals("上传去向证明")) {
			name = "quxiangzhengming";
			flag = 2;
		}
		   if (fileName.equals("上传复垦协议")) {
			name = "fukenxieyi";
			flag = 3;
		}
		   if (fileName.equals("上传自愿放弃宅基地所有权证明")) {
			name = "fangqizhaijidi";
			flag = 4;
		}
		   
		//   System.out.println("扩展名是"+ fileEx);
		//获得wapps的路径，uploaderapp 指的放在文件的地方
		   //String toSrc = ServletActionContext.getServletContext().getRealPath("uploadApp")+"/"+name +LoginRegistServiceImpl.userID.toString()+fileEx;
		   String toSrc = request.getSession().getServletContext().getRealPath("uploadApp")+"/";

		   System.out.println("toFile= "+toSrc);
		  
		   if (!file.isEmpty()) {  
	            try {  
	                this.writeFile(file.getInputStream(), toSrc, name +"1" +fileEx);  

	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        } 
		   //List<Application> applications = as.findAppByFarmer(LoginRegistServiceImpl.userID);
		   List<Application> applications = as.findAppByFarmer(1);
		   Application application = applications.get(0);
		   application.setStatusProvement("待审核");
		   
		   switch (flag) {
		   		case 1:
		   			application.setPathBuilding(toSrc + name +"1" +fileEx);
		   		break;
		   		case 2:
		   			application.setPathDirection(toSrc + name +"1" +fileEx);
		   		break;
		   		case 3:
		   			application.setPathProtocol(toSrc + name +"1" +fileEx);
		   		break;
		   		case 4:
		   			application.setPathHomestead(toSrc + name +"1" +fileEx);
		   		break;
		   					
		}
		   
		   
		   
		   
		   as.changeApplication(application);
		   
		   
		   jsonString = "{\"success\" : true}";
		   return jsonString;
		
		
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
	

	
	//下载
	@RequestMapping(value = "/downloadApp.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String download(
			HttpServletResponse response, 
			HttpServletRequest request, 
			@RequestParam String path
			)
	{
		String[] name = path.split(",");
		System.out.println(name[0]);
		System.out.println(name[1]);
		Application app = as.findApplication(Integer.valueOf(name[0]));
		if (name[1].trim().equals("1")) {
			path = app.getPathBuilding();
		}
		if (name[1].trim().equals("2")) {
			path = app.getPathDirection();
		}
		if (name[1].trim().equals("3")) {
			path = app.getPathProtocol();
		}
		if (name[1].trim().equals("4")) {
			path = app.getPathHomestead();
		}
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

	
	@RequestMapping(value = "/auditApp.action")
	@ResponseBody
	public Map<String, Object> auditApplication(
			@RequestParam("id") String id,
			@RequestParam("statusList") String statusList,
			@RequestParam("statusProvement") String statusProvement
			
			
			){
		dateList = new Date();
		dateProvement = new Date();
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		
				
		Application app = as.findApplication(Integer.valueOf(id));
		if (!statusList.equals("")) {
			app.setStatusList(statusList);
			
			String tempDate = format.format(dateList);
			
			app.setDateList(traslateDate(tempDate));
			as.changeApplication(app);
		}
		if (!statusProvement.equals("")) {
			app.setStatusProvement(statusProvement);
			
			String tempDate = format.format(dateList);
			
			app.setDateProvement(traslateDate(tempDate));
			
			as.changeApplication(app);
		}
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
}


	