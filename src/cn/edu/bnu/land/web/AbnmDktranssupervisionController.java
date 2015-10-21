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

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.AbnmDktranssupervision;
import cn.edu.bnu.land.model.FkDikuai;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.service.AbnmDktranssupervisionService;
import cn.edu.bnu.land.service.AbnmIndexService;

@Controller
public class AbnmDktranssupervisionController {
	private AbnmDktranssupervisionService abnmDktranssupervisionService;
	private AbnmIndexService abnmIndexService;

	@Autowired
	public AbnmDktranssupervisionController(
			AbnmDktranssupervisionService abnmDktranssupervisionService) {
		this.abnmDktranssupervisionService = abnmDktranssupervisionService;

	}

	/*
	 * @Location: 面板：交易风险预警；store：abnmDkTransSupervisionStore,按钮：“搜索”功能
	 * 
	 * @Description:获得交易风险预警的地块信息列表
	 * 
	 * @Params:String start, String limit, String searchKeyword, String
	 * searchDate, String province, String city, String county
	 * 
	 * @Return:Map<String, Object> myAbwsList
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	@RequestMapping(value = "/get_abdktsList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAbdktsList(
			@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("searchDate") String searchDate,
			@RequestParam("province") String province,
			@RequestParam("city") String city,
			@RequestParam("county") String county) throws IOException {

		searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> myAbwsList = this.abnmDktranssupervisionService
				.getAbdktsList(start, limit, searchKeyword, searchDate,
						province, city, county);
		// System.out.println("*************/get_abdktsList");
		return (myAbwsList);

	}

	/*
	 * @Location:面板：信息录入，按钮：弹出信息录入，填写信息后点击确定
	 * 
	 * @Description: 增加交易风险预警的地块信息，同时增加相应的预警指标信息
	 * 
	 * @Params: AbnmDktranssupervision abdkts
	 * 
	 * @Return: Map<String, Object> abdktsResults
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140226
	 */
	@RequestMapping(value = "/add_abdkts")
	// , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAbdkts(
			@RequestBody AbnmDktranssupervision abdkts) throws IOException {
		System.out.println("add_abdkts :" + abdkts);
		this.abnmDktranssupervisionService.addAbdkts(abdkts);
		Map<String, Object> abdktsResults = new HashMap<String, Object>();
		abdktsResults.put("success", true);
		abdktsResults.put("msg", abdkts.getAbdkId() + ",successfully saved");
		return (abdktsResults);
	}

	/*
	 * @Location: 面板：交易风险预警；按钮：“批量删除”，每一行的“删除”图标
	 * 
	 * @Description:通过信息id删除整条交易预警的地块信息和相应的预警指标信息
	 * 
	 * @Params: String[] ids
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140304
	 */
	@RequestMapping(value = "/delete_abdkByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void deleteAbdkByIds(@RequestParam("ids") String[] ids)
			throws IOException {

		this.abnmDktranssupervisionService.delete_abdkByIds(ids);

	}

	/*
	 * @Location:面板：交易风险预警，按钮：点击一条记录，弹出窗口，修改信息后点击“确定”
	 * 
	 * @Description:更新地块的交易风险预警信息
	 * 
	 * @Params:AbnmDktranssupervision abdkts
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140225
	 */
	@RequestMapping(value = "/update_abdkts")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateAbws(@RequestBody AbnmDktranssupervision abdkts)
			throws IOException {

		this.abnmDktranssupervisionService.updateAbdkts(abdkts);

	}

	/*
	 * @Location: 面板：交易风险预警；按钮：交易风险预警
	 * 
	 * @Description: 计算交易风险指数，同时使用freemarker生成doc和html
	 * 
	 * @Params: String abdkId
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140408
	 */
	@RequestMapping(value = "/calculate_abdkById")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void calculateAbdkById(@RequestParam("abdkId") String abdkId)
			throws IOException {

		this.abnmDktranssupervisionService.updateAbdkAfterCal(abdkId);
		

	}

	
	
	
	
	
	@RequestMapping(value = "/abnmGetPublicProject")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public List<PublicProject> abnmGetPublicProject()
			throws IOException {

		List<PublicProject> myAbnmGetPublicProjectt = this.abnmDktranssupervisionService.abnmGetPublicProject();
		
		return myAbnmGetPublicProjectt;
	}
	
	
	
	
	@RequestMapping(value = "/abnmGetDikuai")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public List<FkDikuai> abnmGetDikuai(@RequestParam("projectId") String projectId)
			throws IOException {

		List<FkDikuai> myAbnmGetDikuai = this.abnmDktranssupervisionService.abnmGetDikuai(projectId);
		
		return myAbnmGetDikuai;
	}
	
}