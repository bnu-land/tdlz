package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Application {
	
	public Application()
	{
		
	}
	
	private Integer id;
	private String name;
	private String card;
	private String address;
	private String applicationAddress;
	private String phone;
	private String productID;
	private double totalArea;
	private double bulidArea;
	private Date productDate;
	private Date applicationDate;
	
	//四至范围
	private double north;
	private double south;
	private double west;
	private double east;
	
	private String work;
	private String afterUse;
	private String belongUse;
	private String reword;
	private String isAgreeSelf;
	private String isAgreeVillage;
	private String isAgreeTown;
	
	private String content;

	//权利证明
	private String pathBuilding;
	private String pathDirection;
	private String pathProtocol;
	private String pathHomestead;
	
	//审核状态
	private String statusList;
	private String statusProvement;
	private Date dateList;
	private Date dateProvement;
	
	//农户
	UserInformation farmer;
	
	//意见
	Set<AppSuggestion> suggest = new HashSet<AppSuggestion>();

	
	public Set<AppSuggestion> getSuggest() {
		return suggest;
	}

	public void setSuggest(Set<AppSuggestion> suggest) {
		this.suggest = suggest;
	}

	public UserInformation getFarmer() {
		return farmer;
	}

	public void setFarmer(UserInformation farmer) {
		this.farmer = farmer;
	}

	public Date getDateList() {
		return dateList;
	}

	public void setDateList(Date dateList) {
		this.dateList = dateList;
	}

	public Date getDateProvement() {
		return dateProvement;
	}

	public void setDateProvement(Date dateProvement) {
		this.dateProvement = dateProvement;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApplicationAddress() {
		return applicationAddress;
	}

	public void setApplicationAddress(String applicationAddress) {
		this.applicationAddress = applicationAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(double totalArea) {
		this.totalArea = totalArea;
	}

	public double getBulidArea() {
		return bulidArea;
	}

	public void setBulidArea(double bulidArea) {
		this.bulidArea = bulidArea;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public double getNorth() {
		return north;
	}

	public void setNorth(double north) {
		this.north = north;
	}

	public double getSouth() {
		return south;
	}

	public void setSouth(double south) {
		this.south = south;
	}

	public double getWest() {
		return west;
	}

	public void setWest(double west) {
		this.west = west;
	}

	public double getEast() {
		return east;
	}

	public void setEast(double east) {
		this.east = east;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getAfterUse() {
		return afterUse;
	}

	public void setAfterUse(String afterUse) {
		this.afterUse = afterUse;
	}

	public String getBelongUse() {
		return belongUse;
	}

	public void setBelongUse(String belongUse) {
		this.belongUse = belongUse;
	}

	public String getReword() {
		return reword;
	}

	public void setReword(String reword) {
		this.reword = reword;
	}

	public String getIsAgreeSelf() {
		return isAgreeSelf;
	}

	public void setIsAgreeSelf(String isAgreeSelf) {
		this.isAgreeSelf = isAgreeSelf;
	}

	public String getIsAgreeVillage() {
		return isAgreeVillage;
	}

	public void setIsAgreeVillage(String isAgreeVillage) {
		this.isAgreeVillage = isAgreeVillage;
	}

	public String getIsAgreeTown() {
		return isAgreeTown;
	}

	public void setIsAgreeTown(String isAgreeTown) {
		this.isAgreeTown = isAgreeTown;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPathBuilding() {
		return pathBuilding;
	}

	public void setPathBuilding(String pathBuilding) {
		this.pathBuilding = pathBuilding;
	}

	public String getPathDirection() {
		return pathDirection;
	}

	public void setPathDirection(String pathDirection) {
		this.pathDirection = pathDirection;
	}

	public String getPathProtocol() {
		return pathProtocol;
	}

	public void setPathProtocol(String pathProtocol) {
		this.pathProtocol = pathProtocol;
	}

	public String getPathHomestead() {
		return pathHomestead;
	}

	public void setPathHomestead(String pathHomestead) {
		this.pathHomestead = pathHomestead;
	}

	public String getStatusList() {
		return statusList;
	}

	public void setStatusList(String statusList) {
		this.statusList = statusList;
	}

	public String getStatusProvement() {
		return statusProvement;
	}

	public void setStatusProvement(String statusProvement) {
		this.statusProvement = statusProvement;
	}
	
	
	
}
