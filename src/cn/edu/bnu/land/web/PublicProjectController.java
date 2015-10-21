package cn.edu.bnu.land.web;

import java.io.IOException;
import java.nio.charset.Charset;
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
import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.InfoChannel;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.service.PublicProjectService;

@Controller
public class PublicProjectController {	
	private PublicProjectService publicProjectService;
    @Autowired
    public PublicProjectController(PublicProjectService publicProjectService) {
        this.publicProjectService = publicProjectService;
    }
    /*
     * add project
     * */
    @RequestMapping(value = "/add_project",method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object> handle2(@RequestBody PublicProject publicProject) throws IOException 
	{ 
		//System.out.println(" add project was called"); 
		System.out.println("Username From Client : "+publicProject.getProjectId()); 
		this.publicProjectService.addProject(publicProject);	
		Map<String,Object> Allproject = new HashMap<String, Object>();   		  
		Allproject.put("success",true);  
		Allproject.put("msg", publicProject.getProjectname()+",successfully saved");   
        return  (Allproject);		 
	}    
   /*2013-12-19 @WW
    * select project
    * */    
	@RequestMapping(value = "/findUser")//,method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String, Object> handle1(@RequestParam("start") String start,
			@RequestParam("limit") String limit,			
			@RequestParam ("searchKeywordName") String searchKeyword,
			@RequestParam ("searchKeywordID") String searchKeywordID,
			@RequestParam ("searchKeywordSite") String searchKeywordSite,
			@RequestParam ("searchKeywordStarttime") String searchKeywordStarttime
			) throws IOException 
	{ 	
		System.out.println("finduser"); 
		searchKeyword = Encoder.encode(searchKeyword);
		searchKeywordSite = Encoder.encode(searchKeywordSite);
		Map<String, Object> mylist=this.publicProjectService.select(start, limit, searchKeyword, searchKeywordID, searchKeywordSite, searchKeywordStarttime);		 
        return (mylist); 
	}
	/*2013-12-19 @WW
	    * 验收申请选择项目名称
	    * */ 
		@RequestMapping (value = "/ApplyListname") 
		@ResponseBody 
		public List<PublicProject> ApplyListname()throws IOException
		{
			List<PublicProject> myList= this.publicProjectService.applyListname();
			return (myList);
		}
		/*2013-12-19 @WW
		    * 验收申请选择项目名称
		    * */ 
			@RequestMapping (value = "/ApplyListId") 
			@ResponseBody 
			public List<PublicProject> ApplyListId(@RequestParam ("projectId") String  projectId)throws IOException
			{
				List<PublicProject> myList= this.publicProjectService.applyListId(projectId);
				return (myList);
			}
	/*
	 * delete project 
	 * @WW  2013-12-19 
	 */
	@RequestMapping(value = "/del_projectByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void del_projectByIds(@RequestParam("projectIds") String[] projectIds)
			throws IOException {
		//System.out.println(projectIds);
		this.publicProjectService.del_projectByIds(projectIds);	
	}
	/*
	 * Edit project get projectDetail
	 *  @WW  2013-12-19 
	 * */
	@RequestMapping(value = "/get_projectDetail")
	@ResponseBody
	public Map<String, Object> getprojectDetail (@RequestParam("projectSelectedId") String projectId) throws IOException{
		System.out.println("now is FindUser,to get projectSelectedId = " +projectId);
		PublicProject projectDetail = this.publicProjectService.getProjectById(projectId);
		Map<String, Object> myprojectDetail =new HashMap<String, Object>();
		myprojectDetail.put("success", true);
		myprojectDetail.put("data",projectDetail);
		//System.out.println("now is in InfoArticleController ,to get myInfoArticleDetail.getArticleName()) =" +myInfoArticleDetail.getArticleName());		
		return myprojectDetail;
	
	}
	
	
}
