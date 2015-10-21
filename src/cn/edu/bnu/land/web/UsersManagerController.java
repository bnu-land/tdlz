package cn.edu.bnu.land.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.common.Encoder;
import cn.edu.bnu.land.model.UDeptInfo;
import cn.edu.bnu.land.model.URightInfo;
import cn.edu.bnu.land.model.URoleInfo;
import cn.edu.bnu.land.model.URoleRight;
import cn.edu.bnu.land.model.UUserInfo;
import cn.edu.bnu.land.model.Users;
import cn.edu.bnu.land.service.UsersManagerService;

@Controller
public class UsersManagerController {
	private UsersManagerService userManagerService;

	@Autowired
	public UsersManagerController(UsersManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	// ---------------------------------数据库备份与还原---------------------------
	// 备份数据库
	@RequestMapping(value = "/add_databseBackup")
	@ResponseBody
	public Map<String, Object> databaseBackup(@RequestParam("bkCommnet") String bkCommnet,
			HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		System.out.println("进入数据库备份与还原--------------");
		return this.userManagerService.backupDatabase(bkCommnet);
		//
	}

	// ----------------------------------用户信息users-------------------------------
	// 查询用户信息
	@RequestMapping(value = "/get_Users")
	// ,method=RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getUsers(
			@RequestParam("searchKeyword") String searchKeyword)
			throws IOException {
		System.out.println("searchKeyword : " + searchKeyword);
		searchKeyword = Encoder.encode(searchKeyword);
		return this.userManagerService.getUsersList(searchKeyword);
	}

	// 删除用户信息
	@RequestMapping(value = "/del_UserById")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delUserById(@RequestParam("username") String username)
			throws IOException {
		this.userManagerService.deleteUserById(username);
	}

	// 添加用户信息
	@RequestMapping(value = "/add_User")
	@ResponseBody
	public Map<String, Object> addUser(@RequestBody Users users)
			throws IOException {
		// System.out.println("addUserInfo :"+userInfo);
		this.userManagerService.addUserInfo(users);
		Map<String, Object> userInfoResults = new HashMap<String, Object>();
		userInfoResults.put("success", true);
		userInfoResults.put("msg", ",successfully saved");
		return (userInfoResults);
	}

	// 修改用户信息
	@RequestMapping(value = "/update_User")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateUser(@RequestBody Users users) throws IOException {
		System.out.println("update_userInfo: " + users.getUserid());
		this.userManagerService.updateOneUserInfo(users);
	}

