package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
import cn.edu.bnu.land.model.InfoLetter;
import cn.edu.bnu.land.service.InfoLetterService;



@Controller
public class InfoLetterController{
	private InfoLetterService infoLetterService;
	@Autowired
	public InfoLetterController(InfoLetterService infoLetterService) {
		this.infoLetterService = infoLetterService;
	}
	

	/*
	 * get_infoLetterList
	 * @LF 2013-12-2
	 */
	@RequestMapping(value = "/get_infoLetterList")//,method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String, Object> getInfoLetterList(@RequestParam("start") String start,
			@RequestParam("limit") String limit, @RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate") String searchDate )throws IOException 
	{ 		
		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> myInfoLetterList = this.infoLetterService.getInfoLetterList(start, limit,searchKeyword,searchDate);
		//System.out.println(myInfoLetterList.get("total"));
		return (myInfoLetterList);

		 
	} 
	
	
	
	/*
	 * del_letterByIds 
	 * @LF  2013-12-02 
	 */
	@RequestMapping(value = "/del_letterByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delLetterByIds(@RequestParam("letterIds") String[] letterIds)
			throws IOException {

		this.infoLetterService.deleteLetterByIds(letterIds);
			
		
	}
	
	/*
	 *    update_letter
	 *  @LF 2013-12-02
	 */
	@RequestMapping(value = "/update_letter")//,method=RequestMethod.POST)
	@ResponseBody
	public void updateLetter(@RequestBody InfoLetter infoLetter)
			throws IOException {
		
		Calendar ca = Calendar.getInstance();
     	Date now = ca.getTime();
     	//System.out.println("update_letter: setLetterReplytime is  " + now);
     	infoLetter.setLetterReplytime(now);
     	infoLetter.setLetterIsreply("是");
     	
		//System.out.println("updateLetter: "+infoLetter.getLetterReplytime());
		this.infoLetterService.updateLetter(infoLetter);			
		
	}
	
	
	
}