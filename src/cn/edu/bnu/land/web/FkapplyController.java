package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.Fkapply;
import cn.edu.bnu.land.service.FkapplyService;
import cn.edu.bnu.land.service.fileUploadService;
import cn.edu.bnu.land.common.Word2Html;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
public class FkapplyController {
	private FkapplyService fkapplyService;
	private CommonsMultipartResolver commonsMultipartResolver;
	 @Autowired
	 public FkapplyController(FkapplyService fkapplyService,CommonsMultipartResolver commonsMultipartResolver)
	 {
		this.fkapplyService= fkapplyService;
		this.commonsMultipartResolver=commonsMultipartResolver;

	 }
	 /* 提交申请表
	     * url:submit_apply
	     * */
    @RequestMapping(value ="/submit_apply",method=RequestMethod.POST)	 
	@ResponseBody 	
	public Map<String, Object> saveApply(@RequestParam("PROJECTID") String projectId,
			@RequestParam("projectnameG") String projectName,
			@RequestParam("conUnit") String applyConUnit,
			@RequestParam("fileCheck") String fileCheck,
			@RequestParam ("applyNote") String applyNote) throws IOException 
	{ 
	    System.out.println("add apply");
	    System.out.print("projectId:"+projectId+"projectName:"+projectName+"fileCheck:"+fileCheck);
	    this.fkapplyService.addApply(projectId, projectName, applyConUnit, fileCheck, applyNote); 	    
	    Map<String,Object> model = new HashMap<String, Object>();   		  
        model.put("success",true);  
        model.put("msg", projectName+"successfully saved");   
        return (model);       
    } 
    /* 文件上传
     * submit_applyFile
     * url:submit_applyFile
     * */
    @RequestMapping(value = "/submit_applyFile" ,method=RequestMethod.POST)	 
	@ResponseBody 	
	public Map<String,Object> handleSaveApply1(HttpServletRequest request,HttpServletResponse response) throws Exception 
	{ 
	  System.out.println("文件连接-11");
	  fileUploadService.fileUpload(request, response, "fkapplyfilename"); 
	  fileUploadService.fileUpload(request, response, "fkapplyReport");
	  fileUploadService.fileUpload(request, response, "fkapplyFinancialDocuments");
	  fileUploadService.fileUpload(request, response, "fkapplyBidding");
	  fileUploadService.fileUpload(request, response, "statusMap");
	  fileUploadService.fileUpload(request, response, "planMap");
	  fileUploadService.fileUpload(request, response, "builtDrawingsMap");
	  
        Map<String,Object> model = new HashMap<String, Object>();           		  
        model.put("success",true);  
        model.put("msg", "所有申请文件已经提交成功");   
        return  (model);        
    } 
    
    /*
		public String handle(Fkapply fkapply,BindingResult result) throws IOException{  
    	    System.out.println("文件连接");
	        ExtJSFormResult extjsFormResult = new ExtJSFormResult();  
	   
	        if (result.hasErrors()){  
	            for(ObjectError error : result.getAllErrors()){  
	                System.err.println("Error2014: " + error.getCode() +  " - " + error.getDefaultMessage());  
	            }  	   
	            //set <a target="_blank" title="extjs" href="http://sencha.com/">extjs</a> return - error  
	            extjsFormResult.setSuccess(false);     
	            return extjsFormResult.toString();  
	        }  
	        File file=new File("f://eclipsework2//MyTLFK//WebContent//Upload/ss.png");
	        if(!file.exists())
	            file.createNewFile();
	        fkapply.getFile().transferTo(file);
	        System.out.println( fkapply.getFile().getContentType());	   
	        // Some type of file processing...  
	        System.err.println("-------------------------------------------");  
	        System.err.println("Test upload: " + fkapply.getFile().getOriginalFilename());  
	        System.err.println("-------------------------------------------");  
	        System.out.println("upload userpwd:"+fkapply.getProjectname());
	        //set <a target="_blank" title="extjs" href="http://sencha.com/">extjs</a> return - sucsess  
	        extjsFormResult.setSuccess(true);  
	        return extjsFormResult.toString();  
			 
		} */ 
		
		
	 /*2013-12-19 @WW
	    * select fk_apply
	    * */    
		@RequestMapping(value = "/searchApply")//,method=RequestMethod.POST) 
		@ResponseBody 	
		public Map<String, Object> handle1(@RequestParam("start") String start,
				@RequestParam("limit") String limit,			
				@RequestParam ("searchKeyword") String searchKeyword) throws IOException 
		{ 	
			System.out.println("searchApply"); 
			searchKeyword = Encoder.encode(searchKeyword);
			Map<String, Object> mylist=this.fkapplyService.select(start, limit, searchKeyword);		 
	        return (mylist); 
		}
		
