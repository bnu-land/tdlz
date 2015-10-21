package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.model.InfoChannel;

import cn.edu.bnu.land.service.InfoChannelService;



@Controller
public class InfoChannelController {
    private InfoChannelService infoChannelService;


    @Autowired
    public InfoChannelController(InfoChannelService infoChannelService) {
        this.infoChannelService = infoChannelService;
    }
	@RequestMapping(value = "/get_infoChannelList")//,method=RequestMethod.POST) 
	@ResponseBody 	
	public List<InfoChannel> getInfoChannelList() throws IOException 
	{ 	
		//System.out.println("time to getChannelList"); 
		List<InfoChannel> myInfoChannelList = this.infoChannelService.listInfoChannel();
		return myInfoChannelList;

		 
	} 
}
