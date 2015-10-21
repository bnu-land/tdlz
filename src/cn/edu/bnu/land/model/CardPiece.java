package cn.edu.bnu.land.model;

import java.util.Set;

//地块表
public class CardPiece {

	private Integer id;
	private String houseNumber;
	private String QSDWMC;
	private String DYNHXM;
	private String DYNHSFZ;
	private double Shape_Area;
	private String FWJG;
	private String FWSYSJ;
	private String DLMC;
	private String SFDYXZSJ;
	private String FWZP;
	private String joinFlag;
	private String demolitionFlag;
	private Set<DemolitionLog> dlogs;
	private String projectID;
	
	
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public Set<DemolitionLog> getDlogs() {
		return dlogs;
	}
	public void setDlogs(Set<DemolitionLog> dlogs) {
		this.dlogs = dlogs;
	}
	public String getDemolitionFlag() {
		return demolitionFlag;
	}
	public void setDemolitionFlag(String demolitionFlag) {
		this.demolitionFlag = demolitionFlag;
	}
	public String getJoinFlag() {
		return joinFlag;
	}
	public void setJoinFlag(String joinFlag) {
		this.joinFlag = joinFlag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getQSDWMC() {
		return QSDWMC;
	}
	public void setQSDWMC(String qSDWMC) {
		QSDWMC = qSDWMC;
	}
	public String getDYNHXM() {
		return DYNHXM;
	}
	public void setDYNHXM(String dYNHXM) {
		DYNHXM = dYNHXM;
	}
	public String getDYNHSFZ() {
		return DYNHSFZ;
	}
	public void setDYNHSFZ(String dYNHSFZ) {
		DYNHSFZ = dYNHSFZ;
	}
	public double getShape_Area() {
		return Shape_Area;
	}
	public void setShape_Area(double shape_Area) {
		Shape_Area = shape_Area;
	}
	public String getFWJG() {
		return FWJG;
	}
	public void setFWJG(String fWJG) {
		FWJG = fWJG;
	}
	public String getFWSYSJ() {
		return FWSYSJ;
	}
	public void setFWSYSJ(String fWSYSJ) {
		FWSYSJ = fWSYSJ;
	}
	public String getDLMC() {
		return DLMC;
	}
	public void setDLMC(String dLMC) {
		DLMC = dLMC;
	}
	public String getSFDYXZSJ() {
		return SFDYXZSJ;
	}
	public void setSFDYXZSJ(String sFDYXZSJ) {
		SFDYXZSJ = sFDYXZSJ;
	}
	public String getFWZP() {
		return FWZP;
	}
	public void setFWZP(String fWZP) {
		FWZP = fWZP;
	}
	
	
}
