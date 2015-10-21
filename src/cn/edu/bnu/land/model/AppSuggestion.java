package cn.edu.bnu.land.model;

import java.util.Date;

public class AppSuggestion {
	
	public AppSuggestion()
	{
		
	}
	
	private Integer id;
	private String content;
	private String Status;
	private Date date;
	private Application application;
	
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
