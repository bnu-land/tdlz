package cn.edu.bnu.land.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.BenefitAlarmshou;
import cn.edu.bnu.land.model.BenefitAlarmtai;
import cn.edu.bnu.land.model.BenefitAssign;
import cn.edu.bnu.land.model.BenefitAssignHome;
import cn.edu.bnu.land.model.BenefitCanshushou;
import cn.edu.bnu.land.model.BenefitCanshutai;
import cn.edu.bnu.land.model.BenefitFind;
import cn.edu.bnu.land.model.BenefitStatjs;
import cn.edu.bnu.land.model.BenefitStatnh;
import cn.edu.bnu.land.model.BenefitTrack;
import cn.edu.bnu.land.model.BenefitTrackHome;


@Service
public class BenefitAssignService {

	@Autowired
	private BenefitAssignHome benefitAssignHome;
	@Autowired
	private BenefitTrackHome benefitTrackHome;
	@Autowired
	private SessionFactory sessionFactory;
	

	//private ChartPieHome charPieHome;
	public void setBenefitAssignHome(BenefitAssignHome benefitAssignHome){
		this.benefitAssignHome=benefitAssignHome;

	}
	public void addBenefitAssign(BenefitAssign benefitAssign){
		this.benefitAssignHome.persist(benefitAssign);
	}