	// ----------------------------------部门信息-------------------------------
	// 查询部门信息
	@RequestMapping(value = "/get_DeptInfo")
	// ,method=RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getDeptInfo(
			@RequestParam("searchKeyword") String searchKeyword)
			throws IOException {
		System.out.println("get_DeptInfo : " + searchKeyword);
		searchKeyword = Encoder.encode(searchKeyword);
		// this.userManagerService.addUser(user);
		return this.userManagerService.getDeptInfoList(searchKeyword);
	}

	// 查询部门信息Tree
	@RequestMapping(value = "/get_DeptInfoTree", produces = "text/html;charset=UTF-8")
	// @RequestMapping(value="/Home/writeJson", method=RequestMethod.GET,
	// produces = "text/html;charset=UTF-8")
	// ,method=RequestMethod.POST
	@ResponseBody
	public String getDeptInfoTree(HttpServletResponse response)
			throws IOException {
		return this.userManagerService.getDeptInfoTree(response);
	}

	// 删除部门信息
	@RequestMapping(value = "/del_DeptInfoById")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void deldeptInfoById(@RequestParam("deptId") String deptId)
			throws IOException {
		this.userManagerService.deleteDeptInfoById(deptId);
	}

	// 添加部门信息
	@RequestMapping(value = "/add_DeptInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeptInfo(@RequestBody UDeptInfo deptInfo)
			throws IOException {
		// System.out.println("addDeptInfo :"+deptInfo);
		this.userManagerService.addDeptInfo(deptInfo);
		Map<String, Object> deptInfoResults = new HashMap<String, Object>();
		deptInfoResults.put("success", true);
		deptInfoResults.put("msg", ",successfully saved");
		return (deptInfoResults);
	}

	// 修改部门信息
	@RequestMapping(value = "/update_DeptInfo", method = RequestMethod.POST)
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateDeptInfo(@RequestBody UDeptInfo deptInfo)
			throws IOException {
		System.out.println("update_deptInfo: " + deptInfo.getDeptName());
		this.userManagerService.updateOneDeptInfo(deptInfo);
	}

	// ----------------------------------用户信息UserInfo-------------------------------
	// 查询用户信息
	@RequestMapping(value = "/get_UserInfo")
	// ,method=RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getUserInfo(
			@RequestParam("searchKeyword") String searchKeyword)
			throws IOException {
		System.out.println("searchKeyword : " + searchKeyword);
		searchKeyword = Encoder.encode(searchKeyword);
		// this.userManagerService.addUser(user);
		return this.userManagerService.getUserInfoList(searchKeyword);
	}

	// 删除用户信息
	@RequestMapping(value = "/del_UserInfoById")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delUserInfoById(@RequestParam("userId") String userId)
			throws IOException {

		this.userManagerService.deleteUserInfoById(userId);
	}

	// 添加用户信息
	@RequestMapping(value = "/add_UserInfo")
	@ResponseBody
	public Map<String, Object> addUserInfo(@RequestBody UUserInfo userInfo)
			throws IOException {
		// System.out.println("addUserInfo :"+userInfo);
		this.userManagerService.addUserInfo(userInfo);
		Map<String, Object> userInfoResults = new HashMap<String, Object>();
		userInfoResults.put("success", true);
		userInfoResults.put("msg", ",successfully saved");
		return (userInfoResults);
	}

	// 修改用户信息
	@RequestMapping(value = "/update_UserInfo")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateUserInfo(@RequestBody UUserInfo userInfo)
			throws IOException {
		System.out.println("update_userInfo: " + userInfo.getUserId());
		this.userManagerService.updateOneUserInfo(userInfo);
	}

	// ----------------------------------权限信息-------------------------------
	// 查询权限信息
	@RequestMapping(value = "/get_RightInfo")
	// ,method=RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getRightInfo(
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("limit") String limit) throws IOException {
		System.out.println("searchKeyword : " + searchKeyword);
		searchKeyword = Encoder.encode(searchKeyword);
		// this.userManagerService.addUser(user);
		return this.userManagerService.getRightInfoList(searchKeyword, limit);
	}

	// 删除权限信息
	@RequestMapping(value = "/del_RightInfoById")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delRightInfoById(@RequestParam("rightId") String rightId)
			throws IOException {
		this.userManagerService.deleteRightInfoById(rightId);
	}

	// 添加权限信息
	@RequestMapping(value = "/add_RightInfo")
	@ResponseBody
	public Map<String, Object> addRightInfo(@RequestBody URightInfo rightInfo)
			throws IOException {
		// System.out.println("addRightInfo :"+rightInfo);
		this.userManagerService.addRightInfo(rightInfo);
		Map<String, Object> rightInfoResults = new HashMap<String, Object>();
		rightInfoResults.put("success", true);
		rightInfoResults.put("msg", ",successfully saved");
		return (rightInfoResults);
	}

	// 修改权限信息
	@RequestMapping(value = "/update_RightInfo")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateRightInfo(@RequestBody URightInfo rightInfo)
			throws IOException {
		System.out.println("update_rightInfo: " + rightInfo.getRightName());
		this.userManagerService.updateOneRightInfo(rightInfo);
	}

	// ----------------------------------角色信息-------------------------------
	// 查询角色信息
	@RequestMapping(value = "/get_RoleInfo")
	// ,method=RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getRoleInfo(
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("enabled") String enabled)
			throws IOException {		
		searchKeyword = Encoder.encode(searchKeyword);
		System.out.println("searchKeyword : " + searchKeyword);
		// this.userManagerService.addUser(user);
		return this.userManagerService.getRoleInfoList(searchKeyword,enabled);
	}

	// 删除角色信息
	@RequestMapping(value = "/del_RoleInfoById")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delRoleInfoById(@RequestParam("roleId") String roleId)
			throws IOException {
		this.userManagerService.deleteRoleInfoById(roleId);
	}

	// 添加角色信息
	@RequestMapping(value = "/add_RoleInfo")
	@ResponseBody
	public Map<String, Object> addRoleInfo(@RequestBody URoleInfo roleInfo)
			throws IOException {
		System.out.println("addRoleInfo :"+roleInfo);		
		return this.userManagerService.addRoleInfo(roleInfo);
	}

	// 修改角色信息
	@RequestMapping(value = "/update_RoleInfo")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateRoleInfo(@RequestBody URoleInfo roleInfo)
			throws IOException {
		// System.out.println("update_roleInfo: " + roleInfo.getRoleName());
		this.userManagerService.updateOneRoleInfo(roleInfo);
	}

	// ----------------------------------用户角色设置-------------------------------
	// 查询用户角色信息
	@RequestMapping(value = "/get_UserRoleList")
	// ,method=RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getUserRoleList(
			@RequestParam("username") String username)
			throws IOException {
		return this.userManagerService.getUserRoleList(username);
	}

	// 添加用户角色信息
	@RequestMapping(value = "/update_UserRole")
	@ResponseBody
	public Map<String, Object> updateUserRole(
			@RequestParam("username") String username,
			@RequestParam("userRole") String[] userRole) throws IOException {
		// System.out.println("addRoleRight :"+roleRight);
		this.userManagerService.updateUserRole(username, userRole);
		Map<String, Object> roleRightResults = new HashMap<String, Object>();
		roleRightResults.put("success", true);
		roleRightResults.put("msg", ",successfully saved");
		return (roleRightResults);
	}

	// ----------------------------------角色权限信息-------------------------------
	// 查询角色权限信息
	@RequestMapping(value = "/get_RoleRight")
	// ,method=RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getRoleRight(
			@RequestParam("searchKeyword") String searchKeyword)
			throws IOException {
		System.out.println("searchKeyword : " + searchKeyword);
		searchKeyword = Encoder.encode(searchKeyword);
		// this.userManagerService.addUser(user);
		return this.userManagerService.getRoleRightList(searchKeyword);
	}

	// 删除角色权限信息
	@RequestMapping(value = "/del_RoleRightById")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void delRoleRightById(@RequestParam("roleId") String roleId)
			throws IOException {
		this.userManagerService.deleteRoleRightById(roleId);
	}

	// 添加角色权限信息
	@RequestMapping(value = "/add_RoleRight")
	@ResponseBody
	public Map<String, Object> addRoleRight(@RequestBody URoleRight roleRight)
			throws IOException {
		// System.out.println("addRoleRight :"+roleRight);
		this.userManagerService.addRoleRight(roleRight);
		Map<String, Object> roleRightResults = new HashMap<String, Object>();
		roleRightResults.put("success", true);
		roleRightResults.put("msg", ",successfully saved");
		return (roleRightResults);
	}

	// 修改角色权限信息
	@RequestMapping(value = "/update_RoleRight")
	// ,method=RequestMethod.POST)
	@ResponseBody
	public void updateRoleRight(@RequestBody URoleRight roleRight)
			throws IOException {
		System.out.println("update_roleRight: " + roleRight.getRightId());
		this.userManagerService.updateOneRoleRight(roleRight);
	}

}
