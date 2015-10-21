package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.service.SecurityService;


@Controller
public class SecurityController {
    private SecurityService securityService;
    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }
    //取得当前登录的用户名
	@RequestMapping(value = "/get_loginUsername") //,method=RequestMethod.POST
	@ResponseBody
	public Map<String,Object> getLoginUsername() throws IOException 
	{		
		System.out.println("getLoginUsername");
		String username = this.securityService.getLoginUser();
		
//		this.securityService.addUser(user);		
		Map<String,Object> model = new HashMap<String, Object>();   		  
        model.put("success",true);  
        model.put("username",username);
           
        return  (model);		 
	}
	
	//根据用户角色跳转到不同的系统
	@RequestMapping(value="/dispatch")//, method = RequestMethod.GET)
	@ResponseBody
    public void dispatch() throws IOException {
		//String userRole = "JGXT";
		System.out.println("this is dispatch controller.");
        this.securityService.RedirectSystem();
    }
	
	//根据用户名称，得到用户权限列表
	@RequestMapping(value="/get_UserRightList")//, method = RequestMethod.GET)
	@ResponseBody
    public Boolean getUserRightList(@RequestParam("url") String url) throws IOException {
		//String userRole = "JGXT";
		//System.out.println("this is dispatch controller.");
        return this.securityService.getUserRightList(url);
    }	
}