	//交易态势中交易量柱状图、饼状图
	public List<BenefitAssign> listChartPie(String searchKeyword, String searchDate1, String searchDate2){
		String hql = "SELECT new map(SUM(bf.jyl) as jylsum,bf.jyqy as jyqy,bf.jysj as jysj) FROM BenefitAssign bf";
//		String hql = "FROM BenefitAssign bf";
		String where = "";
		
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj>='"+searchDate1+"'"+" and bf.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj>='"+searchDate1+"'"+
		            " and bf.jyqy like '%"+searchKeyword+"%'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj<='"+searchDate2+"'"+
					 " and bf.jyqy like '%"+searchKeyword+"%'";			
		}		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'"+
					"and bf.jysj>='"+searchDate1+"'"+
					"and bf.jysj<='"+searchDate2+"'";	
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'";
		}
		//搜索日期为空，搜索关键词为空	 		
		else where = "";


			List<BenefitAssign> results = null;
			try {
				org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql+where+" GROUP BY bf.jysj,bf.jyqy");
				results = query.list();
			} catch (Exception e) {
				e.printStackTrace();
			}

		return results;
	}
	
	//交易态势中交易次数柱状图、饼状图
	public List<BenefitAssign> listChartNumber(String searchKeyword, String searchDate1, String searchDate2){

		String hql = "SELECT new map(bf.jyqy as taiarea ,COUNT(bf.jyqy) as taicount,bf.jyqy as jyqy) FROM BenefitAssign bf";
		String where = "";
		
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj>='"+searchDate1+"'"+" and bf.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj>='"+searchDate1+"'"+
		            " and bf.jyqy like '%"+searchKeyword+"%'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj<='"+searchDate2+"'"+
					 " and bf.jyqy like '%"+searchKeyword+"%'";			
		}		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'"+
					"and bf.jysj>='"+searchDate1+"'"+
					"and bf.jysj<='"+searchDate2+"'";	
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'";
		}
		//搜索日期为空，搜索关键词为空	 		
		else where = "";
			

			List<BenefitAssign> results = null;
			try {
				org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql+where+" GROUP BY bf.jyqy");
				results = query.list();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return results;
	}
	
	//交易态势中交易价格折线图
	public List<BenefitAssign> listChartLine(String searchKeyword, String searchDate1, String searchDate2) {
		// TODO Auto-generated method stub

		String hql = "SELECT new map(SUM(bf.je) as jesum,AVG(bf.dj) as djavg,bf.jyqy as jyqy,bf.jysj as jysj) FROM BenefitAssign bf";
		String where = "";
		
	//	String hql = "FROM BenefitAssign bf";
	//	String where = "";
		
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj>='"+searchDate1+"'"+" and bf.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj>='"+searchDate1+"'"+
		            " and bf.jyqy like '%"+searchKeyword+"%'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jysj<='"+searchDate2+"'"+
					 " and bf.jyqy like '%"+searchKeyword+"%'";			
		}		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'"+
					"and bf.jysj>='"+searchDate1+"'"+
					"and bf.jysj<='"+searchDate2+"'";	
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'";
		}
		//搜索日期为空，搜索关键词为空	 		
		else where = "";

			List<BenefitAssign> results = null;
			try {
				org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql+where+" GROUP BY bf.jysj,bf.jyqy");
				results = query.list();
			} catch (Exception e) {
				e.printStackTrace();
			}

		return results;
	}

	//交易态势信息查询
	public Map<String, Object> listTableTaiFind(String page, String start, String limit,String searchKeyword1, String searchDate1, 
				String searchDate2) {
		
		String hql = "";
		String hql1 = "from BenefitAssign deposit where";
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj>='"+searchDate1+"'"+" and deposit.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj>='"+searchDate1+"'"+
		            " and (deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.jyqy like '%"+searchKeyword1+"%'"+
					" or deposit.gmqymc like '%"+searchKeyword1+"%'"+
					" or deposit.qyxz like '%"+searchKeyword1+"%')";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj<='"+searchDate2+"'"+
					" and (deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.jyqy like '%"+searchKeyword1+"%'"+
					" or deposit.gmqymc like '%"+searchKeyword1+"%'"+
					" or deposit.qyxz like '%"+searchKeyword1+"%')";		
		}
		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql2 = "((deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.jyqy like '%"+searchKeyword1+"%'"+
					" or deposit.gmqymc like '%"+searchKeyword1+"%'"+
					" or deposit.qyxz like '%"+searchKeyword1+"%')"+
					"and deposit.jysj>='"+searchDate1+"'"+
					"and deposit.jysj<='"+searchDate2+"')";
			hql=hql1+hql2;
		
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql3 = " (deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.jyqy like '%"+searchKeyword1+"%'"+
					" or deposit.gmqymc like '%"+searchKeyword1+"%'"+
					" or deposit.qyxz like '%"+searchKeyword1+"%')";
			hql=hql1+hql3;
		}
		//搜索日期为空，搜索关键词为空
		else if(searchDate1.equals("") && searchDate2.equals("") && (searchKeyword1.equals("")||searchKeyword1.isEmpty())){ 		
			hql = "from BenefitAssign deposit";
			};


		String totalConut = null;
		List<BenefitAssign> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);

		return myMapResult;
		
	}	
	//交易态势表格删除信息
	@Transactional
	public void deleteTableByIds(String[] tablexh) {
		BenefitAssign result = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			for (int i = 0; i < tablexh.length; i++) {
				result = (BenefitAssign) session.get(BenefitAssign.class,
						Integer.parseInt(tablexh[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//交易态势表格修改信息	
	public BenefitAssign getTaishiFindEditForm(String tablexh){
		BenefitAssign result = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			result = (BenefitAssign) session.get(BenefitAssign.class,tablexh);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
	}
	
	//交易态势表格修改后更新信息	
	public void updateTaishiFormEdit(BenefitAssign benefitAssign) {
		Session session = sessionFactory.getCurrentSession();
		session.update(benefitAssign);
	}
		
	
	//交易态势预警信息饼状图、柱状图显示
	public List<BenefitAlarmtai> listChartTaishiAlarm(String searchKeyword,String searchDate1,String searchDate2){
		
		String hql = "SELECT new map(bf.yclx as yctype ,COUNT(bf.yclx) as yccount) FROM BenefitAlarmtai bf";
		String where = "";
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'"+" and bf.cxsj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'"+
		            " and bf.jyqy like '%"+searchKeyword+"%'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj<='"+searchDate2+"'"+
					 " and bf.jyqy like '%"+searchKeyword+"%'";			
		}		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'"+
					"and bf.cxsj>='"+searchDate1+"'"+
					"and bf.cxsj<='"+searchDate2+"'";	
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'";
		}
		//搜索日期为空，搜索关键词为空	 		
		else where = "";
		
		
//		//搜索日期1不为空，日期2为空
//		if(!searchDate1.equals("") && searchDate2.equals("") ){
//			where=" WHERE bf.cxsj>='"+searchDate1+"'";	
//		}
//		//搜索日期1为空，日期2不为空
//		else if(searchDate1.equals("")&&!searchDate2.equals("") ){
//			where=" WHERE bf.cxsj<='"+searchDate2+"'";		
//		}
//		//搜索日期1和2都不为空
//		else if(!searchDate1.equals("")&&!searchDate2.equals("")){
//			where=" WHERE bf.cxsj>='"+searchDate1+"'"+" and bf.cxsj<='"+searchDate2+"'";		
//		}
//		//搜索日期都为空
//		else where = "";
//		System.out.println("listChartTaishiAlarm where:"+where);
////		SELECT yclx ,COUNT(yclx) FROM benefit_alarmtai GROUP BY yclx;
////		String shql="select new map(deposit.yclx as yctype,count(deposit.yclx) as yccount) "+hql;
			
		List<BenefitAlarmtai> results = null;
		try {
				org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql+where+" GROUP BY bf.yclx");						
				results = query.list();
			} catch (Exception e) {
				e.printStackTrace();
		}

		return results;
	}
		/*
		public List<BenefitAssign> listChartAlarm(BenefitAssign benefitAssign){
			return this.benefitAssignHome.findByExample(benefitAssign);	
		}

		*/
		
	//交易态势预警信息表格显示查询
	public Map<String,Object> listTableTaiAlarm(String page,String start, String limit,String searchKeyword,String searchDate1,String searchDate2){
		
//		String hql = "";
//		String hql1 = "from BenefitAlarmtai deposit where";
//		//搜索日期1不为空，日期2为空，搜索关键词空
//		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
//			hql=hql1+" deposit.cxsj>='"+searchDate1+"'";	
//		}
//		//搜索日期1为空，日期2不为空，搜索关键词空
//		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
//			hql=hql1+" deposit.cxsj<='"+searchDate2+"'";		
//		}
//		//搜索日期1和2都不为空，搜索关键词空
//		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
//			hql=hql1+" deposit.cxsj>='"+searchDate1+"'"+" and deposit.cxsj<='"+searchDate2+"'";		
//		}
//		//搜索日期1不为空，日期2为空，搜索关键词不为空
//		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
//			hql=hql1+" deposit.cxsj>='"+searchDate1+"'"+
//		            " and deposit.jyqy like '%"+searchKeyword+"%'";	
//		}
//		//搜索日期1为空，日期2不为空，搜索关键词不为空
//		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
//			hql=hql1+" deposit.cxsj<='"+searchDate2+"'"+
//					 " and deposit.jyqy like '%"+searchKeyword+"%'";			
//		}
//		
//		//搜索日期不为空，搜索关键词不为空
//		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
//			String hql2 = "(deposit.jyqy like '%"+searchKeyword+"%'"+
//					"and deposit.cxsj>='"+searchDate1+"'"+
//					"and deposit.cxsj<='"+searchDate2+"')";
//			hql=hql1+hql2;
//		
//		}
//		//搜索日期空，搜索关键词不为空
//		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
//			String hql3 = "deposit.jyqy like '%"+searchKeyword+"%'";
//			hql=hql1+hql3;
//		}
//		//搜索日期为空，搜索关键词为空
//		else if(searchDate1.equals("") && searchDate2.equals("") && (searchKeyword.equals("")||searchKeyword.isEmpty())){ 		
//			hql = "from BenefitAlarmtai deposit";
//			};

		String hql = "FROM BenefitAlarmtai bf";
		String where = "";
		
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'"+" and bf.cxsj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'"+
		            " and bf.jyqy like '%"+searchKeyword+"%'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj<='"+searchDate2+"'"+
					 " and bf.jyqy like '%"+searchKeyword+"%'";			
		}		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'"+
					"and bf.cxsj>='"+searchDate1+"'"+
					"and bf.cxsj<='"+searchDate2+"'";	
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'";
		}
		//搜索日期为空，搜索关键词为空	 		
		else where = "";


		String totalConut = null;
		List<BenefitAlarmtai> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql+where);
			totalConut = String.valueOf(query.list().size());
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);

		return myMapResult;
			
	}			

	//交易态势预警信息参数更新
	public void updateTaishiAlarm(BenefitCanshutai benefitCanshutai) {
		Session session = sessionFactory.getCurrentSession();
		session.update(benefitCanshutai);
	}
		
	//收益分配资金流动列表信息登记
