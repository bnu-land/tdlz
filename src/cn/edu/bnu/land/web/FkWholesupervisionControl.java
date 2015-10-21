package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.service.FkWholesupervisionService;

@Controller
public class FkWholesupervisionControl {
	private FkWholesupervisionService fkWholesupervisionService;
	@Autowired
    public FkWholesupervisionControl(FkWholesupervisionService fkWholesupervisionService){
		this.fkWholesupervisionService=fkWholesupervisionService;
	}
	/*2014-03-02 @WW
	    * 查询预警信息
	    * */    
		@RequestMapping(value = "/findYJinformation")//,method=RequestMethod.POST) 
		@ResponseBody 	
		public Map<String, Object> handle1(@RequestParam("start") String start,
				@RequestParam("limit") String limit,			
				@RequestParam ("searchKeyword") String searchKeyword) throws IOException 
		{ 	
			System.out.println("findYJinformation"); 
			searchKeyword = Encoder.encode(searchKeyword);
			Map<String, Object> mylist=this.fkWholesupervisionService.select(start, limit, searchKeyword);		 
	        return (mylist); 
		}
}
