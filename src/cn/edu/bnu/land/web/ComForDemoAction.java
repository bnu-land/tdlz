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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import cn.edu.bnu.land.model.ApplicationDAO;
import cn.edu.bnu.land.model.ApplicationDAOImpl;
import cn.edu.bnu.land.model.ComForDemolition;
import cn.edu.bnu.land.model.FarmersInformation;
import cn.edu.bnu.land.model.Noticement;
import cn.edu.bnu.land.service.ApplicationService;
import cn.edu.bnu.land.service.ComForDemoService;
import cn.edu.bnu.land.service.ApplicationServiceImpl;

@Controller
public class ComForDemoAction {
	
	private ApplicationService as;
	private ComForDemoService cfds;
		
	private String area;
	private String id;
	private String flag;
	private String categoryAZ;
	private String category;
	
	//分辨回执单
	private String count;
	
	private File upload;
    private String uploadContentType;

    private String uploadFileName; // fileName 前面必須和upload一致,不然找不到文件

    private String fileName;
	
    private String projectID;
	//显示
	private String jsonString;//这个就是中转站了
	private int totalCount;//这个是extjs用来分页
	
	//分页
	int start;
	int limit;
	
	//用于导出的路径
	private String path;
	
	private String filename;
	
	
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public ApplicationService getAs() {
		return as;
	}
	@Autowired
	public void setAs(ApplicationService as) {
		this.as = as;
	}
	public String getCategoryAZ() {
		return categoryAZ;
	}
	public void setCategoryAZ(String categoryAZ) {
		this.categoryAZ = categoryAZ;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public ComForDemoService getCfds() {
		return cfds;
	}
	@Autowired
	public void setCfds(ComForDemoService cfds) {
		this.cfds = cfds;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	//什么都没做
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	  
	    return jcf;  
	} 
	
	//日期转换
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
	
	@RequestMapping(value = "/changeStatus.action")
	@ResponseBody
	public Map<String, Object> changeStatus(@RequestParam("id") String id)
			throws IOException {
		ComForDemolition cfd = cfds.getByID(Integer.valueOf(id));
		cfd.setStatusComfire("已确认拆迁");
		cfd.setComfireDate(traslateDate(new Date()));
		cfds.changeComForDemolition(cfd);
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	@RequestMapping(value = "/showMontBC.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showByCategory(
			@RequestParam("category") String category,
			@RequestParam("limit") int limit,
			@RequestParam("start") int start
			)
			throws IOException {

		List<ComForDemolition> temp = cfds.showAll();
		List<ComForDemolition> datas = new ArrayList<ComForDemolition>();
		if (category.equals("补偿监测")) {
			datas = temp;
		}else{
			
			for (ComForDemolition c : temp)
			{
				if (c.getFirstProvement() == null||
						c.getSecondProvement() == null) {
					datas.add(c);
				}
			}
		}

		totalCount = datas.size();
		JSONArray array = JSONArray.fromObject(datas, configJson());
		
		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":" + "true" +
		",\"results\":[";
	
		//for (int i = start; i < limit+start &&i <totalCount; i++)
		for (int i = 0; i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			FarmersInformation f = datas.get(i).getFarmer();

			jsonString += "{\"" + "name" + "\"" + ":\"" + f.getFarmername() + "\",";
			jsonString += "\"" + "phone" + "\"" + ":\"" + f.getPhone().trim() + "\",";
			jsonString += "\"" + "card" + "\"" + ":\"" + f.getIdnumber().toString() + "\"}#";
			
			jsonString += oj.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace("}#{", ",");
		jsonString = jsonString.replace(",]}", "]}");
	
		return jsonString;
	}
	
	@RequestMapping(value = "/showMontAZ.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showByAZ(
			@RequestParam("categoryAZ") String categoryAZ,
			@RequestParam("limit") int limit,
			@RequestParam("start") int start
			)
			throws IOException {
		
		List<ComForDemolition> temp = cfds.showAll();
		List<ComForDemolition> datas = new ArrayList<ComForDemolition>();
		if (categoryAZ.equals("安置监测")) {
			datas = temp;
		}else{
			
			for (ComForDemolition c : temp)
			{
				List<Application> app =  as.findAppByFarmer(c.getFarmer().getId());
				if (app != null) {
					if (app.get(0).getPathDirection() == null) {
						datas.add(c);
					}					
				}
			}
		}

		totalCount = datas.size();
		JSONArray array = JSONArray.fromObject(datas, configJson());
		
		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":" + "true" +
		",\"results\":[";
	    
		//for (int i = start; i < limit+start &&i <totalCount; i++)
		for (int i = 0; i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			FarmersInformation f = datas.get(i).getFarmer();
			List<Application> app =  as.findAppByFarmer(f.getId());
			if (app == null) {
				continue;
			}
			String dPath = app.get(0).getPathDirection();
			
			if (dPath != null) {
				dPath = dPath.replace("\\", "\\\\");
			}
			if (dPath == null) {
				dPath = "";
			}
			
			jsonString += "{\"" + "name" + "\"" + ":\"" + f.getFarmername().trim() + "\",";
			jsonString += "\"" + "address" + "\"" + ":\"" + f.getAddress().trim() + "\",";
			jsonString += "\"" + "provement" + "\"" + ":\"" + dPath + "\",";
			jsonString += "\"" + "idnumber" + "\"" + ":\"" + f.getIdnumber().trim() + "\",";
			jsonString += "\"" + "phone" + "\"" + ":\"" + f.getPhone().toString().trim() + "\"}#";
			
			jsonString += oj.toString() + ",";
			
        }
		
		jsonString += "]}";
		jsonString = jsonString.replace("}#{", ",");
		jsonString = jsonString.replace(",]}", "]}");

		return jsonString;
	}
	
	@RequestMapping(value = "/showAllComForDemo.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showAllComForDemo(
			@RequestParam(value = "flag", required = false) String flag
			)
			throws IOException {
		if (flag != null) {
			this.flag = flag;
		}else{
			flag = this.flag;
		}

		List<ComForDemolition> datas;
		
		//根据flag来判断是加载一条还是加载全部
		if (flag.trim().equals("1")) {
			//datas = cfds.showByFarmer(Integer.valueOf(LoginRegistServiceImpl.userID));
			datas = cfds.showByFarmer(Integer.valueOf(1));
		}else
		{
			datas = cfds.showAll();
		}
		JSONArray array;
		totalCount = datas.size();
		array = JSONArray.fromObject(datas, configJson());


		jsonString = "{\"totalCount\":" + this.getTotalCount() + ",\"success\":" + "true" +
		",\"results\":[";
	

		for (int i = 0; i <totalCount; i++)
        {
			JSONObject oj = array.getJSONObject(i);  
			
			FarmersInformation f = datas.get(i).getFarmer();

			jsonString += "{\"" + "name" + "\"" + ":\"" + f.getFarmername() + "\",";
			jsonString += "\"" + "card" + "\"" + ":\"" + f.getIdnumber().toString() + "\"}#";
			jsonString += oj.toString() + ",";

        }
		jsonString += "]}";
		jsonString = jsonString.replace("}#{", ",");
		jsonString = jsonString.replace(",]}", "]}");
		
		System.out.println("申请");
		System.out.println(jsonString);
		return jsonString;
	}
	
	@RequestMapping(value = "/changeArea.action")
	@ResponseBody
	public Map<String, Object> changeArea(
			@RequestParam("id") String id,
			@RequestParam("area") String area
			)
			throws IOException {
		ComForDemolition cfd = cfds.getByID(Integer.valueOf(id));
		Float newArea = Float.valueOf(area);
		Float totalMoney = new Float(newArea * (96000/666.667));
		Float firstMoney = new Float(totalMoney * 0.4);
		Float secondMoney = new Float(totalMoney - firstMoney);
		
		
		
		
		cfd.setArea(newArea);
		cfd.setFirstMoney(firstMoney);
		cfd.setSecondMoney(secondMoney);
		cfd.setTotalMoney(totalMoney);
		
		cfds.changeComForDemolition(cfd);
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	private static final int BUFFER_SIZE = 16 * 1024;
	@RequestMapping(value = "/uploadProc.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String uploadProc(
			HttpServletRequest request,
			@RequestParam(value = "uploadFileName", required = false) String uploadFileName,
			@RequestParam("upload") MultipartFile file,
			@RequestParam("count") String count,
			@RequestParam("id") String id
			) {  
		
		Date d = new Date();
		uploadFileName = file.getOriginalFilename();
		   try {
			uploadFileName = new String(uploadFileName.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		   System.out.println("uploadFileName = "+ uploadFileName);
		  //扩展名
		   String fileEx = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
		   String name = "";
		   int flag = 0;
		   //转换中文字体
		   if (count.trim().equals("1")) {
			name = "first";
			flag = 1;
		}
		   if (count.trim().equals("2")) {
			name = "second";
			flag = 2;
		}

		   
		//   System.out.println("扩展名是"+ fileEx);
		//获得wapps的路径，uploaderapp 指的放在文件的地方
		   String toSrc = request.getSession().getServletContext().getRealPath("uploadProc")+"/"+name +id+fileEx;

		   System.out.println("toFile= "+toSrc);
		  
		   
		   if (!file.isEmpty()) {  
	            try {  
	                this.writeFile(file.getInputStream(), toSrc, name + id+fileEx);  

	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        } 
		  
		   ComForDemolition cfd = cfds.getByID(Integer.valueOf(id));

		   
		   switch (flag) {
	   		case 1:
	   			cfd.setFirstProvement(toSrc + name + id+fileEx);
	   			cfd.setFirstMoneyDate(traslateDate(new Date()));
	   		break;
	   		case 2:
	   			cfd.setSecondProvement(toSrc + name + id+fileEx);
	   			cfd.setSecondMoneyDate(traslateDate(new Date()));
	   		break;
	   					
		   }
		   
		   
		   
		   
		   cfds.changeComForDemolition(cfd);
		   
		   
		   
		   System.out.println("写入之后");
		 
		   return "{\"success\": true}";
		
		
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
	
	
	//导出excel表
	@RequestMapping(value = "/gridToExcel.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String gridToExcel(
			HttpServletResponse response, 
			HttpServletRequest request,
			@RequestParam(value = "projectID", required = false) String projectID
			)
	{
		if (projectID == null) {
			projectID = this.projectID;
		}else{
			this.projectID = projectID;
		}
		List<ComForDemolition> datas = cfds.showByProject(projectID);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("拆迁确认总表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		
		HSSFCell cell = row.createCell((short)0);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("身份证号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("是否已确认拆迁");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("项目编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("手印/印章");
		cell.setCellStyle(style);
		
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < datas.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			ComForDemolition cfd = (ComForDemolition) datas.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(cfd.getFarmer().getFarmername());
			row.createCell((short) 1).setCellValue(cfd.getFarmer().getIdnumber());
			row.createCell((short) 2).setCellValue(cfd.getStatusComfire());
			row.createCell((short) 4).setCellValue(cfd.getFarmer().getProjectID());
			cell = row.createCell((short) 3);
			if (cfd.getComfireDate() != null) {
				cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(cfd.
						getComfireDate()));
			}else{
				cell.setCellValue("");
			}
			
		}
		
		// 第六步，将文件存到指定位置
		try
		{
			path = request.getSession().getServletContext().getRealPath("excel")+"/"+ projectID + "chaiqian.xls";
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
			
		
			

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return "{\"success\": true}";
	}
	
	@RequestMapping(value = "/downHZD.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String downHZD(
			HttpServletResponse response, 
			HttpServletRequest request, 
			@RequestParam("id") String id
			)
	{
		String[] name = id.split(",");
		System.out.println(name[0]);
		System.out.println(name[1]);
		ComForDemolition c = cfds.getByID(Integer.valueOf(name[0]));
		if (name[1].equals("1")) {
			path = c.getFirstProvement();
		}else {
			path = c.getSecondProvement();
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

	@RequestMapping(value = "/downDirection.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String downDirection(
			HttpServletResponse response, 
			HttpServletRequest request, 
			@RequestParam("id") String id
			)
	{
		ComForDemolition c = cfds.getByID(Integer.valueOf(id));
		List<Application> app = as.findAppByFarmer(c.getFarmer().getId());
		
		
		path = app.get(0).getPathDirection();
		
		
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
		
}