		/*2013-12-19 @WW
		    * select check  fk_apply
		    * */    
			@RequestMapping(value = "/searchCheckApply")//,method=RequestMethod.POST) 
			@ResponseBody 	
			public Map<String, Object> checkapply(@RequestParam("start") String start,
					@RequestParam("limit") String limit,			
					@RequestParam ("searchKeyword") String searchKeyword) throws IOException 
			{ 	
				System.out.println("searchApply"); 
				searchKeyword = Encoder.encode(searchKeyword);
				Map<String, Object> mylist=this.fkapplyService.selectCheck(start, limit, searchKeyword);		 
		        return (mylist); 
			}
		 
		
		
		/*
		 * delete project apply 
		 * @WW  2013-12-19 
		 */
		@RequestMapping(value = "/del_applyByIds")
		// ,method=RequestMethod.POST)
		@ResponseBody
		public void del_projectByIds(@RequestParam("applyIds") String[] applyIds)
				throws IOException {
			this.fkapplyService.del_applyByIds(applyIds);	
		}
		/*
		 * word to html 查看申请表
		 * 
		 * */
		@RequestMapping(value = "/seeApplyword")
		// ,method=RequestMethod.POST)
		@ResponseBody
		public void seeApplyword()//@RequestParam("applyIds") String[] applyIds
				throws IOException {
			System.out.print("seeapply_word");
			String s=System.getProperty("user.dir");
			System.out.print("项目根路径："+s);
			Word2Html.wordToHtml("F:\\eclipsework2\\tdjg\\WebContent\\jsp\\EvaluateReport\\", "04.doc");
		} 
		/*
		 * word to html 查看前后对比图 seePictureword
		 * */
		@RequestMapping(value = "/seePictureword")
		// ,method=RequestMethod.POST)
		@ResponseBody
		public void seePictureword()//@RequestParam("applyIds") String[] applyIds
				throws IOException {
			System.out.print("seePictureword");
			Word2Html.wordToHtml("F:\\eclipsework2\\tdjg\\WebContent\\jsp\\Picture\\", "2011009.doc");
		} 
		/*
		 * word to html 查看前后对比图 seePictureword
		 * */
		@RequestMapping(value = "/seeBiddingword")
		// ,method=RequestMethod.POST)
		@ResponseBody
		public void seeBiddingword()//@RequestParam("applyIds") String[] applyIds
				throws IOException {
			System.out.print("seeBiddingword");
			Word2Html.wordToHtml("F:\\eclipsework2\\tdjg\\WebContent\\Upload\\fkapplyBidding\\", "fkapplyBidding20140305152609491.doc");
		} 
		
		/*
		 * 输入审核结果
		 * */
		@RequestMapping(value = "/applyCheckResult",method=RequestMethod.POST)
		@ResponseBody
		public void updateFkapplyResult(
				@RequestParam("checkApplyId") String checkApplyId,
				@RequestParam("applyResult")  String applyResult)
				throws IOException {
			checkApplyId = Encoder.encode(checkApplyId);			
			System.out.print("打印出来看看怎么是乱码:"+applyResult+"applyId:"+checkApplyId );
			this.fkapplyService.updatefkapply(checkApplyId, applyResult);
		}
		
}
