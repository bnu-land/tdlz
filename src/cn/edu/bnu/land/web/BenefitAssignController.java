package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.BenefitAlarmshou;
import cn.edu.bnu.land.model.BenefitAlarmtai;
import cn.edu.bnu.land.model.BenefitAssign;
import cn.edu.bnu.land.model.BenefitCanshushou;
import cn.edu.bnu.land.model.BenefitCanshutai;
import cn.edu.bnu.land.model.BenefitTrack;
import cn.edu.bnu.land.model.User;
import cn.edu.bnu.land.service.BenefitAssignService;
//import cn.edu.bnu.land.service.fileUploadService;


@Controller
public class BenefitAssignController {
    private BenefitAssignService benefitAssignService;
    @Autowired
    public BenefitAssignController(BenefitAssignService benefitAssignService) {
        this.benefitAssignService = benefitAssignService;
    }
	@RequestMapping(value = "/add_chart_pie",method=RequestMethod.POST)
	@ResponseBody 	
	public Map<String,Object> handle(@RequestBody BenefitAssign benefitAssign) throws IOException 
	{ 	
		System.out.println("ChartPie From Client : "+benefitAssign.getJe()); 
		this.benefitAssignService.addBenefitAssign(benefitAssign);		
		Map<String,Object> model = new HashMap<String, Object>();   		  
        model.put("success",true);  
        model.put("msg", benefitAssign.getJe()+",successfully saved");   
        return  (model);	
        
	} 
	
	
	//交易态势信息查询
	@RequestMapping(value = "/getBenefitList20", method = RequestMethod.GET)
	@ResponseBody 			
	public Map<String,Object> getTableTaiFind(@RequestParam("page") String page,@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam ("searchKeyword1") String searchKeyword1,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 
		searchKeyword1 = Encoder.encode(searchKeyword1);
		Map<String, Object> list = this.benefitAssignService.
				listTableTaiFind(page,start,limit,searchKeyword1,searchDate1,searchDate2);
		
		return (list);						
        //return  this.benefitAssignService.listTableTaiFind(start, limit);
	} 
	
	//交易态势表格删除信息
	@RequestMapping(value = "/getBenefitList26")	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delTableByIds(@RequestParam("tablexh") String[] tablexh)
			throws IOException {

		this.benefitAssignService.deleteTableByIds(tablexh);
				
	}
	
	//交易态势表格修改信息
	@RequestMapping(value = "/getBenefitList27")
	@ResponseBody
	public Map<String, Object> getTaishiFindEdit (@RequestParam("tablexh") String tablexh) throws IOException{	
		BenefitAssign benefitAssign = this.benefitAssignService.getTaishiFindEditForm(tablexh);
		Map<String, Object> result =new HashMap<String, Object>();
		result.put("success", true);
		result.put("data",benefitAssign);
		return result;				
	}
	
