package cn.edu.bnu.land.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.common.AutoSendingTask;
import cn.edu.bnu.land.model.ProjectProgrestatic2;
import cn.edu.bnu.land.model.ProjectYqProgress;
import cn.edu.bnu.land.model.RwTable;
import cn.edu.bnu.land.model.DkMonitordata;
import cn.edu.bnu.land.model.PublicProject;
import cn.edu.bnu.land.model.FkDikuai;
import cn.edu.bnu.land.model.AbnmWholesupervision;

@Service
public class TaskSendingService {
	@Autowired
	private SessionFactory sessionFactory;

	//1、自动下发任务
	public void addTask(){
		Calendar ca = Calendar.getInstance();//日历实例化
		java.util.Date nowdate=ca.getTime();//ca.getTime()得到当前时间
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");//24时制的日期格式
		String dateString = format2.format(nowdate);//当前日期格式化		
		long now=nowdate.getTime();//ca.getTime().getTime() 日期转为天数，用于计算日期差

		//1、查找yq表没有下过任务的记录
		org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery("from ProjectYqProgress as yq where yq.isSended='0' or yq.isSended=null ORDER BY yq.monitorTime ASC");
		List<ProjectYqProgress> results=query.list();//得到没有下过任务的列表	
		System.out.println("yq表未下发任务的记录数："+results.size());
		//for(java.util.Iterator i = results.iterator(); i.hasNext();){
		//	ProjectYqProgress prj=(ProjectYqProgress) i.next();	//prj是第i条查询结果
		if(results.size()>0)
		{
			ProjectYqProgress prj=results.get(0);	

			long monitortime=prj.getMonitorTime().getTime();
			long remains= monitortime/(24*60*60*1000) - now/(24*60*60*1000);
			//2、如果任务该下达的话，即下达任务
			if (remains<=prj.getFinishdateLimit()){
				//3、填写任务表
				System.out.println("----have-new—task:");
				RwTable rwtable=new RwTable();
				rwtable.setRwId("FKJC"+dateString);
				rwtable.setProjectId(prj.getProjectId());
				rwtable.setProjectName(prj.getProjectName());
				rwtable.setRwContent("监测'"+prj.getProjectName()+"'中地块的工程进度、当前地块范围和现场照片");
				rwtable.setRwClass("复垦监测");
				rwtable.setRwGeneratetime(ca.getTime());
				rwtable.setRwStarttime(ca.getTime());
				rwtable.setRwEndtime(prj.getMonitorTime());
				rwtable.setRwResponsiblePerson("长寿区国土资源局");	
				sessionFactory.getCurrentSession().save(rwtable);
				prj.setRwId("FKJC"+dateString);
				prj.setIsSended(true);
				sessionFactory.getCurrentSession().update(prj);
			}
		}
		else{
			System.out.println("results2.size()=====");
		}
	}

	//2、自动分析新采集的数据
	public void addupdateTask(){
		String hql="select rw.rwId,rw.rwIsfinished,rw.rwSubmittime,"
				+ " SUM(dk.fukenArea*dm.tdpzPercent)/SUM(dk.fukenArea) as pz,"
				+ " SUM(dk.fukenArea*dm.tjdlPercent)/SUM(dk.fukenArea) as dl," 
				+ " SUM(dk.fukenArea*dm.ntslPercent)/SUM(dk.fukenArea) as sl,"
				+ " yq.ypercentPz,yq.ypercentDl,yq.ypercentSl,prj.projectId,prj.projectname"
				+ " from ProjectYqProgress as yq,RwTable as rw,DkMonitordata as dm,FkDikuai as dk,PublicProject as prj "
				+ " where yq.rwId=rw.rwId and rw.rwIsfinished='1' and yq.isFinished='0' and yq.projectId=prj.projectId and yq.projectName=prj.projectname  "
				+ " and dm.dkId=dk.landId AND dm.projectName=dk.projectname AND dm.rwId=yq.rwId group by rw.rwId ORDER BY yq.monitorTime ASC";
		System.out.println("addupdateTask hql:"+hql);
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			List result = query.list();
			System.out.println("result.size="+result.size()); 
			for(java.util.Iterator i = result.iterator(); i.hasNext();)  {               
				Object[] prj=(Object[]) i.next();//
				System.out.println("判断rwId:"+prj[0]);
				if(prj[0].equals("")) continue;
				//1、ProjectYqProgress预期监测计划表的字段更新
				for(int j=0;j<prj.length;j++)
					System.out.println("prjid:"+prj[j]);
				try{ 
					org.hibernate.Query query2=sessionFactory.getCurrentSession().createQuery("from ProjectYqProgress as yq where yq.rwId='"+prj[0]+"'");
					System.out.println("from ProjectYqProgress as yq where yq.rwId='"+prj[0]+"'");
					List<ProjectYqProgress> yqresults=query2.list();//列表
					System.out.println("需要分析的数据个数："+yqresults.size()); 
					ProjectYqProgress yq=yqresults.get(0);
					yq.setIsFinished((Boolean) prj[1]);
					yq.setFinishTime((java.util.Date) prj[2]);
					yq.setPpercentPz(new Float(prj[3].toString()));
					yq.setPpercentDl(new Float(prj[4].toString()));
					yq.setPpercentSl(new Float(prj[5].toString()));
					sessionFactory.getCurrentSession().update(yq);
				}
				catch(RuntimeException e){	
					e.printStackTrace();}
				//2、AbnmWholesupervision：如果进度异常，写入预警数据表
				try{ 
					org.hibernate.Query query3=sessionFactory.getCurrentSession().createQuery("from ProjectYqProgress as yq where (yq.ppercentPz<yq.ypercentPz or yq.ppercentSl<yq.ypercentSl or yq.ppercentDl<yq.ypercentDl) and yq.rwId='"+prj[0]+"'");
					List<ProjectYqProgress> results=query3.list();//列表
					if(results.size()>0)
					{				
						AbnmWholesupervision abnormal=new AbnmWholesupervision();
						System.out.println("进度异常项目个数为："+results.size()); 
						java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMdd");//24时制的日期格式
						String dateString = format2.format((java.util.Date) prj[2]);//当前日期格式化
						abnormal.setAbwsTitle("项目进度异常："+prj[9]+"在"+prj[0]);
						abnormal.setAbwsClass("复垦监测");
						abnormal.setAbwsDate((java.util.Date) prj[2]);
						abnormal.setAbwsContent("复垦进度落后于预期进度");
						sessionFactory.getCurrentSession().save(abnormal);
						System.out.println("abnormal.getAbwsTitle()-----"+abnormal.getAbwsTitle()); 
						//3、PublicProject字段更新最新进度和时间				
						try{ 
							org.hibernate.Query query4=sessionFactory.getCurrentSession().createQuery("from PublicProject as pub where pub.projectId='"+prj[9]+"'");
							List<PublicProject> results4=query4.list();//列表
							PublicProject pubprj=results4.get(0);
							pubprj.setLatelyMonitortime((java.util.Date) prj[2]);
							pubprj.setPpercentPz(new Float(prj[3].toString()));
							pubprj.setPpercentDl(new Float(prj[4].toString()));
							pubprj.setPpercentSl(new Float(prj[5].toString()));
							pubprj.setIsNormal(false);
							sessionFactory.getCurrentSession().update(pubprj);}
						catch(RuntimeException e){	
							e.printStackTrace();}


					}
					else 	
						System.out.println("无进度异常项目"); 
				}
				catch(RuntimeException e){	
					e.printStackTrace();}


			}

		}
		catch(RuntimeException e){	
			e.printStackTrace();}
	}
}



