package cn.edu.bnu.land.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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


import cn.edu.bnu.land.model.CardPiece;
import cn.edu.bnu.land.model.ComForDemolition;
import cn.edu.bnu.land.service.CardPieceService;

@Controller
public class CardPieceAction {
	private CardPieceService cps;
	private String houseNumber;
	private String QSDWMC;
	private String DYNHXM;
	private String DYNHSFZ;
	private String Shape_Area;
	private String FWJG;
	private String FWSYSJ;
	private String DLMC;
	private String SFDYXZSJ;
	private String FWZP;
	private String joinFlag;
	private String projectID;
	private String fileName;
	private String path;
	
	private String jsonString;
	
	
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public CardPieceService getCps() {
		return cps;
	}
	@Autowired
	public void setCps(CardPieceService cps) {
		this.cps = cps;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getQSDWMC() {
		return QSDWMC;
	}
	public void setQSDWMC(String qSDWMC) {
		QSDWMC = qSDWMC;
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
	public String getShape_Area() {
		return Shape_Area;
	}
	public void setShape_Area(String shape_Area) {
		Shape_Area = shape_Area;
	}
	public String getFWJG() {
		return FWJG;
	}
	public void setFWJG(String fWJG) {
		FWJG = fWJG;
	}
	public String getFWSYSJ() {
		return FWSYSJ;
	}
	public void setFWSYSJ(String fWSYSJ) {
		FWSYSJ = fWSYSJ;
	}
	public String getDLMC() {
		return DLMC;
	}
	public void setDLMC(String dLMC) {
		DLMC = dLMC;
	}
	public String getSFDYXZSJ() {
		return SFDYXZSJ;
	}
	public void setSFDYXZSJ(String sFDYXZSJ) {
		SFDYXZSJ = sFDYXZSJ;
	}
	public String getFWZP() {
		return FWZP;
	}
	public void setFWZP(String fWZP) {
		FWZP = fWZP;
	}
	public String getJoinFlag() {
		return joinFlag;
	}
	public void setJoinFlag(String joinFlag) {
		this.joinFlag = joinFlag;
	}
	
	public static JsonConfig configJson(){  
	    JsonConfig jcf = new JsonConfig();  
	    jcf.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
	    jcf.setExcludes(new String[]{"dlogs"});
	    return jcf;  
	} 
	
	
	@RequestMapping(value = "/showCard.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showCardPiece(){
		List<CardPiece> datas = cps.showCard();
		JSONArray array;
		int totalCount = datas.size();
		array = JSONArray.fromObject(datas, configJson());


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
	
	@RequestMapping(value = "/saveCard.action")
	@ResponseBody
	public Map<String, Object> addCardPiece(
			@RequestParam("houseNumber") String houseNumber,
			@RequestParam("DLMC") String DLMC,
			@RequestParam("DYNHSFZ") String DYNHSFZ,
			@RequestParam("DYNHXM") String DYNHXM,
			@RequestParam("FWJG") String FWJG,
			@RequestParam("FWSYSJ") String FWSYSJ,
			@RequestParam("FWZP") String FWZP,
			@RequestParam("QSDWMC") String QSDWMC,
			@RequestParam("SFDYXZSJ") String SFDYXZSJ,
			@RequestParam("Shape_Area") String Shape_Area,
			@RequestParam("projectID") String projectID
			){
		
		String[] houseNumberNew = houseNumber.split(",");
		String[] DLMCNew = DLMC.split(",");
		String[] DYNHSFZNew = DYNHSFZ.split(",");
		String[] DYNHXMNew = DYNHXM.split(",");
		String[] FWJGNew = FWJG.split(",");
		String[] FWSYSJNew = FWSYSJ.split(",");
		String[] FWZPNew = FWZP.split(",");
		String[] QSDWMCNew = QSDWMC.split(",");
		String[] SFDYXZSJNew = SFDYXZSJ.split(",");
 		String[] Shape_AreaNew = Shape_Area.split(",");
 		
 		for (int i = 0; i < houseNumberNew.length; i++) {
 			CardPiece cp = new CardPiece();
 			
 			cp.setHouseNumber(houseNumberNew[i]);
 			cp.setDLMC(DLMCNew[i]);
 			cp.setDYNHSFZ(DYNHSFZNew[i]);
 			cp.setDYNHXM(DYNHXMNew[i]);
 			cp.setFWJG(FWJGNew[i]);
 			cp.setFWSYSJ(FWSYSJNew[i]);
 			cp.setFWZP(FWZPNew[i]);
 			cp.setJoinFlag("未参与");
 			cp.setQSDWMC(QSDWMCNew[i]);
 			cp.setSFDYXZSJ(SFDYXZSJNew[i]);
 			cp.setDemolitionFlag("未拆迁");
 			cp.setProjectID(projectID);
 			cp.setShape_Area(Double.parseDouble(Shape_AreaNew[i]));
 			
 			cps.addCard(cp);
		}
		
 		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", true);
		results.put("msg", "1"
				+ ",successfully saved");
		return (results);
	}
	
	
	//导出excel表
	@RequestMapping(value = "/downPlan.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String gridToExcel(
			HttpServletRequest request,
			HttpServletResponse response)
	{
		List<CardPiece> datas = cps.showCard();
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("可参与复垦农户表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		
		HSSFCell cell = row.createCell((short)0);
		cell.setCellValue("宅基地所在地");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("户主姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("户主身份证号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("房屋结构");
		cell = row.createCell((short) 4);
		cell.setCellValue("房屋使用时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("所属地类");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("现状地类");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("项目编号");
		cell.setCellStyle(style);
		
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < datas.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			CardPiece cp = (CardPiece) datas.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(cp.getQSDWMC());
			row.createCell((short) 1).setCellValue(cp.getDYNHXM());
			row.createCell((short) 2).setCellValue(cp.getDYNHSFZ());
			row.createCell((short) 3).setCellValue(cp.getFWJG());
			row.createCell((short) 4).setCellValue(cp.getFWSYSJ());
			row.createCell((short) 5).setCellValue(cp.getDLMC());
			row.createCell((short) 6).setCellValue(cp.getSFDYXZSJ());
			row.createCell((short) 7).setCellValue(cp.getProjectID());
		}
		
		// 第六步，将文件存到指定位置
		try
		{
			path = request.getSession().getServletContext().getRealPath("excel")+"/"+"guihua.xls";

		
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
		
}
