package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Noticement {
	private Integer id;
	private String content;
	private Date date;
	private String category;
	private String publisher;
	private String title;
	private String enclosureFlag;
	private String enclosurePath;
	private String projectID;
	private Set<Suggestion> suggest = new HashSet<Suggestion>();
	
	
	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getEnclosureFlag() {
		return enclosureFlag;
	}

	public void setEnclosureFlag(String enclosureFlag) {
		this.enclosureFlag = enclosureFlag;
	}

	public String getEnclosurePath() {
		return enclosurePath;
	}

	public void setEnclosurePath(String enclosurePath) {
		this.enclosurePath = enclosurePath;
	}

	public Set<Suggestion> getSuggest() {
		return suggest;
	}

	public void setSuggest(Set<Suggestion> suggest) {
		this.suggest = suggest;
	}

	public Noticement()
	{
	}
	
	public Noticement(Integer id, String ct, Date d, 
			String ca, String p, String t)
	{
		this.id = id;
		this.content = ct;
		this.date = d;
		this.category = ca;
		this.publisher = p;
		this.title = t;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
