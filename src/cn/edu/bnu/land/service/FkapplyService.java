package cn.edu.bnu.land.service;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.Fkapply;
import cn.edu.bnu.land.model.FkapplyHome;
import cn.edu.bnu.land.model.InfoVote;

@Service
public class FkapplyService {
	private FkapplyHome fkapplyHome;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setPublicProjectHome(FkapplyHome fkapplyHome){
		this.fkapplyHome=fkapplyHome;
	}
	public void addApply(String projectId,String projectName,String applyConUnit,String fileCheck,String applyNote){
		//System.out.print("save successfully");
		this.fkapplyHome.save(projectId, projectName, applyConUnit, fileCheck, applyNote); 
	}
	/*
	 * 查询复垦申请 20140116 @WW
	 */	
	public Map<String, Object> select(String start, String limit,String searchKeyword){
		System.out.print("select");
		return this.fkapplyHome.select(start, limit, searchKeyword);
	}
	/*
	 * 查询复垦审核申请 20140116 @WW
	 */	
	public Map<String, Object> selectCheck(String start, String limit,String searchKeyword){
		System.out.print("select");
		return this.fkapplyHome.selectCheck(start, limit, searchKeyword);
	}
	/*
	 * 输入审核结果
	 * */
	public void updatefkapply(String checkApplyId,String applyResult ) {
		
		String hql = "update Fkapply set checkResult='"+applyResult+"' where applyId='"+checkApplyId+"' ";
		System.out.print("打印hql:"+hql);
		try{
			org.hibernate.Query query=sessionFactory.getCurrentSession().createQuery(hql);
			query.executeUpdate();					
		}catch(Exception e){
			e.printStackTrace();
		}	 		 	
	}
	
	/*
	 * delete project 20131105 @WW
	 */	
	@Transactional
	public void del_applyByIds(String[] applyIds){
		this.fkapplyHome.del_applyByIds(applyIds);
	}	
	
}
