package cn.edu.bnu.land.model;

import java.util.HashSet;
import java.util.Set;

public class AdministrationUnit extends UserInformation{	
	private String email;
	private String level;
	private String department;
	private Set<AdministrationUnit> AdministrationUnit = new HashSet<AdministrationUnit>();
	
	
	public Set<AdministrationUnit> getAdministrationUnit() {
		return AdministrationUnit;
	}

	public void setAdministrationUnit(Set<AdministrationUnit> AdministrationUnit) {
		this.AdministrationUnit = AdministrationUnit;
	}

	public AdministrationUnit()
	{
	}
	
	public AdministrationUnit(String email, 
			String level, String department)
	{	
		this.email =email;
		this.level = level;
		this.department = department;
	}
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level =level;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department =department;
	}	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email =email;
	}
}
