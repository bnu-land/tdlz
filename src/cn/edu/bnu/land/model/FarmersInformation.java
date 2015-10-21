package cn.edu.bnu.land.model;

import java.util.HashSet;
import java.util.Set;

public class FarmersInformation extends UserInformation{
	private String farmername;
	private String sex;
	private String idnumber;
	private String address;
	private Set<FarmersInformation> FarmersInformation = new HashSet<FarmersInformation>();
	public Set<FarmersInformation> getFarmersInformation() {
		return FarmersInformation;
	}

	public void setFarmersInformation(Set<FarmersInformation> FarmersInformation) {
		this.FarmersInformation = FarmersInformation;
	}

	public FarmersInformation()
	{
	}
	
	public FarmersInformation(String farmername, String sex, String idnumber, 
			String address)
	{	this.farmername = farmername;
		this.sex =sex;
		this.idnumber = idnumber;
		this.address = address;
	}
	
	public String getFarmername() {
		return farmername;
	}

	public void setFarmername(String farmername) {
		this.farmername = farmername;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber=idnumber;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
