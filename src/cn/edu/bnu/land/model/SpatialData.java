package cn.edu.bnu.land.model;

public class SpatialData {
	private Integer id;
	private String spatialContent;
	private String projectID;
	
	
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
	public String getSpatialContent() {
		return spatialContent;
	}
	public void setSpatialContent(String spatialContent) {
		this.spatialContent = spatialContent;
	}
	
	
}
