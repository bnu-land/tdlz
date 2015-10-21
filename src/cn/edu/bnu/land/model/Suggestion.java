package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Suggestion {
	private Integer id;
	private String content;
	private Date date;
	private String category;
	private String publisher;
	private Float grade;
	private Noticement notice;
	private String projectID;

	public Noticement getNotice() {
		return notice;
	}

	public void setNotice(Noticement notice) {
		this.notice = notice;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public Suggestion() {
		// TODO Auto-generated constructor stub
	}
	
	public Suggestion(Integer id, String co, Date d, String ca,
			String p, Float g)
	{
		this.id = id;
		this.content = co;
		this.date = d;
		this.category = ca;
		this.publisher = p;
		this.grade = g;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Float getGrade() {
		return grade;
	}
	public void setGrade(Float grade) {
		this.grade = grade;
	}
	
	
}
