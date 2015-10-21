package cn.edu.bnu.land.web;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.common.Word2Html;
import cn.edu.bnu.land.model.FkQualityreport;
import cn.edu.bnu.land.service.FkQualityreportService;
import cn.edu.bnu.land.service.fileUploadService;

@Controller
public class FkQualityreportController {
	private FkQualityreportService fkQualityreportService;
	 @Autowired
	 public FkQualityreportController(FkQualityreportService fkQualityreportService){
		 this.fkQualityreportService=fkQualityreportService;
	 }
	 /*
	  * 保存项目土壤评价结果 20140218 @WW
	  */	
	 @RequestMapping(value = "/addEvaluateResult") //,method=RequestMethod.POST
		@ResponseBody 	
		public Map<String, Object> SaveSoilResult(@RequestParam("proJecTid") String proJecTid,
				@RequestParam("proJecTname") String proJecTname,
				@RequestParam("auditResults") String auditResults,
				@RequestParam("overallConclusion") String overallConclusion,
				@RequestParam ("evaluateResult") String evaluateResult) throws IOException 
				{ 
			this.fkQualityreportService.saveSoilresult(proJecTid, proJecTname, auditResults, evaluateResult, overallConclusion);
			Map<String,Object> model = new HashMap<String, Object>();   		  
	        model.put("success",true);  
	        model.put("msg", proJecTname+",successfully saved");   
	        return  (model);
			 
		} 
	 /*save GongCheng quality
		 * URL:GongchengOpinionResult
		 * */
	 @RequestMapping(value = "/GongchengOpinionResult") //,method=RequestMethod.POST
		@ResponseBody 	
		public Map<String, Object> SaveGongchengResult(@RequestParam("PROJECTID") String PROJECTID,
				@RequestParam("projectnameG") String projectnameG,
				@RequestParam("GongchengOpinion") String GongchengOpinion,
				@RequestParam("chgcid") String chgcid,
				@RequestParam("ntslid") String ntslid,
				@RequestParam("tjdlid") String tjdlid,
				@RequestParam ("tdpzid") String tdpzid) throws IOException 
				{ 
			this.fkQualityreportService.saveGongChengresult(PROJECTID, projectnameG, GongchengOpinion, chgcid, ntslid, tjdlid, tdpzid);
			Map<String,Object> model = new HashMap<String, Object>();   		  
	        model.put("success",true);  
	        model.put("msg", projectnameG+",successfully saved");   
	        return  (model);
			 
		} 
		/*save Expert quality
		 * 
		 * */
	 @RequestMapping(value = "/ExpertOpinionResult") //,method=RequestMethod.POST
		@ResponseBody 	
		public Map<String, Object> SaveExpertResult(@RequestParam("PROJECTID") String PROJECTID,
				@RequestParam("projectnameE") String projectnameE,
				@RequestParam("evaluationResults") String evaluationResults,
				@RequestParam("microreliefDescription") String microreliefDescription,
				@RequestParam("landProportion") String landProportion,
				@RequestParam("waterStorageCapacity") String waterStorageCapacity,
				@RequestParam("trafficConditions") String trafficConditions,
				@RequestParam("goukanQuality") String goukanQuality,
				@RequestParam("formingClodsDegree") String formingClodsDegree,
				@RequestParam("fertilityEvaluation") String fertilityEvaluation,
				@RequestParam ("evaluationExpert") String evaluationExpert) throws IOException 
				{ 
			this.fkQualityreportService.saveExpertresult(PROJECTID, projectnameE, evaluationResults, microreliefDescription, landProportion, waterStorageCapacity, trafficConditions, goukanQuality, formingClodsDegree, fertilityEvaluation, evaluationExpert);
			Map<String,Object> model = new HashMap<String, Object>();   		  
	        model.put("success",true);  
	        model.put("msg", projectnameE+",successfully saved");   
	        return  (model);
			 
		} 
	 
	 
	 
	 /*URL:selectEvaluateReport
	  * 查询项目评价结果
	  * */
	 @RequestMapping(value="/selectEvaluateReport")
		@ResponseBody
		public Map<String, Object> selectResults(@RequestParam("start") String start,
				@RequestParam("limit") String limit,			
				@RequestParam ("searchKeyword") String searchKeyword) throws IOException 
		{ 	
			searchKeyword = Encoder.encode(searchKeyword);
			Map<String, Object> mylist=this.fkQualityreportService.selectResults(start, limit, searchKeyword);		 
	        return (mylist); 
		}
	 /*测试上传下载文件
	  * URL:projectFileupload
      * @throws:IOException  
      * @desc:上传文件 
      * @params:自带参数,客户端不传入实参 
      * @Date:2014-3
	  * */
	 @RequestMapping(value = "/projectFileupload" ,method=RequestMethod.POST)	 
		@ResponseBody 	
		public Map<String,Object> projectFileupload(HttpServletRequest request,HttpServletResponse response) throws Exception 
		{ 
		  System.out.println("进入竣工报告提交projectFileupload");
		  fileUploadService.fileUpload(request, response, "projectReportFile");
		  fileUploadService.fileUpload(request, response, "expertFile");		 
	        Map<String,Object> model = new HashMap<String, Object>();           		  
	        model.put("success",true);  
	        model.put("msg", "项目文件验收已经提交成功");   
	        return  (model);        
	    } 	
	 /*
		 * word to html 查看评价报告
		 * 
		 * */
		@RequestMapping(value = "/seeReportword")
		// ,method=RequestMethod.POST)
		@ResponseBody
		public void seeReportword()//@RequestParam("applyIds") String[] applyIds
				throws IOException {
			//String s=System.getProperty("user.dir");
			//URL s=FkQualityreportController.class.getResource("");
			//InputStream is=(InputStream) FkQualityreportController.class.getResourceAsStream("/test.txt"); 
			//System.out.print("项目根路径："+is);
			Word2Html.wordToHtml("F:\\eclipsework2\\tdjg\\WebContent\\jsp\\EvaluateReport\\", "report.doc");
		} 
	 
}
