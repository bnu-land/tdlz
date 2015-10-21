package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
import cn.edu.bnu.land.model.InfoVote;
import cn.edu.bnu.land.model.InfoVoteoption;
import cn.edu.bnu.land.service.InfoVoteService;


@Controller
public class InfoVoteController{
	private InfoVoteService infoVoteService;
	@Autowired
	public InfoVoteController(InfoVoteService infoVoteService) {
		this.infoVoteService = infoVoteService;
	}
	
	
	
	/*
	 * ͶƱ�����ʾ
	 * ������get_voteResult
	 * ���ز���ͶƱѡ������List<InfoVoteoption> myVoteResult
	 * 20130903 @LF
	 */
	@RequestMapping(value = "/get_voteResult")//,method=RequestMethod.POST) 
	@ResponseBody 	
	public List<InfoVoteoption> getVoteResult(@RequestParam("voteId") String voteId )throws IOException 
	{ 			
		//System.out.println("time to getVoteResult"); 
		List<InfoVoteoption> myVoteResult = this.infoVoteService.getVoteResult(voteId);
		return (myVoteResult);

		 
	} 
	
	@RequestMapping(value = "/get_infoVoteList")//,method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object> getinfoVoteList(@RequestParam("start") String start,
			@RequestParam("limit") String limit,@RequestParam ("searchKeyword") String searchKeyword) throws IOException 
	{ 			
		//System.out.println("LF @InfoVoteController getInfoVoteList()"); 
		searchKeyword = Encoder.encode(searchKeyword);
		Map<String,Object> myVoteList = this.infoVoteService.listInfoVote(start, limit,searchKeyword);
		return (myVoteList);

		 
	} 
	
	/*
	 *   update_voteStateTo2
	 *  @LF 2013-12-12
	 */
	@RequestMapping(value = "/update_voteStateTo2",method=RequestMethod.POST)
	@ResponseBody
	public void updateVoteStateTo2(@RequestParam("voteIds") String[] voteIds)
			throws IOException {
		
		this.infoVoteService.updateVoteStateTo2(voteIds);		
		
	}
	
	/*
	 *   update_voteStateTo3
	 *  @LF 2013-12-12
	 */
	@RequestMapping(value = "/update_voteStateTo3",method=RequestMethod.POST)
	@ResponseBody
	public void updateVoteStateTo3(@RequestParam("voteIds") String[] voteIds)
			throws IOException {
		
		this.infoVoteService.updateVoteStateTo3(voteIds);		
		
	}
	
	
	/*
	 * del_voteByIds 
	 * @LF  2013-12-16  
	 */
	@RequestMapping(value = "/del_voteByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delVoteByIds(@RequestParam("voteIds") String[] voteIds)
			throws IOException {

		this.infoVoteService.deleteVoteByIds(voteIds);
			
		
	}
	
	
	/*
	 * add_voteoption 
	 * @LF  2013-12-16 
	 */
	@RequestMapping(value = "/add_voteoption")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void addVoteoption(@RequestBody InfoVoteoption infoVoteoption)
			throws IOException {
		
		infoVoteoption.setVoteId(0);
		infoVoteoption.setVoptionNumber("0");

		this.infoVoteService.addVoteoption(infoVoteoption);
			
		
	}
	/*
	 * add_voteoption 2
	 * @LF  2013-12-16 
	 */
	@RequestMapping(value = "/add_voteoption2")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void addVoteoption2(@RequestBody InfoVoteoption infoVoteoption)
			throws IOException {
		

		this.infoVoteService.addVoteoption(infoVoteoption);
			
		
	}
	
	
	
	/*
	 * del_voptionIds 
	 * @LF  2013-12-16 
	 */
	@RequestMapping(value = "/del_voptionIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delVoptionIds(@RequestParam("voptionIds") String[] voptionIds)
			throws IOException {

		this.infoVoteService.delVoptionIds(voptionIds);
			
		
	}
	
	
	/*
	 * add_vote 
	 * @LF  2013-12-16 
	 */
	@RequestMapping(value = "/add_vote")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void addVote(@RequestBody InfoVote infoVote)
			throws IOException {
		
		
		infoVote.setVoteState("待发布");
		
		//Calendar ca = Calendar.getInstance();
     	//Date now = ca.getTime();
     	//infoVote.setVoteStarttime(now);

		this.infoVoteService.addVote(infoVote);
			
		
	}
	
	
}