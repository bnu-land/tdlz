package cn.edu.bnu.land.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.AbnmDktranssupervision;
import cn.edu.bnu.land.model.AbnmDktranssupervisionHome;
import cn.edu.bnu.land.model.AbnmIndex;
import cn.edu.bnu.land.model.AbnmIndexHome;
import cn.edu.bnu.land.model.FkDikuai;
import cn.edu.bnu.land.model.PublicProject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class AbnmDktranssupervisionService {
	private AbnmDktranssupervisionHome abnmDktranssupervisionHome;
	private AbnmIndexHome abnmIndexHome;
	private SessionFactory sessionFactory;
	private Configuration configuration = null;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setAbnmDktranssupervisionHome(
			AbnmDktranssupervisionHome abnmDktranssupervisionHome) {
		this.abnmDktranssupervisionHome = abnmDktranssupervisionHome;

	}

	@Autowired
	public void setAbnmIndexHome(AbnmIndexHome abnmIndexHome) {
		this.abnmIndexHome = abnmIndexHome;

	}

	/*
	 * @Description:获得交易风险预警的地块信息列表
	 * 
	 * @Params:String start, String limit, String searchKeyword, String
	 * searchDate, String province, String city, String county
	 * 
	 * @Return:Map<String, Object> myAbwsList
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	public Map<String, Object> getAbdktsList(String start, String limit,
			String searchKeyword, String searchDate, String province,
			String city, String county) {

		String areaCode = "";
		if (county.equals("")) {
			if (city.equals("")) {
				areaCode = province;
			} else {
				areaCode = city;
			}
		} else if (!county.equals("")) {
			areaCode = county;
		}

		String hql = " ";
		String hql1 = "from AbnmDktranssupervision as abnmDktranssupervision ";
		String hql2 = "areaCode = " + areaCode;
		String hql3 = "(zldwmc like '%" + searchKeyword + "%'"
				+ " or abdkSeller like '%" + searchKeyword + "%'"
				+ "or abdkBuyer like '%" + searchKeyword + "%')";
		String hql4 = " abdkWarningtime >= '" + searchDate + "'";

		if (areaCode.equals("")) {
			if (searchKeyword.equals("")) {
				if (searchDate.equals("")) {
					hql = hql1;
				} else if (!searchDate.equals("")) {
					hql = hql1 + "where" + hql4;
				}

			} else if (!searchKeyword.equals("")) {
				if (searchDate.equals("")) {
					hql = hql1 + "where " + hql3;
				} else if (!searchDate.equals("")) {
					hql = hql1 + "where " + hql3 + "and " + hql4;
				}
			}
		} else if (!areaCode.equals("")) {
			if (searchKeyword.equals("")) {
				if (searchDate.equals("")) {
					hql = hql1 + "where " + hql2;
				} else if (!searchDate.equals("")) {
					hql = hql1 + "where " + hql2 + "and " + hql4;
				}

			} else if (!searchKeyword.equals("")) {
				if (searchDate.equals("")) {
					hql = hql1 + "where " + hql2 + "and " + hql3;
				} else if (!searchDate.equals("")) {
					hql = hql1 + "where " + hql2 + "and " + hql3 + "and "
							+ hql4;
				}
			}
		}

		System.out.println("@@@@@*********getAbdktsList" + hql);

		String totalConut = null;
		List<AbnmDktranssupervision> results = null;
		try {
			// System.out.println(hql);
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			// System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);
		// System.out.println("myMapResult "+gridId+" "+myMapResult);

		return myMapResult;

	}

	/*
	 * @Description: 增加交易风险预警的地块信息
	 * 
	 * @Params: AbnmDktranssupervision abdkts
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140226
	 */
	public void addAbdkts(AbnmDktranssupervision abdkts) throws UnsupportedEncodingException {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(abdkts);
		} catch (Exception er) {
			System.out.println(er.getMessage());
		}

		add5Index(abdkts);
		updateuseFreemarker2doc(abdkts.getAbdkId().toString());
		
	}

	/*
	 * @Description: 增加相应的预警指标信息
	 * 
	 * @Params: AbnmDktranssupervision abdkts
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140226
	 */
	// for one DK,there are 5 index
	public void add5Index(AbnmDktranssupervision abdkts) {

		// get the value of djbd
		double dgjj = Double.parseDouble(abdkts.getDgjj());
		double dgcl = Double.parseDouble(abdkts.getDgcl());
		double aprice = Double.parseDouble(abdkts.getAbdkAprice());
		double djbdValue = Math.abs(dgjj * dgcl - aprice) / (dgjj * dgcl);
		// System.out.println("********dgjj:"+dgjj+"\ndgcl:"+dgcl+"aprice:"+aprice+"djbdValue:"+djbdValue+"******");

		// get the value of mjpc
		double aarea = Double.parseDouble(abdkts.getAbdkAarea());
		double barea = Double.parseDouble(abdkts.getDkArea());
		double mjpcValue = Math.abs(aarea - barea);

		// get the value of zbpc
		double radLat1 = Double.parseDouble(abdkts.getAbdkBx()) * Math.PI
				/ 180.0;
		double radLng1 = Double.parseDouble(abdkts.getAbdkBy()) * Math.PI
				/ 180.0;
		double radLat2 = Double.parseDouble(abdkts.getAbdkAx()) * Math.PI
				/ 180.0;
		double radLng2 = Double.parseDouble(abdkts.getAbdkAy()) * Math.PI
				/ 180.0;
		double a = radLat1 - radLat1;
		double b = radLng1 - radLng2;
		double zbpc = 2 * Math.asin(Math.sqrt(Math.pow(Math.asin(a / 2), 2)
				+ Math.acos(radLat1) * Math.acos(radLat2)
				* Math.pow(Math.asin(b / 2), 2)));
		zbpc = zbpc * 6378.137;
		zbpc = Math.round(zbpc * 10000) / 10000;

		// add new indexs ,and set value and weight of these indexs
		AbnmIndex abi1 = new AbnmIndex();
		AbnmIndex abi2 = new AbnmIndex();
		AbnmIndex abi3 = new AbnmIndex();
		AbnmIndex abi4 = new AbnmIndex();
		AbnmIndex abi5 = new AbnmIndex();
		abi1.setDkId(abdkts.getAbdkId().toString());
		abi1.setAbiName("地价波动");
		abi1.setAbiClass("经济指标");
		abi1.setAbiValue(Double.toString(djbdValue)); // |稻谷均价*稻谷产量-交易后价格|/（稻谷均价*稻谷产量）
		abi1.setAbiWeight("38.096");
		abi2.setDkId(abdkts.getAbdkId().toString());
		abi2.setAbiName("坡度");
		abi2.setAbiClass("空间指标");
		abi2.setAbiValue(abdkts.getAbdkSlope());
		abi2.setAbiWeight("0.372");
		abi3.setDkId(abdkts.getAbdkId().toString());
		abi3.setAbiName("坡度方差");
		abi3.setAbiClass("空间指标");
		abi3.setAbiValue(abdkts.getAbdkSlopevariance());
		abi3.setAbiWeight("0.763");
		abi4.setDkId(abdkts.getAbdkId().toString());
		abi4.setAbiName("坐标偏移");
		abi4.setAbiClass("空间指标");
		abi4.setAbiValue(Double.toString(zbpc)); // 使用地块属性的“交易前地块中心点坐标”字段 和
													// 地块属性的“交易后地块中心点坐标”字段
													// ，计算两个中心点的直线距离。
		abi4.setAbiWeight("1.893");
		abi5.setDkId(abdkts.getAbdkId().toString());
		abi5.setAbiName("面积偏差");
		abi5.setAbiClass("空间指标");
		abi5.setAbiValue(Double.toString(mjpcValue));// |地块属性的“交易前地块面积”字段 -
														// 地块属性的“交易后地块面积”字段|
		abi5.setAbiWeight("-1.625");
		Session session = sessionFactory.getCurrentSession();
		session.save(abi1);
		session.save(abi2);
		session.save(abi3);
		session.save(abi4);
		session.save(abi5);
	}

	/*
	 * @Description:通过信息id删除整条交易预警的地块信息
	 * 
	 * @Params: String[] ids
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140304
	 */
	public void delete_abdkByIds(String[] ids) {
		AbnmDktranssupervision result = null;
		Session session = sessionFactory.getCurrentSession();

		try {
			for (int i = 0; i < ids.length; i++) {
				result = (AbnmDktranssupervision) session.get(
						AbnmDktranssupervision.class, Integer.parseInt(ids[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		delete_abiByDkIds(ids);

	}

	/*
	 * @Description:删除相应的预警指标信息
	 * 
	 * @Params: String[] ids
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140304
	 */
	public void delete_abiByDkIds(String[] dkIds) {
		for (int i = 0; i < dkIds.length; i++) {
			String hql = "delete AbnmIndex as abi where abi.dkId = " + dkIds[i];
			sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
		}
	}

	/*
	 * @Description:更新地块的交易风险预警信息
	 * 
	 * @Params:AbnmDktranssupervision abdkts
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140225
	 */
	public void updateAbdkts(AbnmDktranssupervision abdkts) throws UnsupportedEncodingException {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(abdkts);
		updateuseFreemarker2doc(abdkts.getAbdkId().toString());
	}

	/*
	 * @Description: 计算交易风险指数，更新“是否异常”“风险等级”“预警时间”和“是否预警”
	 * 
	 * @Params: abdkId
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140408
	 */
	public void updateAbdkAfterCal(String abdkId) throws UnsupportedEncodingException {

		Calendar ca = Calendar.getInstance();
		Date now = ca.getTime();

		AbnmDktranssupervision abdkts = null;
		Session session = sessionFactory.getCurrentSession();
		abdkts = (AbnmDktranssupervision) session.get(
				AbnmDktranssupervision.class, Integer.parseInt(abdkId));

		Double num = Math.random();
		if (num < 0.2) {
			abdkts.setAbdkRisklevel("无");
			abdkts.setAbdkIsnormal("否");
		} else if (num >= 0.2 && num < 0.5) {
			abdkts.setAbdkRisklevel("低");
			abdkts.setAbdkIsnormal("是");
		} else if (num >= 0.5 && num < 0.7) {
			abdkts.setAbdkRisklevel("中");
			abdkts.setAbdkIsnormal("是");
		} else {
			abdkts.setAbdkRisklevel("高");
			abdkts.setAbdkIsnormal("是");
		}

		abdkts.setAbdkWarningtime(now);
		abdkts.setAbdkIswarning("是");

		session.update(abdkts);
		updateuseFreemarker2doc(abdkts.getAbdkId().toString());

	}

	/*
	 * @Description: 使用freemarker生成doc，并存储，更新“html存储路径”
	 * 
	 * @Params: abdkId
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140408
	 */
	public void updateuseFreemarker2doc(String abdkId) throws UnsupportedEncodingException {
		// 要填入模本的数据文件
		Session session = sessionFactory.getCurrentSession();
		String hql = "from AbnmDktranssupervision as abnmDktranssupervision ";
		AbnmDktranssupervision result = null;
		try {
			// System.out.println(hql);
			result = (AbnmDktranssupervision) session.get(
					AbnmDktranssupervision.class, Integer.parseInt(abdkId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		// 这里我们的模板是放在com.havenliu.document.template包下面
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(),
				"/cn/edu/bnu/land/common");
		Template t = null;

		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate("abts_freemarker2doc.ftl");
			t.setEncoding("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 输出文档路径及名称
		Calendar ca = Calendar.getInstance();
		Date date = ca.getTime();
		java.text.DateFormat format2 = new java.text.SimpleDateFormat(
				"yyyyMMddhhmmss");
		String dateString = format2.format(date);

		// 文件名称
//		String m_fileName = result.getDkTbid() + "_" + dateString + ".doc";
//		System.out.println("before"+m_fileName);
//		m_fileName= URLEncoder.encode(m_fileName, "UTF-8"); 
//		System.out.println("after"+m_fileName);
		String m_fileName = dateString + ".doc";
		String realPath = this.getClass().getClassLoader().getResource("/")
				.getPath();
		// System.out.println(realPath);
		int pos = realPath.indexOf("/WEB-INF");
		// System.out.println(pos);
		realPath = realPath.substring(0, pos);
		realPath = realPath + "/Upload/abnmTransSupDoc/";
		// System.out.println(realPath);
		String filePath = realPath + m_fileName;
		result.setAbdkDocpath("/tdjg/Upload/abnmTransSupDoc/" + m_fileName);

		File outFile = new File(filePath);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			t.process(result, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * @Description: 使用freemarker生成html，并存储，更新“html存储路径”
	 * 
	 * @Params: abdkId
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140408
	 */
	public void updateuseFreemarker2html(String abdkId) {
		// 要填入模本的数据文件
		Session session = sessionFactory.getCurrentSession();
		String hql = "from AbnmDktranssupervision as abnmDktranssupervision ";
		AbnmDktranssupervision result = null;
		try {
			// System.out.println(hql);
			result = (AbnmDktranssupervision) session.get(
					AbnmDktranssupervision.class, Integer.parseInt(abdkId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		// 这里我们的模板是放在cn.edu.bnu.land.common包下面
		configuration = new Configuration();
		// configuration.setDefaultEncoding("utf-8");
		configuration.setEncoding(Locale.CHINA, "utf-8");
		configuration.setClassForTemplateLoading(this.getClass(),
				"/cn/edu/bnu/land/common");
		Template t = null;

		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate("abts_freemarker2html.ftl");
			t.setEncoding("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 输出文档路径及名称
		Calendar ca = Calendar.getInstance();
		Date date = ca.getTime();
		java.text.DateFormat format2 = new java.text.SimpleDateFormat(
				"yyyyMMddhhmmss");
		String dateString = format2.format(date);
		// 文件名称
		String m_fileName = result.getDkTbid() + "_" + dateString + ".html";
		//
		String realPath = this.getClass().getClassLoader().getResource("/")
				.getPath();
		System.out.println(realPath);
		int pos = realPath.indexOf("/WEB-INF");
		System.out.println(pos);
		realPath = realPath.substring(0, pos);
		realPath = realPath + "/Upload/abnmTransSupHtml/";
		System.out.println(realPath);
		String filePath = realPath + m_fileName;
		result.setAbdkHtmlpath("/tdlzJGXT/Upload/abnmTransSupHtml/"
				+ m_fileName);

		File outFile = new File(filePath);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			t.process(result, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<PublicProject> abnmGetPublicProject() {
		String hql = "from PublicProject";

		List<PublicProject> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			results = (List<PublicProject>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	public List<FkDikuai> abnmGetDikuai(String projectId) {
		String hql = null;
		System.out.println(projectId);
		
		hql = "from FkDikuai where projectId = '"+projectId+"'";
		
		
		System.out.println(hql);

		List<FkDikuai> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			results = (List<FkDikuai>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

}