	//交易态势表格修改后更新信息
	@RequestMapping(value = "/getBenefitList28",method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object> updateTaishiFindFormEdit(@RequestBody BenefitAssign benefitAssign) throws IOException 
	{ 

		this.benefitAssignService.updateTaishiFormEdit(benefitAssign);
	
		Map<String,Object> model = new HashMap<String, Object>();   		  
		model.put("success",true);  

	    return  (model);
	} 
//	@RequestMapping(value = "/getBenefitList28",method=RequestMethod.POST) 
//	@ResponseBody 	
//	public Map<String,Object> updateTaishiFindFormEdit(@RequestBody BenefitAssign benefitAssign) throws IOException 
//	{ 
//		this.benefitAssignService.updateTaishiFormEdit(benefitAssign);	
//		Map<String,Object> model = new HashMap<String, Object>();   		  
//		model.put("success",true);  
//		model.put("msg", "第"+benefitAssign.getXh()+"条信息更新成功！");   
//      return  (model);
//	}   
	
	//交易态势中交易量柱状图、饼状图
	@RequestMapping(value = "/getBenefitList22") 
	@ResponseBody 	
	public List<BenefitAssign> getTaiVolume(@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 		
		BenefitAssign benefitAssign=new BenefitAssign(); 
		searchKeyword = Encoder.encode(searchKeyword);
		List<BenefitAssign> list = this.benefitAssignService.listChartPie(searchKeyword, searchDate1, searchDate2);
        return (List<BenefitAssign>) list;
	} 
		
	//交易态势中交易次数柱状图、饼状图
	@RequestMapping(value = "/getBenefitList24") 
	@ResponseBody 	
	public List<BenefitAssign> getTaiNumber(@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 		
		BenefitAssign benefitAssign=new BenefitAssign(); 
		searchKeyword = Encoder.encode(searchKeyword);
		List<BenefitAssign> list = this.benefitAssignService.listChartNumber(searchKeyword, searchDate1, searchDate2);
		return (List<BenefitAssign>) list;
	} 
	
	//交易态势中交易价格折线图
	@RequestMapping(value = "/getBenefitList21") 
	@ResponseBody 	
//	public List<BenefitAssign> listChartLine() throws IOException 
//	{ 	
//		
//		BenefitAssign benefitAssign=new BenefitAssign();
//
//		benefitAssign.setJe(10.00);
//		System.out.println("ChartLine From Client : "+benefitAssign.getJe()); 
//		List<BenefitAssign> list = this.benefitAssignService.listChartLine(benefitAssign);  		  
//           
//        return  list;
//
//	}	
	public List<BenefitAssign> getTaiPriceChartLine(@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 		
		BenefitAssign benefitAssign=new BenefitAssign(); 
		searchKeyword = Encoder.encode(searchKeyword);
//		Map<String, Object> list = (Map<String, Object>) this.benefitAssignService.
//				listChartLine(searchKeyword, searchDate1, searchDate2);
		List<BenefitAssign> list = this.benefitAssignService.listChartLine(searchKeyword, searchDate1, searchDate2);
        return (List<BenefitAssign>) list;

	}
	
	
	//交易态势预警信息饼状图、柱状图显示
	@RequestMapping(value = "/getBenefitList23") 
	@ResponseBody 	
	public List<BenefitAlarmtai> getlistChartTaishiAlarm(@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 		
		BenefitAlarmtai benefitAlarmtai=new BenefitAlarmtai();
		searchKeyword = Encoder.encode(searchKeyword);
		List<BenefitAlarmtai> list = this.benefitAssignService.listChartTaishiAlarm(searchKeyword,searchDate1, searchDate2);
        return (List<BenefitAlarmtai>) list;

	}
	
	//交易态势预警信息表格显示查询
	@RequestMapping(value = "/getBenefitList25") 
	@ResponseBody 	
	//public Map<String,Object> listTableTaiAlarm(@RequestParam("page") String page,@RequestParam("start") String start,@RequestParam("limit") String limit) throws IOException 
	//{ 	
	//	 	System.out.println("TaishiAlarm");	  
   //     return  this.benefitAssignService.listTableTaiAlarm(start, limit);
	//} 
	public Map<String,Object> getTableTaiAlarm(@RequestParam("page") String page,
			@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 
		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> list = this.benefitAssignService.listTableTaiAlarm(page,start,limit,searchKeyword,searchDate1,searchDate2);
		
		return (list);						
	}
	
	//交易态势预警信息参数更新
	@RequestMapping(value = "/getBenefitList29",method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object> updateTaishiAlarmCanshu(@RequestBody BenefitCanshutai benefitCanshutai) throws IOException 
	{ 

		this.benefitAssignService.updateTaishiAlarm(benefitCanshutai);
		Map<String,Object> model = new HashMap<String, Object>();   		  
		model.put("success",true);  

	    return  (model);
	} 
	
	//收益分配信息登记
	@RequestMapping(value = "/getBenefitList12",method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object> addShouyiRegister(HttpServletRequest request,
			HttpServletResponse response) throws IOException 
	{ 
		System.out.println("pjh:"+request.getParameter("pjh"));
//		System.out.println("jysj:"+request.getParameter("jysj"));
//		System.out.println("je:"+request.getParameter("je"));
		this.benefitAssignService.addShouRegister(request, response,"scsmj");
		
		Map<String,Object> model = new HashMap<String, Object>();   		  
		model.put("success",true);  
//		model.put("msg", request.getParameter("htbh")+",successfully saved");
//		model.put("msg", benefitTrack.getPjh()+",successfully saved"); 
//		model.put("msg", benefitTrack.getYt()+",successfully saved"); 
//		model.put("msg", benefitTrack.getZjlcf()+",successfully saved"); 
//		model.put("msg", benefitTrack.getZjlrf()+",successfully saved"); 
//		model.put("msg", benefitTrack.getJe()+",successfully saved");
//		model.put("msg", benefitTrack.getJbr()+",successfully saved"); 
//		model.put("msg", benefitTrack.getJysj()+",successfully saved");
//		model.put("msg", benefitTrack.getDzqr()+",successfully saved"); 
//		model.put("msg", benefitTrack.getScsmj()+",successfully saved");   

	    return  (model);      
				 
	} 
	
	//收益分配合同信息查询
	@RequestMapping(value = "/getBenefitList10") 
	@ResponseBody 	
	public Map<String,Object> getTableShouFind(@RequestParam("page") String page,@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam ("searchKeyword1") String searchKeyword1,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 
		searchKeyword1 = Encoder.encode(searchKeyword1);
		Map<String, Object> list = this.benefitAssignService.
				listTableShouFind(page,start,limit,searchKeyword1,searchDate1,searchDate2);
		
		return (list);						
	} 
	
	//收益分配表（合同表）表格信息删除
	@RequestMapping(value = "/getBenefitList14")	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delShouyiHetongTableByIds(@RequestParam("tablexh") String[] tablexh)
			throws IOException {

		this.benefitAssignService.deleteShouyiHetongTableByIds(tablexh);
				
	}
	
	//收益分配资金流动信息查询
	@RequestMapping(value = "/getBenefitList11") 
	@ResponseBody 	
	public Map<String,Object> getTableShouFindTrack(@RequestParam("page") String page,@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam ("searchKeyword1") String searchKeyword1,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 
		searchKeyword1 = Encoder.encode(searchKeyword1);
		Map<String, Object> list = this.benefitAssignService.
				listTableShouFindTrack(page,start,limit,searchKeyword1,searchDate1,searchDate2);
		
		return (list);						
	} 
	
	//收益分配表（资金流动列表）表格信息删除
	@RequestMapping(value = "/getBenefitList18")	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delShouyiZijinTableByIds(@RequestParam("tablexh") String[] tablexh)
			throws IOException {

		this.benefitAssignService.deleteShouyiZijinTableByIds(tablexh);
				
	}
	
	///收益分配预警信息饼状图、柱状图显示
	@RequestMapping(value = "/getBenefitList13") 
	@ResponseBody 	
	public List<BenefitAlarmshou> getlistChartShouyiAlarm(
			@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 		
		searchKeyword = Encoder.encode(searchKeyword);
		BenefitAlarmshou benefitAlarmshou=new BenefitAlarmshou(); 
		List<BenefitAlarmshou> list = this.benefitAssignService.listChartShouyiAlarm(searchKeyword,searchDate1, searchDate2);
        return (List<BenefitAlarmshou>) list;

	}
	
	//收益分配预警信息表格显示查询
	@RequestMapping(value = "/getBenefitList15") 
	@ResponseBody 
	public Map<String,Object> getTableShouAlarm(@RequestParam("page") String page,
			@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam ("searchKeyword") String searchKeyword,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 
		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> list = this.benefitAssignService.
				listTableShouAlarm(page,start,limit,searchKeyword,searchDate1,searchDate2);
		
		return (list);						
        //return  this.benefitAssignService.listTableTaiFind(start, limit);
	} 
	/*
	public Map<String,Object> listTableShouAlarm(@RequestParam("page") String page,@RequestParam("start") String start,@RequestParam("limit") String limit) throws IOException 
	{ 	
		 	System.out.println("shouyiAlarm");	  
        return  this.benefitAssignService.listTableShouAlarm(start, limit);
	} 
	*/
	//收益分配建设单位信息统计查询
	@RequestMapping(value = "/getBenefitList16") 
	@ResponseBody 	
	public Map<String,Object> getTableShouStatJian(@RequestParam("page") String page,@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam ("searchKeyword1") String searchKeyword1,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 
		searchKeyword1 = Encoder.encode(searchKeyword1);
		Map<String, Object> list = this.benefitAssignService.
				listTableShouStatJian(page,start,limit,searchKeyword1,searchDate1,searchDate2);
		
		return (list);						
	} 
	//收益分配农户信息统计查询
	@RequestMapping(value = "/getBenefitList17") 
	@ResponseBody 	
	public Map<String,Object> getTableShouStatNong(@RequestParam("page") String page,@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam ("searchKeyword1") String searchKeyword1,
			@RequestParam ("searchDate1") String searchDate1,
			@RequestParam ("searchDate2") String searchDate2) throws IOException 
	{ 
		searchKeyword1 = Encoder.encode(searchKeyword1);
		Map<String, Object> list = this.benefitAssignService.
				listTableShouStatNong(page,start,limit,searchKeyword1,searchDate1,searchDate2);
		
		return (list);						
	} 

	//收益分配预警信息参数更新
	@RequestMapping(value = "/getBenefitList19",method=RequestMethod.POST) 
	@ResponseBody 	
	public Map<String,Object> updateShouyiAlarmCanshu(@RequestBody BenefitCanshushou benefitCanshushou) throws IOException 
	{ 

		this.benefitAssignService.updateShouyiAlarm(benefitCanshushou);
		Map<String,Object> model = new HashMap<String, Object>();   		  
		model.put("success",true);  

	    return  (model);
	} 

}
