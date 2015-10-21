package cn.edu.bnu.land.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.edu.bnu.land.model.SpatialData;
import cn.edu.bnu.land.service.SpatialDataService;

@Controller
public class MontAction {
	private String jsonString;
	private SpatialDataService ss;
		
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public SpatialDataService getSs() {
		return ss;
	}	
	@Autowired
	public void setSs(SpatialDataService ss) {
		this.ss = ss;
	}

	@RequestMapping(value = "/showMap.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String showMap()
			throws IOException {
		List<SpatialData> data = ss.show();
		String filePath = "";
	
		StringBuffer map;
		map = new StringBuffer("{\"data\":[");
		for (int i = 0; i < data.size(); i++) {
			filePath = data.get(i).getSpatialContent();
			try {
				FileReader fr = new FileReader(new File(filePath));
				BufferedReader br = new BufferedReader(fr);
						
				String temp = br.readLine();				

				br.close();
				if (i == data.size() - 1) {
					map.append(temp);
					map.append("]");
					continue;
				}
				map.append(temp);
				map.append(",");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		map.append("}");
		

		jsonString = "{\"totalCount\":" + data.size() + ",\"results\":[";
	
		for (int i = 0; i < 1; i++)
        {
			
			
			jsonString += map.toString() + ",";
			
        }
		jsonString += "]}";
		jsonString = jsonString.replace(",]}", "]}");

		System.out.println(jsonString);
		return jsonString;
	}
}
