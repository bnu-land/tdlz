package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.CardPieceDAO;
import cn.edu.bnu.land.model.DemoLogDAO;
import cn.edu.bnu.land.model.DemolitionLog;
import cn.edu.bnu.land.model.Projection;
import cn.edu.bnu.land.model.ProjectionDAO;
import cn.edu.bnu.land.model.Suggestion;
import cn.edu.bnu.land.model.SuggestionDAO;
import cn.edu.bnu.land.model.WarmLogs;
import cn.edu.bnu.land.model.WarmLogsDAO;
import cn.edu.bnu.land.service.EvaluateService;
@Service
public class EvaluateServiceImpl implements EvaluateService{

	SuggestionDAO sDAO;
	DemoLogDAO dDAO;
	WarmLogsDAO wDAO;
	ProjectionDAO pDAO;
	CardPieceDAO cDAO;
	
	
	public SuggestionDAO getsDAO() {
		return sDAO;
	}
	@Autowired
	public void setsDAO(SuggestionDAO sDAO) {
		this.sDAO = sDAO;
	}

	public DemoLogDAO getdDAO() {
		return dDAO;
	}
	@Autowired
	public void setdDAO(DemoLogDAO dDAO) {
		this.dDAO = dDAO;
	}

	public WarmLogsDAO getwDAO() {
		return wDAO;
	}
	@Autowired
	public void setwDAO(WarmLogsDAO wDAO) {
		this.wDAO = wDAO;
	}

	public ProjectionDAO getpDAO() {
		return pDAO;
	}
	@Autowired
	public void setpDAO(ProjectionDAO pDAO) {
		this.pDAO = pDAO;
	}

	public CardPieceDAO getcDAO() {
		return cDAO;
	}
	@Autowired
	public void setcDAO(CardPieceDAO cDAO) {
		this.cDAO = cDAO;
	}

	public String evaluateSuggest(String projectID) {
		// TODO Auto-generated method stub
		List<Suggestion> data = sDAO.findByProjectID(projectID);
		int peopleNumber = cDAO.findByProjectID(projectID).size();
		float maxPointBC = 0;
		float minPointBC = 5;
		float avgPointBC;
		float maxPointAZ = 0;
		float minPointAZ = 5;
		float avgPointAZ;
		float maxPointCQ = 0;
		float minPointCQ = 5;
		float avgPointCQ;
		float maxPointQT = 0;
		float minPointQT = 5;
		float avgPointQT;
		int suggestionNumber = data.size();
		
		String json = "";
		int numBC = 0;
		float sumBC = 0;
		int numAZ = 0;
		float sumAZ = 0;
		int numCQ = 0;
		float sumCQ = 0;
		int numQT = 0;
		float sumQT = 0;
		for (Suggestion s : data) {
			if (s.getCategory().trim().equals("补偿意见")){
				if (maxPointBC < s.getGrade()) {
					maxPointBC = s.getGrade();
				}
				if (minPointBC > s.getGrade()){
					minPointBC = s.getGrade();
				}
				sumBC += s.getGrade();
				numBC++;
			}else if (s.getCategory().trim().equals("安置意见")){
				if (maxPointAZ < s.getGrade()) {
					maxPointAZ = s.getGrade();
				}
				if (minPointAZ > s.getGrade()){
					minPointAZ = s.getGrade();
				}
				sumAZ += s.getGrade();
				numAZ++;	
			}else if (s.getCategory().trim().equals("拆迁意见")){
				if (maxPointCQ < s.getGrade()) {
					maxPointCQ = s.getGrade();
				}
				if (minPointCQ > s.getGrade()){
					minPointCQ = s.getGrade();
				}
				sumCQ += s.getGrade();
				numCQ++;	
			}else{
				if (maxPointQT < s.getGrade()) {
					maxPointQT = s.getGrade();
				}
				if (minPointQT > s.getGrade()){
					minPointQT = s.getGrade();
				}
				sumQT += s.getGrade();
				numQT++;
			}
			
		}
		avgPointBC = sumBC/numBC;
		avgPointAZ = sumAZ/numAZ;
		avgPointCQ = sumCQ/numCQ;
		avgPointQT = sumQT/numQT;
		json += "\"peopleNumber\": " + peopleNumber + ",\"suggestionNumber\":" +
				suggestionNumber + ",\"maxPointBC\":" + maxPointBC + ",\"minPointBC\":" +
				minPointBC + ",\"avgPointBC\":" + avgPointBC + ",\"maxPointAZ\":" + 
				maxPointAZ + ",\"minPointAZ\":" + minPointAZ + ",\"avgPointAZ\":" + avgPointAZ
				+ ",\"maxPointCQ\":" + maxPointCQ + ",\"minPointCQ\":" + minPointCQ + 
				",\"avgPointCQ\":" + avgPointCQ + ",\"maxPointQT\":" + maxPointQT + ",\"minPointQT\":" 
				+ minPointQT + ",\"avgPointQT\":" + avgPointQT + ",\"totalPointYJ\":" + 
				(avgPointAZ + avgPointBC + avgPointCQ + avgPointQT)*5;
		
		return json;
	}

