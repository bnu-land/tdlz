package cn.edu.bnu.land.web;
//Generated 2013-8-20 17:21:01 by Hibernate Tools 4.0.0
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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

import cn.edu.bnu.land.common.SpringContextHolder;
import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.ProjectAll;
import cn.edu.bnu.land.model.ProjectAllHome;
import cn.edu.bnu.land.model.DkMonitordata;
import cn.edu.bnu.land.model.DkMonitordataHome;
import cn.edu.bnu.land.model.ProjectYqProgress;
import cn.edu.bnu.land.service.MonitordataService;

/**
 * Home object for domain model class Prodb.
 * @see dao.Prodb
 * @author Hibernate Tools
 */

@Controller
public class MonitordataController {
	private MonitordataService monitordataService;
	@Autowired
	public MonitordataController(MonitordataService monitordataService) 
	{
		this.monitordataService  = monitordataService;
	}


	//添加监测数据
	@RequestMapping(value = "/add_monitordata",method=RequestMethod.POST)
	@ResponseBody
	public void addMonitordata(@RequestBody DkMonitordata dkMonitordata)
			throws IOException {
		this.monitordataService.addMonitordata(dkMonitordata);
	}

	//更新监测数据
	@RequestMapping(value = "/update_monitordataByIds",method=RequestMethod.POST)
	@ResponseBody
	public void updateMonitordata(@RequestBody DkMonitordata dkMonitordata)
			throws IOException {
		System.out.println("update_monitordata: "+dkMonitordata.getDkId());
		this.monitordataService.updateMonitordata(dkMonitordata);			
	}
	

	
	
	//删除监测数据
	@RequestMapping(value = "/del_monitordataByIds")
	@ResponseBody
	public void deleteMonitordataByIds(@RequestParam("recordIds") String[] recordIds)
			throws IOException {

		this.monitordataService.deleteMonitordatabyIds(recordIds);		

	}
	
	
	}
