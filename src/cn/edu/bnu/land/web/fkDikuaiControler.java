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
import cn.edu.bnu.land.model.FkDikuai;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.service.fkDikuaiService;

@Controller
public class fkDikuaiControler {
	private fkDikuaiService FkDikuaiService;
	 @Autowired
	 public fkDikuaiControler(fkDikuaiService FkDikuaiService){
		 this.FkDikuaiService=FkDikuaiService;
	 }
	 /*2013-12-19 @WW
	    * select project
	    * */    
		@RequestMapping(value = "/selectDikuai")//,method=RequestMethod.POST) 
		@ResponseBody 	
		public Map<String, Object> handle1(@RequestParam("start") String start,
				@RequestParam("limit") String limit,			
				@RequestParam ("searchKeywordName") String searchKeywordName) throws IOException 
		{ 	
			System.out.print("selectDikuai");
			searchKeywordName = Encoder.encode(searchKeywordName);
			Map<String, Object> mylist=this.FkDikuaiService.select(start, limit, searchKeywordName);		 
	        return (mylist); 
		}	
		 /*
	     * add fukenfikuai
	     * */
	    @RequestMapping(value = "/add_projectDikuai",method=RequestMethod.POST) 
		@ResponseBody 	
		public Map<String,Object> add_projectDikuai(@RequestBody FkDikuai fkDikuai) throws IOException 
		{ 
			System.out.println("Username From Client : "+fkDikuai.getLandId()); 
			this.FkDikuaiService.saveDikuai(fkDikuai);	
			Map<String,Object> Allproject = new HashMap<String, Object>();   		  
			Allproject.put("success",true);  
			Allproject.put("msg", fkDikuai.getLandId()+",successfully saved");   
	        return  (Allproject);		 
		} 
	    /*
	     * get dikuai 信息
	     * */
	    @RequestMapping(value = "/get_dikuaiDetail")//,method=RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> getDikuai(@RequestParam("dikuaiSelectedId") String landId)
				throws IOException {
			System.out.print("希望获取到地块信息"+landId);
			FkDikuai result=this.FkDikuaiService.getdikuaiById(landId);	
			Map<String, Object> mydikuaiDetail =new HashMap<String, Object>();
			mydikuaiDetail.put("success", true);
			mydikuaiDetail.put("data",result);
			return mydikuaiDetail;
		}
		/*
		 * 更新地块信息
		 * */
	    @RequestMapping(value = "/update_projectDikuai",method=RequestMethod.POST) 
		@ResponseBody 	
		public Map<String,Object> updateDikuai(@RequestBody FkDikuai fkDikuai) throws IOException 
		{ 
			this.FkDikuaiService.updateDikuai(fkDikuai);	
			Map<String,Object> dikuai = new HashMap<String, Object>();   		  
			dikuai.put("success",true);  
			dikuai.put("msg", fkDikuai.getLandId()+",successfully updated");   
	        return  (dikuai);		 
		}  
	    
	    /*
		 * delete task 
		 * @WW  2013-12-19 
		 */
		@RequestMapping(value = "/del_dikuaiByIds")
		// ,method=RequestMethod.POST)
		@ResponseBody
		public void del_taskByIds(@RequestParam("projectIds") String[] taskIds)
				throws IOException {
			System.out.println(taskIds);
			this.FkDikuaiService.del_dikuaiByIds(taskIds);	
		}
}
