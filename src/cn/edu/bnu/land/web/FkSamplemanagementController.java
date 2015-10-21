package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.FkSamplemanagement;
import cn.edu.bnu.land.service.FkSamplemanagementService;

@Controller
public class FkSamplemanagementController {

	private FkSamplemanagementService fkSamplemanagementService;
	@Autowired
	public FkSamplemanagementController(FkSamplemanagementService fkSamplemanagementService){
		this.fkSamplemanagementService=fkSamplemanagementService;
	}
	@RequestMapping(value="/add_sample",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> handle1(@RequestBody FkSamplemanagement fkSamplemanagement)throws IOException
	{
		this.fkSamplemanagementService.add_sample(fkSamplemanagement);
		Map<String,Object> model = new HashMap<String, Object>();   		  
        model.put("success",true); 
        model.put("msg",fkSamplemanagement.getSampleId()+"添加成功");
	    return(model);	
	}
	/*select sample by sampleId
	 * 2013-12-21
	 * @WW
	 * */
	@RequestMapping(value="/findsample")
	@ResponseBody
	public Map<String, Object> handle2(@RequestParam("start") String start,
			@RequestParam("limit") String limit,			
			@RequestParam ("searchKeyword") String searchKeyword) throws IOException 
	{ 	
		System.out.println("findsample"); 
		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> mylist=this.fkSamplemanagementService.select(start, limit, searchKeyword);		 
        return (mylist); 
	}
	/*
	 * delete sample 
	 * @WW  2013-12-19 
	 */
	@RequestMapping(value = "/del_sampleByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void del_sampleByIds(@RequestParam("sampleIds") String[] sampleIds)
			throws IOException {
		System.out.println(sampleIds);
		this.fkSamplemanagementService.del_sampleByIds(sampleIds);	
	}
	/*
	 * Edit sample get sampleDetail
	 *  @WW  2013-12-19 
	 * */
	@RequestMapping(value = "/get_sampleDetail")
	@ResponseBody
	public Map<String, Object> getsampleDetail (@RequestParam("projectSelectedId") String sampleId) throws IOException{
		System.out.println("now is sampleDetail,to get projectSelectedId = " +sampleId);
		FkSamplemanagement sampleDetail = this.fkSamplemanagementService.getSampleById(sampleId);
		Map<String, Object> mysampleDetail =new HashMap<String, Object>();
		mysampleDetail.put("success", true);
		mysampleDetail.put("data",sampleDetail);
		//System.out.println("now is in InfoArticleController ,to get myInfoArticleDetail.getArticleName()) =" +myInfoArticleDetail.getArticleName());
		return mysampleDetail;				
	}
	/*
	 * 编辑采样点数据 2014-03-26 @ww
	 * */
	@RequestMapping(value = "/update_sample",method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object> updateSample(@RequestBody FkSamplemanagement fkSamplemanagement) throws IOException 
	{ 
		this.fkSamplemanagementService.updateSample(fkSamplemanagement);	
		Map<String,Object> sample = new HashMap<String, Object>();   		  
		sample.put("success",true);  
		sample.put("msg", "样点"+fkSamplemanagement.getSampleId()+",successfully updated");   
        return  (sample);		 
	}   
	
	/*
	 * TIANjia pingjiazhibiao get sampleDetail
	 *  @WW  2013-12-19 
	 * */
	@RequestMapping(value = "/get_sampleDetail_E")
	@ResponseBody
	public Map<String, Object> getsampleDetail_E (@RequestParam("projectSelectedId") String projectId) throws IOException{
		System.out.println("now is Pingjia_sampleDetail,to get projectSelectedId = " +projectId);
		List sampleDetail = this.fkSamplemanagementService.getSampleById_E(projectId);
		Map<String, Object> mysampleDetail =new HashMap<String, Object>();
		mysampleDetail.put("success", true);
		mysampleDetail.put("data",sampleDetail.get(0));
		//System.out.println("now is in InfoArticleController ,to get myInfoArticleDetail.getArticleName()) =" +myInfoArticleDetail.getArticleName());
		return mysampleDetail;				
	}
	
	/*
	 * 获取抽样点数据，显示在地图上
	 *  @吴灿  2014-04-22 
	 * */
	@RequestMapping(value = "/get_sampleManagement")
	@ResponseBody
	public Map<String, Object> getSampleManagement (
			@RequestParam("projectId") String projectId,
			@RequestParam("limit") String limit) throws IOException{
		System.out.println("获取抽样点数据 ，任务号为： " +projectId);
		return this.fkSamplemanagementService.getSampleManagement(projectId,limit);		
	}
		
}
