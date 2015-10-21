package cn.edu.bnu.land.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.Evaluationindextable;
import cn.edu.bnu.land.service.EvaluationindextableService;

@Controller
public class EvaluationindextableController {

	private EvaluationindextableService evaluationindextableService;
	@Autowired
	public EvaluationindextableController(EvaluationindextableService evaluationindextableService)
    {
    	this.evaluationindextableService=evaluationindextableService;
    }
    @RequestMapping(value="/add_index",method=RequestMethod.POST)
    @ResponseBody 
    public Map<String,Object> handle1(@RequestBody Evaluationindextable evaluationindextable) throws IOException
    {
    	System.out.println("add index  was called");
    	this.evaluationindextableService.add_evaluation(evaluationindextable);
		
    	Map<String,Object> model = new HashMap<String, Object>();   		  
        model.put("success",true); 
        model.put("msg",evaluationindextable.getProjectName()+"add index");
		return (model);	
    }
    /*2013-12-19 @WW
     * 测试评价
     * */    
    @RequestMapping(value = "/evaluate")//,method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object>  handle2(@RequestParam("organicmatter") float organicmatter,
			@RequestParam("soiltexture") float  soiltexture, @RequestParam("phvalue") float  phvalue,	
			@RequestParam("soilThickness") float  soilThickness,@RequestParam("soilmoisture") float  soilmoisture,
			@RequestParam("totalnitrogen") float  totalnitrogen,@RequestParam("totalphosphorus") float  totalphosphorus,
			@RequestParam ("totalpotassium") float  totalpotassium) throws IOException 
	{ 	
		System.out.println("evaluate");
		String  respText="";
		if (phvalue < 4.5 && phvalue > 7.5) {
			respText="土壤酸碱性不符合条件";
			System.out.print(respText);
		} else if (soilThickness < 40) {
			respText="土层厚度小于40cm，不满足条件";
			System.out.print(respText);
		}else if (organicmatter < 6) {
			respText="土壤有机质含量不合格";
			System.out.print(respText);
		}else if (soiltexture > 0.5) {
			respText="土壤质地不合格";
			System.out.print(respText);
		}else if (soilmoisture<0.15 && soilmoisture > 0.8) {
			respText="土壤过于干燥或潮湿";
			System.out.print(respText);
		}else if (totalnitrogen <0.5) {
			respText="全氮含量过低";
			System.out.print(respText);
		}else if (totalphosphorus<0.2) {
			respText="全磷含量过低";
			System.out.print(respText);
		}else if (totalpotassium<5) {
			respText="全钾含量过低";
			System.out.print(respText);
		}else {
			respText="合格";
			System.out.print(respText);
		}
		// 前台转换代码 respText =encodeURIComponent(respText);
		//respText=new String(respText.getBytes("UTF-8"),"ISO8859_1");
		//respText = new String(respText.getBytes("iso-8859-1"), "UTF-8" );		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("evaluateResult", respText);	
		return map;
	}   
}