	public String evaluateCQGC(String projectID) {
		// TODO Auto-generated method stub
		List<DemolitionLog> data = dDAO.findByProjectID(projectID);
		int houseNumber = cDAO.findByProjectID(projectID).size();
		float maxPointGZ = 0;
		float minPointGZ = 100;
		float avgPointGZ;
		float maxPointXZ = 0;
		float minPointXZ = 100;
		float avgPointXZ;
		float maxPointGT = 0;
		float minPointGT = 100;
		float avgPointGT;
		float maxPointNH = 0;
		float minPointNH = 100;
		float avgPointNH;
		int logNumber = data.size();
		
		String json = "";

		float sumGZ = 0;

		float sumXZ = 0;

		float sumGT = 0;
	
		float sumNH = 0;
		for (DemolitionLog s : data) {
		
			if (maxPointGZ < Float.valueOf(s.getPointGZ())) {
				maxPointGZ = Float.valueOf(s.getPointGZ());
			}
			if (minPointGZ > Float.valueOf(s.getPointGZ())){
				minPointGZ = Float.valueOf(s.getPointGZ());
			}
			sumGZ += Float.valueOf(s.getPointGZ());


			if (maxPointXZ < Float.valueOf(s.getPointXZ())) {
				maxPointXZ = Float.valueOf(s.getPointXZ());
			}
			if (minPointXZ > Float.valueOf(s.getPointXZ())){
				minPointXZ = Float.valueOf(s.getPointXZ());
			}
			sumXZ += Float.valueOf(s.getPointXZ());


			if (maxPointGT < Float.valueOf(s.getPointGT())) {
				maxPointGT = Float.valueOf(s.getPointGT());
			}
			if (minPointGT > Float.valueOf(s.getPointGT())){
				minPointGT = Float.valueOf(s.getPointGT());
			}
			sumGT += Float.valueOf(s.getPointGT());
	

			if (maxPointNH < Float.valueOf(s.getPointNH())) {
				maxPointNH = Float.valueOf(s.getPointNH());
			}
			if (minPointNH > Float.valueOf(s.getPointNH())){
				minPointNH = Float.valueOf(s.getPointNH());
			}
			sumNH += Float.valueOf(s.getPointNH());

		
			
		}
		avgPointGZ = sumGZ/logNumber;
		avgPointXZ = sumXZ/logNumber;
		avgPointGT = sumGT/logNumber;
		avgPointNH = sumNH/logNumber;
		json += "\"houseNumber\": " + houseNumber + ",\"logNumber\":" +
				logNumber + ",\"maxPointGZ\":" + maxPointGZ + ",\"minPointGZ\":" +
				minPointGZ + ",\"avgPointGZ\":" + avgPointGZ + ",\"maxPointXZ\":" + 
				maxPointXZ + ",\"minPointXZ\":" + minPointXZ + ",\"avgPointXZ\":" + avgPointXZ
				+ ",\"maxPointGT\":" + maxPointGT + ",\"minPointGT\":" + minPointGT + 
				",\"avgPointGT\":" + avgPointGT + ",\"maxPointNH\":" + maxPointNH + ",\"minPointNH\":" 
				+ minPointNH + ",\"avgPointNH\":" + avgPointNH + ",\"totalPointCQ\":" + 
				(avgPointGT + avgPointGZ + avgPointNH + avgPointXZ) / 4;
		
		return json;
	}

	public String evaluateWarm(String projectID) {
		Projection p =  pDAO.findByNumber(projectID).get(0);
		long day = (p.getEndTime().getTime() - p.getCreateTime().getTime())/86400000;
		List<WarmLogs> data = wDAO.findByProjectID(projectID);
		int warmNumber = data.size();
		double warmFrequency = warmNumber/day;
		String json = "";
		long fastTime = 10000;
		long slowTime = 0;
		long sum = 0;
		double avgTime;
		for (WarmLogs w : data) {
			long temp = (w.getSolveTime().getTime() - w.getWarmTime().getTime())/86400000;
			if (fastTime > temp){
				fastTime = temp;
			}
			if (slowTime < temp) {
				slowTime = temp;
			}
			sum += temp;
		}
		avgTime = sum/day;
		
		json += "\"warmNumber\": " + warmNumber + ",\"warmFrequency\":" +
				warmFrequency + ",\"fastSolve\":" + fastTime + ",\"slowSolve\":" +
				slowTime + ",\"avgSolve\":" + avgTime + ",\"totalPointWarm\":" + 
				89;
		return json;
	}

}
