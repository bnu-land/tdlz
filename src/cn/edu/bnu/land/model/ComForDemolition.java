package cn.edu.bnu.land.model;

import java.util.Date;

public class ComForDemolition {

	private Integer id;
	private Float area;
	private Float totalMoney;
	private Float firstMoney;
	private Float SecondMoney;
	private Date comfireDate;
	private Date firstMoneyDate;
	private Date secondMoneyDate;
	private String statusComfire;
	private String firstProvement;
	private String secondProvement;
	
	
	//一对一映射
	private FarmersInformation farmer;

	public ComForDemolition() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public Float getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Float getFirstMoney() {
		return firstMoney;
	}

	public void setFirstMoney(Float firstMoney) {
		this.firstMoney = firstMoney;
	}

	public Float getSecondMoney() {
		return SecondMoney;
	}

	public void setSecondMoney(Float secondMoney) {
		SecondMoney = secondMoney;
	}

	public Date getComfireDate() {
		return comfireDate;
	}

	public void setComfireDate(Date comfireDate) {
		this.comfireDate = comfireDate;
	}

	public Date getFirstMoneyDate() {
		return firstMoneyDate;
	}

	public void setFirstMoneyDate(Date firstMoneyDate) {
		this.firstMoneyDate = firstMoneyDate;
	}

	public Date getSecondMoneyDate() {
		return secondMoneyDate;
	}

	public void setSecondMoneyDate(Date secondMoneyDate) {
		this.secondMoneyDate = secondMoneyDate;
	}

	public String getStatusComfire() {
		return statusComfire;
	}

	public void setStatusComfire(String statusComfire) {
		this.statusComfire = statusComfire;
	}

	public String getFirstProvement() {
		return firstProvement;
	}

	public void setFirstProvement(String firstProvement) {
		this.firstProvement = firstProvement;
	}

	public String getSecondProvement() {
		return secondProvement;
	}

	public void setSecondProvement(String secondProvement) {
		this.secondProvement = secondProvement;
	}

	public FarmersInformation getFarmer() {
		return farmer;
	}

	public void setFarmer(FarmersInformation farmer) {
		this.farmer = farmer;
	}
	
	
	
}

