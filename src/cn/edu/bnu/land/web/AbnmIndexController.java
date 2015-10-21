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
import cn.edu.bnu.land.model.AbnmIndex;
import cn.edu.bnu.land.model.InfoVoteoption;
import cn.edu.bnu.land.service.AbnmIndexService;

@Controller
public class AbnmIndexController {
	private AbnmIndexService abnmIndexService;

	@Autowired
	public AbnmIndexController(AbnmIndexService abnmIndexService) {
		this.abnmIndexService = abnmIndexService;
	}

	/*
	 * @Location:store:abnmIndexStore
	 * 
	 * @Description:通过dkId获得相应的交易预警指标数值，
	 * 
	 * @Params:String start, String limit, String dkId
	 * 
	 * @Return: Map<String, Object> myAbwsList
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140225
	 */
	@RequestMapping(value = "/get_abiList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAbiList(@RequestParam("start") String start,
			@RequestParam("limit") String limit,
			@RequestParam("dkId") String dkId) throws IOException {

		// searchKeyword = Encoder.encode(searchKeyword);
		Map<String, Object> myAbwsList = this.abnmIndexService.getAbiList(
				start, limit, dkId);
		// System.out.println("/get_abwsList");
		return (myAbwsList);

	}

	/*
	 * @Location:点击交易预警地块信息，显示出该地块的详细信息，以及相应的指标信息,增加指标
	 * 
	 * @Description:增加新的指标系数
	 * 
	 * @Params:AbnmIndex abnmIndex
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140306
	 */
	@RequestMapping(value = "/add_abi")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void addAbi(@RequestBody AbnmIndex abnmIndex) throws IOException {

		this.abnmIndexService.addAbi(abnmIndex);

	}

	/*
	 * @Location:点击交易预警地块信息，显示出该地块的详细信息，以及相应的指标信息，更新指标
	 * 
	 * @Description:更新指标系数
	 * 
	 * @Params:AbnmIndex abnmIndex
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140306
	 */
	@RequestMapping(value = "/update_abi")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateAbi(@RequestBody AbnmIndex abnmIndex) throws IOException {

		this.abnmIndexService.updateAbi(abnmIndex);

	}

	/*
	 * @Location:点击交易预警地块信息，显示出该地块的详细信息，以及相应的指标信息，删除指标
	 * 
	 * @Description:通过指标的id，删除指标系数
	 * 
	 * @Params:AbnmIndex abnmIndex
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140304
	 */
	@RequestMapping(value = "/delete_abiByIds")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void deleteAbiByIds(@RequestParam("ids") String[] ids)
			throws IOException {

		this.abnmIndexService.deleteAbiByIds(ids);

	}

}