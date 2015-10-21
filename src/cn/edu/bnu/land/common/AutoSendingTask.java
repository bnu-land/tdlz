package cn.edu.bnu.land.common;

import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import java.text.SimpleDateFormat; 

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.ProjectYqProgress;
import cn.edu.bnu.land.model.RssAreaCode;
import cn.edu.bnu.land.model.RwTable;
import cn.edu.bnu.land.service.TaskSendingService;

public class AutoSendingTask extends TimerTask{
	private TaskSendingService taskSendingService;

	
	public void setTaskSendingService(TaskSendingService taskSendingService) {
		this.taskSendingService = taskSendingService;
	}
	public void AutotaskTimer(){

	}
	
	@Override
	public void run() {
		taskSendingService.addTask();//下发任务
		taskSendingService.addupdateTask();//分析数据
	}
}