//	public void addShouRegister(InfoArticle infoArticle) {
//		Session session = sessionFactory.getCurrentSession();   	 
//     	session.saveOrUpdate(infoArticle); 
//     	//System.out.println("updateOneDraft: "+infoArticle.getArticlePublishtime());
//	}
	private static String generateFileName() {
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// 转换为字符串
		String formatDate = format.format(new Date());
		// 随机生成文件编号
		int random = new Random().nextInt(1000);
		return new StringBuffer().append(formatDate).append(
		random).toString();
		}
	
	public void addShouRegister(HttpServletRequest request,HttpServletResponse response,String filename){
		
		MultipartHttpServletRequest multipartHttpservletRequest=(MultipartHttpServletRequest) request;  //获得表单数据请求
	    MultipartFile multipartFile = multipartHttpservletRequest.getFile(filename);  //获得上传图片名称
	    String originalFileName=multipartFile.getOriginalFilename();//获得上传图片新生成的名称
	    
	    File file=new File("E:\\eclipsework\\Mytest\\WebContent\\images\\"+request.getParameter("pjh")+"_"+generateFileName()+"");//上传的文件存放路径
	        try {  
	            FileOutputStream fileOutputStream=new FileOutputStream(file+originalFileName.substring(originalFileName.lastIndexOf('.'), 
	            		originalFileName.length()));  
	            fileOutputStream.write(multipartFile.getBytes());  
	            fileOutputStream.flush();  
	            fileOutputStream.close();  
	        } catch (FileNotFoundException e) {   
	            e.printStackTrace();   
	        } catch (IOException e) {  	           
	            e.printStackTrace();  	            
	        }  
	     BenefitTrack benefitTrack=new BenefitTrack();
	     benefitTrack.setHtbh(request.getParameter("htbh"));
	     benefitTrack.setPjh(request.getParameter("pjh"));
	     benefitTrack.setYt(Encoder.encode(request.getParameter("yt")));
	     benefitTrack.setZjlcf(Encoder.encode(request.getParameter("zjlcf")));
	     benefitTrack.setZjlrf(Encoder.encode(request.getParameter("zjlrf")));
	     benefitTrack.setJe(Integer.parseInt(request.getParameter("je")));
	     benefitTrack.setJbr(Encoder.encode(request.getParameter("jbr")));
	     try {
	    	 System.out.println("setJysj:"+request.getParameter("jysj"));
			benefitTrack.setJysj(new SimpleDateFormat("yy年MM月dd日").parse(Encoder.encode(request.getParameter("jysj"))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     benefitTrack.setDzqr(Encoder.encode(request.getParameter("dzqr")));
	     benefitTrack.setScsmj(request.getParameter("pjh")+"_"+generateFileName()+"");
	   
	     this.benefitTrackHome.save(benefitTrack);
	    
	}
	
	//收益分配合同信息查询
	public Map<String,Object> listTableShouFind(String page,String start, String limit, String searchKeyword1, String searchDate1, String searchDate2){
		
		String hql = "";
		String hql1 = "from BenefitFind deposit where";
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj>='"+searchDate1+"'"+" and deposit.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj>='"+searchDate1+"'"+
					" and (deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.jyqy like '%"+searchKeyword1+"%'"+
					" or deposit.gmqymc like '%"+searchKeyword1+"%'"+
					" or deposit.qyxz like '%"+searchKeyword1+"%')";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj<='"+searchDate2+"'"+ 
					"and (deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.jyqy like '%"+searchKeyword1+"%'"+
					" or deposit.gmqymc like '%"+searchKeyword1+"%'"+
					" or deposit.qyxz like '%"+searchKeyword1+"%')";		
		}
		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql2 ="((deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.jyqy like '%"+searchKeyword1+"%'"+
					" or deposit.gmqymc like '%"+searchKeyword1+"%'"+
					" or deposit.qyxz like '%"+searchKeyword1+"%')"+
					"and deposit.jysj>='"+searchDate1+"'"+
					"and deposit.jysj<='"+searchDate2+"')";
			hql=hql1+hql2;
		
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql3 = " (deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.jyqy like '%"+searchKeyword1+"%'"+
					" or deposit.gmqymc like '%"+searchKeyword1+"%'"+
					" or deposit.qyxz like '%"+searchKeyword1+"%')";
			hql=hql1+hql3;
		}
		//搜索日期为空，搜索关键词为空
		else if(searchDate1.equals("") && searchDate2.equals("") && (searchKeyword1.equals("")||searchKeyword1.isEmpty())){ 		
			hql = "from BenefitFind deposit";
			};


		String totalConut = null;
		List<BenefitFind> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);

		return myMapResult;

	}
	
	//收益分配表（合同表）表格信息删除
	@Transactional
	public void deleteShouyiHetongTableByIds(String[] tablexh) {
		BenefitFind result = null;
		Session session = sessionFactory.getCurrentSession();
//		System.out.println("收益分配表（合同表）表格信息删除");
		try {
			for (int i = 0; i < tablexh.length; i++) {
				result = (BenefitFind) session.get(BenefitFind.class,
						Integer.parseInt(tablexh[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	//收益分配资金流动信息查询
	public Map<String,Object> listTableShouFindTrack(String page,String start, String limit, String searchKeyword1,String searchDate1,String searchDate2){
		String hql = "";
		String hql1 = "from BenefitTrack deposit where";
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj>='"+searchDate1+"'"+" and deposit.jysj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj>='"+searchDate1+"'"+
					" and (deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.pjh like '%"+searchKeyword1+"%'"+
					" or deposit.zjlcf like '%"+searchKeyword1+"%'"+
					" or deposit.zjlrf like '%"+searchKeyword1+"%'"+
					" or deposit.yt like '%"+searchKeyword1+"%'"+
					" or deposit.jbr like '%"+searchKeyword1+"%'"+
					" or deposit.scsmj like '%"+searchKeyword1+"%'"+
					" or deposit.dzqr like '%"+searchKeyword1+"%')";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.jysj<='"+searchDate2+"'"+ 
					"and (deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.pjh like '%"+searchKeyword1+"%'"+
					" or deposit.zjlcf like '%"+searchKeyword1+"%'"+
					" or deposit.zjlrf like '%"+searchKeyword1+"%'"+
					" or deposit.yt like '%"+searchKeyword1+"%'"+
					" or deposit.jbr like '%"+searchKeyword1+"%'"+
					" or deposit.scsmj like '%"+searchKeyword1+"%'"+
					" or deposit.dzqr like '%"+searchKeyword1+"%')";		
		}
		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql2 ="((deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.pjh like '%"+searchKeyword1+"%'"+
					" or deposit.zjlcf like '%"+searchKeyword1+"%'"+
					" or deposit.zjlrf like '%"+searchKeyword1+"%'"+
					" or deposit.yt like '%"+searchKeyword1+"%'"+
					" or deposit.jbr like '%"+searchKeyword1+"%'"+
					" or deposit.scsmj like '%"+searchKeyword1+"%'"+
					" or deposit.dzqr like '%"+searchKeyword1+"%')"+
					"and deposit.jysj>='"+searchDate1+"'"+
					"and deposit.jysj<='"+searchDate2+"')";
			hql=hql1+hql2;
		
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql3 = " (deposit.htbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.pjh like '%"+searchKeyword1+"%'"+
					" or deposit.zjlcf like '%"+searchKeyword1+"%'"+
					" or deposit.zjlrf like '%"+searchKeyword1+"%'"+
					" or deposit.yt like '%"+searchKeyword1+"%'"+
					" or deposit.jbr like '%"+searchKeyword1+"%'"+
					" or deposit.scsmj like '%"+searchKeyword1+"%'"+
					" or deposit.dzqr like '%"+searchKeyword1+"%')";
			hql=hql1+hql3;
		}
		//搜索日期为空，搜索关键词为空
		else if(searchDate1.equals("") && searchDate2.equals("") && (searchKeyword1.equals("")||searchKeyword1.isEmpty())){ 		
			hql = "from BenefitTrack deposit";
			};


		String totalConut = null;
		List<BenefitTrack> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);

		return myMapResult;

	}	
	
	//收益分配资金流动列表格删除信息
	@Transactional
	public void deleteShouyiZijinTableByIds(String[] tablexh) {
		BenefitTrack result = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			for (int i = 0; i < tablexh.length; i++) {
				result = (BenefitTrack) session.get(BenefitTrack.class,
						Integer.parseInt(tablexh[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	///收益分配预警信息饼状图、柱状图显示
	public List<BenefitAlarmshou> listChartShouyiAlarm(String searchKeyword,String searchDate1,String searchDate2){
	
		String hql = "SELECT new map(bf.yclx as yctype ,COUNT(bf.yclx) as yccount) FROM BenefitAlarmshou bf";
		String where = "";
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'"+" and bf.cxsj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'"+
		            " and bf.jyqy like '%"+searchKeyword+"%'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj<='"+searchDate2+"'"+
					 " and bf.jyqy like '%"+searchKeyword+"%'";			
		}		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'"+
					"and bf.cxsj>='"+searchDate1+"'"+
					"and bf.cxsj<='"+searchDate2+"'";	
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'";
		}
		//搜索日期为空，搜索关键词为空	 		
		else where = "";
		System.out.println("listChartTaishiAlarm where:"+where);
		
		List<BenefitAlarmshou> results = null;
		try {
				org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql+where+" GROUP BY bf.yclx");						
				results = query.list();
			} catch (Exception e) {
				e.printStackTrace();
		}

		return results;	
	}
	
	//收益分配预警信息表格显示查询
	public Map<String,Object> listTableShouAlarm(String page,String start, String limit,String searchKeyword, String searchDate1,String searchDate2){
		String hql = "FROM BenefitAlarmshou bf";
		String where = "";
		
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'"+" and bf.cxsj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj>='"+searchDate1+"'"+
		            " and bf.jyqy like '%"+searchKeyword+"%'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.cxsj<='"+searchDate2+"'"+
					 " and bf.jyqy like '%"+searchKeyword+"%'";			
		}		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'"+
					"and bf.cxsj>='"+searchDate1+"'"+
					"and bf.cxsj<='"+searchDate2+"'";	
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword.equals("")||searchKeyword.isEmpty())){
			where=" WHERE bf.jyqy like '%"+searchKeyword+"%'";
		}
		//搜索日期为空，搜索关键词为空	 		
		else where = "";


		String totalConut = null;
		List<BenefitAlarmshou> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql+where);
			totalConut = String.valueOf(query.list().size());
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);

		return myMapResult;
		
	}	
	
	//收益分配预警信息参数更新
	public void updateShouyiAlarm(BenefitCanshushou benefitCanshushou) {
		Session session = sessionFactory.getCurrentSession();
		session.update(benefitCanshushou);
	}	

	
	//收益分配建设单位信息统计查询
	public Map<String,Object> listTableShouStatJian(String page, String start, String limit,String searchKeyword1,String searchDate1,String searchDate2){
		String hql = "";
		String hql1 = "from BenefitStatjs deposit where";
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.cysj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.cysj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.cysj>='"+searchDate1+"'"+" and deposit.cysj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.cysj>='"+searchDate1+"'"+
					" and (deposit.sjdkbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.dwmc like '%"+searchKeyword1+"%'"+
					" or deposit.cyrw like '%"+searchKeyword1+"%')";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.cysj<='"+searchDate2+"'"+ 
					"and (deposit.sjdkbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.dwmc like '%"+searchKeyword1+"%'"+
					" or deposit.cyrw like '%"+searchKeyword1+"%')";		
		}
		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql2 ="((deposit.sjdkbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.dwmc like '%"+searchKeyword1+"%'"+
					" or deposit.cyrw like '%"+searchKeyword1+"%')"+
					"and deposit.cysj>='"+searchDate1+"'"+
					"and deposit.cysj<='"+searchDate2+"')";
			hql=hql1+hql2;
		
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql3 = " (deposit.sjdkbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.dwmc like '%"+searchKeyword1+"%'"+
					" or deposit.cyrw like '%"+searchKeyword1+"%')";
			hql=hql1+hql3;
		}
		//搜索日期为空，搜索关键词为空
		else if(searchDate1.equals("") && searchDate2.equals("") && (searchKeyword1.equals("")||searchKeyword1.isEmpty())){ 		
			hql = "from BenefitStatjs deposit";
			};


		String totalConut = null;
		List<BenefitStatjs> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);

		return myMapResult;
	}	
	
	//收益分配农户信息统计查询
	public Map<String,Object> listTableShouStatNong(String page, String start, String limit,String searchKeyword1,String searchDate1,String searchDate2){		
		String hql = "";
		String hql1 = "from BenefitStatnh deposit where";
		//搜索日期1不为空，日期2为空，搜索关键词空
		if(!searchDate1.equals("") && searchDate2.equals("") &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.lzsj>='"+searchDate1+"'";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.lzsj<='"+searchDate2+"'";		
		}
		//搜索日期1和2都不为空，搜索关键词空
		else if(!searchDate1.equals("")&&!searchDate2.equals("")  &&  (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.lzsj>='"+searchDate1+"'"+" and deposit.lzsj<='"+searchDate2+"'";		
		}
		//搜索日期1不为空，日期2为空，搜索关键词不为空
		else if(!searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.lzsj>='"+searchDate1+"'"+
					" and (deposit.yydkbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.nhxm like '%"+searchKeyword1+"%')";	
		}
		//搜索日期1为空，日期2不为空，搜索关键词不为空
		else if(searchDate1.equals("")&&!searchDate2.equals("")  && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			hql=hql1+" deposit.lzsj<='"+searchDate2+"'"+ 
					"and (deposit.yydkbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.nhxm like '%"+searchKeyword1+"%')";		
		}
		
		//搜索日期不为空，搜索关键词不为空
		else if(!searchDate1.equals("")  && !searchDate2.equals("")&& ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql2 ="((deposit.yydkbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.nhxm like '%"+searchKeyword1+"%')"+
					"and deposit.lzsj>='"+searchDate1+"'"+
					"and deposit.lzsj<='"+searchDate2+"')";
			hql=hql1+hql2;
		
		}
		//搜索日期空，搜索关键词不为空
		else if(searchDate1.equals("") && searchDate2.equals("") && ! (searchKeyword1.equals("")||searchKeyword1.isEmpty())){
			String hql3 = " (deposit.yydkbh like '%"+searchKeyword1+"%'"+ 
					" or deposit.nhxm like '%"+searchKeyword1+"%')";
			hql=hql1+hql3;
		}
		//搜索日期为空，搜索关键词为空
		else if(searchDate1.equals("") && searchDate2.equals("") && (searchKeyword1.equals("")||searchKeyword1.isEmpty())){ 		
			hql = "from BenefitStatnh deposit";
			};


		String totalConut = null;
		List<BenefitStatnh> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);

		return myMapResult;
	}
	

}

