package cn.edu.bnu.land.service;

public interface EvaluateService {
	public String evaluateSuggest(String projectID);
	public String evaluateCQGC(String projectID);
	public String evaluateWarm(String projectID);
}
