package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.service.InfoCommentService;



@Controller
public class InfoCommentController{
	private InfoCommentService infoCommentService;
	@Autowired
	public InfoCommentController(InfoCommentService infoCommentService) {
		this.infoCommentService = infoCommentService;
	}
	

	@RequestMapping(value = "/get_infoCommentList",method=RequestMethod.GET) 
	@ResponseBody 	
	public Map<String, Object> getInfoCommentList(@RequestParam("start") String start,
			@RequestParam("limit") String limit,@RequestParam("articleId") String articleId) throws IOException 
	{ 			
		
		//System.out.println("LF @InfoCommentController getInfoCommentList");
		 //System.out.println("LF @InfoCommentController getInfoCommentList"+articleId);
		 //System.out.println("LF @InfoCommentController getInfoCommentList"+limit);
//		 //System.out.println();
		Map<String, Object> myInfoCommentList =this.infoCommentService.listInfoComment(start, limit,articleId);
		//System.out.println(myInfoCommentList.get("total"));
		return (myInfoCommentList);
		 
	} 
	
	
	/*
	 * update_comment 
	 * @LF  2013-12-4 
	 */
	@RequestMapping(value = "/update_comment")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateComment(@RequestParam("commentIds") String[] commentIds)
			throws IOException {

		this.infoCommentService.updateComment(commentIds);
			
		
	}

	
	
}