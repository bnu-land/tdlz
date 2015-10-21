package cn.edu.bnu.land.web;

import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.model.SpatialData;
import cn.edu.bnu.land.service.SpatialDataService;

@Controller
public class SpatialDataAction{
	private SpatialDataService sds;
	private String spatialContent;
	private String projectID;
	
	
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public SpatialDataService getSds() {
		return sds;
	}
	@Autowired
	public void setSds(SpatialDataService sds) {
		this.sds = sds;
	}
	public String getSpatialContent() {
		return spatialContent;
	}
	public void setSpatialContent(String spatialContent) {
		this.spatialContent = spatialContent;
	}
	
	
	@RequestMapping(value = "/saveSpatial.action")
	@ResponseBody
	public String saveSpatial(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("spatialContent") String spatialContent,
			@RequestParam("projectID") String projectID
			){ 
		SpatialData sd = new SpatialData();
		String path;
		try
		{
			path = request.getSession().getServletContext().getRealPath("spatialData")+"/"+"json" 
		+ projectID + ".txt";

			FileOutputStream fout = new FileOutputStream(path);
			fout.write(spatialContent.getBytes());
			sd.setSpatialContent(path);
			sd.setProjectID(projectID);
			sds.createSpatialData(sd);
		
			fout.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "{success: true}";
		
		
		
	}
}
