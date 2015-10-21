package cn.edu.bnu.land.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.service.EvaluateService;

@Controller
public class EvalutionAction{
	private String projectID;
	private String jsonString;
	private EvaluateService es;
	
	
	public String getProjectID() {
		return projectID;
	}


	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}


	public String getJsonString() {
		return jsonString;
	}


	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}


	public EvaluateService getEs() {
		return es;
	}

	@Autowired
	public void setEs(EvaluateService es) {
		this.es = es;
	}

	@RequestMapping(value = "/finalEvaluation.action", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String finalEvaluation(
			@RequestParam(value = "projectID", required = false) String projectID
			){
		if (projectID == null) {
			projectID = this.projectID;
		}else {
			this.projectID = projectID;
		}
		jsonString = "{\"totalCount\":" + 1 + ",\"results\":[{" +
		es.evaluateSuggest(projectID) + "," + es.evaluateCQGC(projectID) +
		"," + es.evaluateWarm(projectID) + "}]}";
		System.out.println(jsonString);
		return jsonString;
	}
}
