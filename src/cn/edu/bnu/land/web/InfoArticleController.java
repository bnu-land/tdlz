package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.service.InfoArticleService;
import cn.edu.bnu.land.service.PublishService;

/*
 * ���@Controller���
 * 2013-8-29  @LF
 */
@Controller
public class InfoArticleController {
	private InfoArticleService infoArticleService;

	/*
	 * ���@Autowire��� 2013-8-29 @LF
	 */
	@Autowired
	public InfoArticleController(InfoArticleService infoArticleService) {
		this.infoArticleService = infoArticleService;
	}
	/*
	 * ���·���ҳ�档�����Ϣ�������?����Ϣ¼�뵽��ݿ���
	 * 2013-8-29 @LF
	 */
	@RequestMapping(value = "/add_infoArticle")
	// , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addInfoArticle(@RequestBody InfoArticle infoArticle)
			throws IOException {
		// //System.out.println("add_infoArticle :");
		this.infoArticleService.addInfoArticle(infoArticle);
		Map<String, Object> infoArticleResults = new HashMap<String, Object>();
		infoArticleResults.put("success", true);
		infoArticleResults.put("msg", infoArticle.getArticleName()
				+ ",successfully saved");
		return (infoArticleResults);
	}


	@RequestMapping(value = "/get_infoArticleDetail")
	@ResponseBody
	public Map<String, Object> getInfoArticleDetail (@RequestParam("articleSelectedId") String articleId) throws IOException{
		////System.out.println("now is in InfoArticleController ,to get articleSelectedId = " +articleId);
		InfoArticle articleDetail = this.infoArticleService.getArticleById(articleId);
		Map<String, Object> myInfoArticleDetail =new HashMap<String, Object>();
		myInfoArticleDetail.put("success", true);
		myInfoArticleDetail.put("data",articleDetail);
		////System.out.println("now is in InfoArticleController ,to get myInfoArticleDetail.getArticleName()) =" +myInfoArticleDetail.getArticleName());
		return myInfoArticleDetail;
		
		
	}
	
	
	/*
	 * get_pubArticleList  isrecycle=0 isdraft=0  channelId  start limit
	 * @LF   2013-11-27
	 */
	@RequestMapping(value = "/get_pubArticleList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPubArticleList(@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam("channelId") String channelId,
			@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate") String searchDate) throws IOException {

		 ////System.out.println("get_pubArticleList    searchKeyword: " +searchKeyword);
		 ////System.out.println("get_pubArticleList    searchDate: " +searchDate);
		// //System.out.println(limit);
		// //System.out.println();
		String isrecycle = "否" ;
		String isdraft= "否";
		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> myInfoArticleList = this.infoArticleService
				.listInfoArticle(start, limit, channelId, isrecycle, isdraft,searchKeyword,searchDate);
		////System.out.println(myInfoArticleList.get("total"));
		return (myInfoArticleList);

	}
	

	
	
	
	/* 
	 * get_draftArticleList   isdraft=1 isrecycle=0  channelId  start limit
	 * @LF   2013-11-27
	 */
	@RequestMapping(value = "/get_draftArticleList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDraftArticleList(@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam("channelId") String channelId,
			@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate") String searchDate) throws IOException {
		 ////System.out.println("get_draftArticleList    searchKeyword: " +searchKeyword);
		 ////System.out.println("get_draftArticleList    searchDate: " +searchDate);
		// ////System.out.println("time to getInfoArticleList");
		// //System.out.println(start);
		// //System.out.println(limit);
		// //System.out.println();
		String isrecycle = "否" ;
		String isdraft= "是";
		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> myInfoArticleList = this.infoArticleService
				.listInfoArticle(start, limit, channelId, isrecycle, isdraft,searchKeyword,searchDate);
		////System.out.println(myInfoArticleList.get("total"));
		return (myInfoArticleList);

	}
	
	
	/* 
	 * get_recycleArticleList   isrecycle=1 channelId  start limit
	 * @LF  2013-11-27
	 */
	@RequestMapping(value = "/get_recycleArticleList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRecycleArticleList(@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam("channelId") String channelId,
			@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate") String searchDate) throws IOException {
		 ////System.out.println("get_recArticleList    searchKeyword: " +searchKeyword);
		 ////System.out.println("get_recArticleList    searchDate: " +searchDate);
		String isrecycle = "是" ;
		String isdraft= "否";
		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> myInfoArticleList = this.infoArticleService
				.listInfoArticle(start, limit, channelId, isrecycle, isdraft,searchKeyword,searchDate);
		////System.out.println(myInfoArticleList.get("total"));
		return (myInfoArticleList);

	}
	
	
	
