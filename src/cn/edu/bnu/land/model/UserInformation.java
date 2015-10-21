package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserInformation{
	private Integer id;
	private String username;
	private String password;
	private String phone;
	private String permission;
	private String projectID;
	private Set<UserInformation> userinformation = new HashSet<UserInformation>();
	
	
	public Set<UserInformation> getUserInformation() {
		return userinformation;
	}

	public void setUserInformation(Set<UserInformation> userinformation) {
		this.userinformation = userinformation;
	}

	public UserInformation()
	{
	}
	
	
	public UserInformation(Integer id, String username, String password, 
			String phone, String permission, String projectID)
	{
		this.projectID = projectID;
		this.id = id;
		this.username =username;
		this.password =password;
		this.phone = phone;
		this.permission = permission;
	}

	
	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}

