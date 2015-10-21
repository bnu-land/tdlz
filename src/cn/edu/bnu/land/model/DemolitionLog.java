package cn.edu.bnu.land.model;

import java.util.Date;

public class DemolitionLog {

	private Integer id;
	private String content;
	private Date date;
	private String person;
	private Integer photoNumber;
	private String photoPath;
	private String photoExt;
	private CardPiece cp;
	private String projectID;
	private String pointGZ;
	private String pointXZ;
	private String pointGT;
	private String pointNH;
	
	
	
	public String getPointGZ() {
		return pointGZ;
	}


	public void setPointGZ(String pointGZ) {
		this.pointGZ = pointGZ;
	}


	public String getPointXZ() {
		return pointXZ;
	}


	public void setPointXZ(String pointXZ) {
		this.pointXZ = pointXZ;
	}


	public String getPointGT() {
		return pointGT;
	}


	public void setPointGT(String pointGT) {
		this.pointGT = pointGT;
	}


	public String getPointNH() {
		return pointNH;
	}


	public void setPointNH(String pointNH) {
		this.pointNH = pointNH;
	}


	public String getProjectID() {
		return projectID;
	}


	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}


	public CardPiece getCp() {
		return cp;
	}


	public void setCp(CardPiece cp) {
		this.cp = cp;
	}


	public DemolitionLog()
	{
		photoNumber = 0;
	}

	
	public String getPhotoExt() {
		return photoExt;
	}


	public void setPhotoExt(String photoExt) {
		this.photoExt = photoExt;
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

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Integer getPhotoNumber() {
		return photoNumber;
	}

	public void setPhotoNumber(Integer photoNumber) {
		this.photoNumber = photoNumber;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
	
	
}
