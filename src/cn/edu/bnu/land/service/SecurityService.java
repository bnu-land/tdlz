package cn.edu.bnu.land.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.URoleRight;
import cn.edu.bnu.land.model.Users;

@Service
public class SecurityService {

	private SessionFactory sessionFactory;

	// @Autowired
	// private HttpServletRequest request;
	// private HttpServletResponse response;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// @Autowired
	// public void setReqAndRes(HttpServletRequest request, HttpServletResponse
	// response){
	// this.request = request;
	// this.response = response;
	// }

	// @Autowired
	// public void setHttpServletRequest(HttpServletRequest request){
	// this.request = request;
	// }
	//
	// @Autowired
	// public void setHttpServletResponse(HttpServletResponse response){
	// this.response = response;
	// }

	// 得到登录用户名
	public String getLoginUser() {
		// Object principal = SecurityContextHolder.getContext()
		// .getAuthentication()
		// .getPrincipal();
		String username = "匿名";
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername(); // get logged in username
			System.out.println("get logged in username:" + username);
			// if (principal instanceof UserDetails) {
			// username = ((UserDetails) principal).getUsername();
			// } else {
			// username = principal.toString();
			// }
		} catch (RuntimeException e) {
			System.out.println("getLoginUserError:");
			e.printStackTrace();
		}
		return username;
	}

	// 根据用户名称，得到用户权限列表
	@SuppressWarnings("unchecked")
	public Boolean getUserRightList(String url) {
		Boolean hasTheRight = false;
		String username = getLoginUser();
		System.out.println("getLoginUser():"+username);
		try {
			String hql = "SELECT rti.url"
					+ " FROM UUserRole AS ur,URoleRight AS rr,URightInfo AS rti"
					+ " WHERE ur.roleid = rr.roleId AND rr.rightId = rti.rightId AND"
					+ " ur.username = '" + username + "'";
			System.out.println(hql);
			org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
			//System.out.println(query.list().toArray().toString());
			List<String> rightList = null;
			rightList = (List<String>) query.list();
			hasTheRight = rightList.contains(url);//判断是否包含此权限

		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return hasTheRight;
		//return true;	//屏蔽权限管理，请使用这个返回值
	}

	/**
	 * 根据用户名获取权限列表
	 * 
	 * @param userName
	 * @return
	 */
	// public List<String> getAuthorityName(String userName) {
	// System.out.println("取得权限列表：getAuthorityName（）");
	// String hql = "FROM Users as Users";
	// if (!searchKey.equals("")) {
	// String hql2 = " WHERE Users.username LIKE '%" + searchKey + "%'"
	// + "OR Users.truename LIKE '%" + searchKey + "%'"
	// + " OR Users.userid LIKE '%" + searchKey + "%'"
	// + " OR Users.description LIKE '%" + searchKey + "%'"
	// + " OR Users.email LIKE '%" + searchKey + "%'"
	// + " OR Users.mobilenum LIKE '%" + searchKey + "%'"
	// + " OR Users.phonenum LIKE '%" + searchKey + "%'";
	// hql = hql + hql2;
	// }
	// System.out.println(hql);
	// List<Users> results = null;
	// org.hibernate.Query query = sessionFactory.getCurrentSession()
	// .createQuery(hql);
	// results = (List<Users>) query.list();
	// Map<String, Object> myMapResult = new TreeMap<String, Object>();
	// myMapResult.put("root", results);
	// myMapResult.put("success", true);
	// return myMapResult;
	// }

	// /**
	// * 根据用户名查找密码
	// * @param username
	// * @return
	// */
	// public String getPWD(String username) {
	// return "";//this.sqlSession.selectOne(sid("selectPWD"), username);
	// }
	//
	// /**
	// * 得到所有权限
	// * @return
	// */
	// public List<String> getAllAuthorityName() {
	// return "";// this.sqlSession.selectList(sid("selectAllAuthorityName"));
	// }
	//
	// /**
	// * 根据权限获得可以访问的页面
	// * @param authorityName
	// * @return
	// */
	// public List<String> getResource(String authorityName) {
	// //return this.sqlSession.selectList(sid("selectResource"),authorityName);
	// }

	@Autowired
	public void RedirectSystem() throws IOException {
		// window.location.href = 'index.html'; //跳转到登陆成功后的页面
		// HttpServletRequest request =
		// ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		// String path = request.getContextPath() ;
		// String basePath =
		// request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		//
		// HttpServletResponse response =
		// ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		//
		// String username = getLoginUser();
		// System.out.println("this is RedirectSystem:"+basePath);
		// if(username.contains("oper")){
		// //response.sendRedirect("http://www.baidu.com");
		// }
		// Set<String> roles =
		// AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext()
		// .getAuthentication().getAuthorities());
		//
		// for (String role : roles) {
		// System.out.println("Role:"+role);
		// }

		// if (roles.contains("ROLE_ADMIN")) {
		// return new RedirectView(basePath + "app/admin");
		// }
		// return new RedirectView(basePath + "app/welcome");
	}
}