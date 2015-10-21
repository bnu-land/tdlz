package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.service.MapService;

@Controller
public class MapController {
	private MapService mapService;

	@Autowired
	public MapController(MapService mapService) {
		this.mapService = mapService;
	}

	// 获取所有地图资源
	@RequestMapping(value = "/map_getMapResources")
	// ,method=RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getMapResouces(
			@RequestParam("searchKeyword") String searchKeyword)
			throws IOException {
		System.out.println("searchKeyword : " + searchKeyword);
		searchKeyword = Encoder.encode(searchKeyword);
		return this.mapService.getMapResources(searchKeyword);
	}

	// 由图层index名称获取图层url
	@RequestMapping(value = "/map_getMapURLByName")
	// ,method=RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getMapURLByName() throws IOException {
		return this.mapService.getMapURLByName();
	}
}
