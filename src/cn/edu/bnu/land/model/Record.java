package cn.edu.bnu.land.model;

import java.util.Date;

public class Record {
	private Integer id;
	private String category;
	private Date date;
	private String statusUp;
	private String statusAug;
	private String path;
	private String projectID;
	
	public Record(){
		
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatusUp() {
		return statusUp;
	}
	public void setStatusUp(String statusUp) {
		this.statusUp = statusUp;
	}
	public String getStatusAug() {
		return statusAug;
	}
	public void setStatusAug(String statusAug) {
		this.statusAug = statusAug;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
