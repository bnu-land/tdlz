package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.FkGongcheng;
import cn.edu.bnu.land.service.FkGongchengService;

@Controller
public class FkGongchengController {

	private FkGongchengService fkGongchengService;
	@Autowired
	public FkGongchengController(FkGongchengService fkGongchengService){
		this.fkGongchengService=fkGongchengService;
	}
	/*
	 * 添加保存工程信息
	 * add_gongchengxinxi
	 * */	
	@RequestMapping(value = "/add_gongchengxinxi",method=RequestMethod.POST)
	@ResponseBody 	
	public Map<String,Object> saveGongcheng(@RequestBody FkGongcheng fkGongcheng) throws Exception 
	{ 	
		System.out.print("添加工程信息并上传文件吧");
		this.fkGongchengService.saveGongchengxin(fkGongcheng);		
		Map<String,Object> model = new HashMap<String, Object>();   		  
        model.put("success",true);  
        model.put("msg", fkGongcheng.getProjectId()+"successfully saved");   
        return  (model);
    } 
	/*
	@RequestMapping(value = "/add_gongchengxinxi",method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object> saveGongcheng(@RequestBody FkGongcheng fkGongcheng) throws IOException 
	{ 
		this.fkGongchengService.saveGongchengxin(fkGongcheng);	
		Map<String,Object> Allproject = new HashMap<String, Object>();   		  
		Allproject.put("success",true);  
		Allproject.put("msg", fkGongcheng.getProjectId()+",successfully saved");   
        return  (Allproject);		 
	}    
	*/
	
	/*
	 * 查询工程质量信息 2014-03-21
	 * */
	@RequestMapping(value="/seclet_gongcheng")
	@ResponseBody
	public Map<String, Object> handle2(@RequestParam("start") String start,
			@RequestParam("limit") String limit,			
			@RequestParam ("searchKeyword") String searchKeyword) throws IOException 
	{ 	
		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> mylist=this.fkGongchengService.selectGC(start, limit, searchKeyword);		 
        return (mylist); 
	}
	/*edit Gongchengxinxi
	 * 2013-12-20 @WW
	 * */
	@RequestMapping(value = "/get_gongchengDetail")
	@ResponseBody
	public Map<String, Object> getGongchengDetail (@RequestParam("projectSelectedId") String projectId) throws IOException{	
		FkGongcheng gongchengDetail = this.fkGongchengService.getGongchengDatail(projectId);
		Map<String, Object> mygongchengDetail =new HashMap<String, Object>();
		mygongchengDetail.put("success", true);
		mygongchengDetail.put("data",gongchengDetail);
		return mygongchengDetail;				
	}
	/*edit Gongchengxinxi
	 * 2013-12-20 @WW
	 * */
	@RequestMapping(value = "/get_gongchengDetail_E")
	@ResponseBody
	public Map<String, Object> getGongchengDetail_E (@RequestParam("projectSelectedId") String projectId) throws IOException{	
		FkGongcheng gongchengDetail = this.fkGongchengService.getGongchengDatail(projectId);
		Map<String, Object> mygongchengDetail =new HashMap<String, Object>();
		mygongchengDetail.put("success", true);
		mygongchengDetail.put("data",gongchengDetail);
		return mygongchengDetail;				
	}
	
	/*
	 * 编辑采样点数据 2014-03-26 @ww
	 * */
	@RequestMapping(value = "/update_Gongcheng",method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object> updateSample(@RequestBody FkGongcheng fkGongcheng) throws IOException 
	{ 
		this.fkGongchengService.updateGongcheng(fkGongcheng);	
		Map<String,Object> gongCheng = new HashMap<String, Object>();   		  
		gongCheng.put("success",true);  
		gongCheng.put("msg", "项目"+fkGongcheng.getProjectId()+",工程信息successfully updated");   
        return  (gongCheng);		 
	}   
	/*
	 * 删除工程质量信息 2014-03-21
	 * */
	@RequestMapping(value = "/del_gongchengByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void del_sampleByIds(@RequestParam("sampleIds") String[] sampleIds)
			throws IOException {
		System.out.println(sampleIds);
		this.fkGongchengService.del_gongchengByIds(sampleIds);	
	}
}
