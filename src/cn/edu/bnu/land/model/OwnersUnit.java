package cn.edu.bnu.land.model;

import java.util.HashSet;
import java.util.Set;

public class OwnersUnit extends UserInformation{	
	private String email;
	private String level;
	private String department;

private Set<OwnersUnit> OwnersUnit = new HashSet<OwnersUnit>();
	
	
	public Set<OwnersUnit> getOwnersUnit() {
		return OwnersUnit;
	}

	public void setOwnersUnit(Set<OwnersUnit> OwnersUnit) {
		this.OwnersUnit = OwnersUnit;
	}

	public OwnersUnit()
	{
	}
	
	public OwnersUnit(String email, 
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