	/*
	 *   update_draftArticle
	 *  @LF 2013-11-27
	 */
	@RequestMapping(value = "/update_draftArticle",method=RequestMethod.POST)
	@ResponseBody
	public void updateDraftArticle(@RequestBody InfoArticle infoArticle)
			throws IOException {
		
		////System.out.println("update_draftArticle: "+infoArticle.getArticlePublishtime());
		this.infoArticleService.updateOneArticle(infoArticle);			
		
	}
	
	/*
	 *   update_recArticle
	 *  @LF 2013-11-28
	 */
	@RequestMapping(value = "/update_recArticle",method=RequestMethod.POST)
	@ResponseBody
	public void updateRecArticle(@RequestBody InfoArticle infoArticle)
			throws IOException {
		this.infoArticleService.updateOneArticle(infoArticle);			
		
	}
	
	/*
	 *    add_draftArticle
	 *  @LF 2013-11-28
	 */
	@RequestMapping(value = "/add_draftArticle",method=RequestMethod.POST)
	@ResponseBody
	public void addDraftArticle(@RequestBody InfoArticle infoArticle)
			throws IOException {
		infoArticle.setArticleIsdraft("是");
		infoArticle.setArticleIsrecycle("否");
		
		////System.out.println("update_draftArticle: "+infoArticle.getArticlePublishtime());
		this.infoArticleService.updateOneArticle(infoArticle);			
		
	}
	
	/*
	 *    update_draftArticle
	 *  @LF 2013-11-28
	 */
	@RequestMapping(value = "/add_pubArticle",method=RequestMethod.POST)
	@ResponseBody
	public void addPubArticle(@RequestBody InfoArticle infoArticle)
			throws IOException {
		
		Calendar ca = Calendar.getInstance();
     	Date now = ca.getTime();
     	////System.out.println("add_pubArticle: setArticlePublishtime is  " + now);
     	infoArticle.setArticlePublishtime(now);
		infoArticle.setArticleIsdraft("否");
		infoArticle.setArticleIsrecycle("否");
		////System.out.println("update_draftArticle: "+infoArticle.getArticlePublishtime());
		this.infoArticleService.updateOneArticle(infoArticle);			
		
	}
	
	

	/*
	 * update_articleToRecycle 
	 * @LF 2013-11-28 
	 */
	@RequestMapping(value = "/update_articleToRecycle")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateToRecFromPub(@RequestParam("articleIds") String[] articleIds)
			throws IOException {
		this.infoArticleService.updateRecTo1(articleIds);
			
		
	}
	
	/*
	 * update_articleToPubFromRec 
	 * @LF  2013-11-28 
	 */
	@RequestMapping(value = "/update_articleToPubFromRec")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateToPubFromRec(@RequestParam("articleIds") String[] articleIds)
			throws IOException {

		this.infoArticleService.updateRecTo0(articleIds);
			
		
	}
	/*
	 * update_articleToPubFromDraft 
	 *  @LF  2013-11-28
	 */
	@RequestMapping(value = "/update_articleToPubFromDraft")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateToPubFromDraft(@RequestParam("articleIds") String[] articleIds)
			throws IOException {
		

		this.infoArticleService.updateDraftTo0(articleIds);
			
		
	}
	
	/*
	 * update_articleToPubFromDraft 
	 * @LF  2013-11-28 
	 */
	@RequestMapping(value = "/del_articleByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delArticleByIds(@RequestParam("articleIds") String[] articleIds)
			throws IOException {

		this.infoArticleService.deleteArticleByIds(articleIds);
			
		
	}
	
}