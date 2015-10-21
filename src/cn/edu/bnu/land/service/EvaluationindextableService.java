package cn.edu.bnu.land.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import cn.edu.bnu.land.model.EvaluationindextableHome;
import cn.edu.bnu.land.model.Evaluationindextable;

@Service
public class EvaluationindextableService {

	private EvaluationindextableHome evaluationindextableHome;
	@Autowired
	public void setEvaluationindextableHome(EvaluationindextableHome evaluationindextableHome)
	{
		this.evaluationindextableHome=evaluationindextableHome;
	}
	public void add_evaluation(Evaluationindextable evaluationindextable)
	{
		//System.out.print("�����<�ָ��");
		this.evaluationindextableHome.save(evaluationindextable);
	}	
}
