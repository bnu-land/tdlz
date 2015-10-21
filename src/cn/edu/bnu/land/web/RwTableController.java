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

import cn.edu.bnu.land.model.FkTasktable;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.model.RwTable;
import cn.edu.bnu.land.service.RwTableService;
@Controller
public class RwTableController {
	private RwTableService rwTableService;
	@Autowired
	public RwTableController(RwTableService rwTableService){
		this.rwTableService=rwTableService;
	}
	/*Add task function
	 * 2013-12-21 @WW
	 * */
	 @RequestMapping(value = "/add_task")//,method=RequestMethod.POST) 
	 @ResponseBody 	
		public Map<String,Object> handle2(@RequestBody RwTable rwTable) throws IOException 
		{ 
			System.out.println("add_task was called"); 
			this.rwTableService.addtask(rwTable);	
			Map<String,Object> Alltask = new HashMap<String, Object>();   		  
			Alltask .put("success",true);  
			Alltask .put("msg", rwTable.getRwId()+",successfully saved");   
	        return  (Alltask);		 
		} 
	 
	/*Select task function
	 * 2013-12-21 @WW
	 * */
	@RequestMapping(value = "/findtask")//,method=RequestMethod.POST) 
	@ResponseBody 	
	public List<RwTable> handle1() throws IOException 
	{ 
		
		System.out.println("findtask "); 
		List<RwTable> mylist=this.rwTableService.select();
		for(RwTable rwTable:mylist){
			System.out.println("findtask:"+rwTable.getRwId());
		}   
        return  (mylist); 
	} 
	/*
	 * Edit project get projectDetail
	 *  @WW  2013-12-19 
	 * */
	@RequestMapping(value = "/get_rwTableDetail")
	@ResponseBody
	public Map<String, Object> getprojectDetail (@RequestParam("projectSelectedId") String rwId) throws IOException{
		
		RwTable projectDetail = this.rwTableService.getRwTableById(rwId);
		Map<String, Object> rwTableDetail =new HashMap<String, Object>();
		rwTableDetail.put("success", true);
		rwTableDetail.put("data",projectDetail);
		return rwTableDetail;	
	}

	/*
	 * delete task 
	 * @WW  2013-12-19 
	 */
	@RequestMapping(value = "/del_taskByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void del_taskByIds(@RequestParam("taskIds") String[] taskIds)
			throws IOException {
		System.out.println(taskIds);
		this.rwTableService.del_projectByIds(taskIds);	
	}
	
	
}
