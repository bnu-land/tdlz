package cn.edu.bnu.land.model;

// Generated 2014-4-22 17:47:25 by Hibernate Tools 4.0.0

/**
 * FkDikuai generated by hbm2java
 */
public class FkDikuai implements java.io.Serializable {

	private Integer recordId;
	private String landId;
	private String projectId;
	private String projectname;
	private String ownershipUnits;
	private String personRights;
	private String landLocation;
	private String propertyNumber;
	private String fuKenhouPropertyNumber;
	private String polygonNumber;
	private String mapSheetNumber;
	private String fukenqianLandUse;
	private String fukenArea;
	private String newHousingArea;
	private String whereAbouts;
	private String chanQuanzhengTupian;

	public FkDikuai() {
	}

	public FkDikuai(String landId, String projectId) {
		this.landId = landId;
		this.projectId = projectId;
	}

	public FkDikuai(String landId, String projectId, String projectname,
			String ownershipUnits, String personRights, String landLocation,
			String propertyNumber, String fuKenhouPropertyNumber,
			String polygonNumber, String mapSheetNumber,
			String fukenqianLandUse, String fukenArea, String newHousingArea,
			String whereAbouts, String chanQuanzhengTupian) {
		this.landId = landId;
		this.projectId = projectId;
		this.projectname = projectname;
		this.ownershipUnits = ownershipUnits;
		this.personRights = personRights;
		this.landLocation = landLocation;
		this.propertyNumber = propertyNumber;
		this.fuKenhouPropertyNumber = fuKenhouPropertyNumber;
		this.polygonNumber = polygonNumber;
		this.mapSheetNumber = mapSheetNumber;
		this.fukenqianLandUse = fukenqianLandUse;
		this.fukenArea = fukenArea;
		this.newHousingArea = newHousingArea;
		this.whereAbouts = whereAbouts;
		this.chanQuanzhengTupian = chanQuanzhengTupian;
	}

	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getLandId() {
		return this.landId;
	}

	public void setLandId(String landId) {
		this.landId = landId;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getOwnershipUnits() {
		return this.ownershipUnits;
	}

	public void setOwnershipUnits(String ownershipUnits) {
		this.ownershipUnits = ownershipUnits;
	}

	public String getPersonRights() {
		return this.personRights;
	}

	public void setPersonRights(String personRights) {
		this.personRights = personRights;
	}

	public String getLandLocation() {
		return this.landLocation;
	}

	public void setLandLocation(String landLocation) {
		this.landLocation = landLocation;
	}

	public String getPropertyNumber() {
		return this.propertyNumber;
	}

	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public String getFuKenhouPropertyNumber() {
		return this.fuKenhouPropertyNumber;
	}

	public void setFuKenhouPropertyNumber(String fuKenhouPropertyNumber) {
		this.fuKenhouPropertyNumber = fuKenhouPropertyNumber;
	}

	public String getPolygonNumber() {
		return this.polygonNumber;
	}

	public void setPolygonNumber(String polygonNumber) {
		this.polygonNumber = polygonNumber;
	}

	public String getMapSheetNumber() {
		return this.mapSheetNumber;
	}

	public void setMapSheetNumber(String mapSheetNumber) {
		this.mapSheetNumber = mapSheetNumber;
	}

	public String getFukenqianLandUse() {
		return this.fukenqianLandUse;
	}

	public void setFukenqianLandUse(String fukenqianLandUse) {
		this.fukenqianLandUse = fukenqianLandUse;
	}

	public String getFukenArea() {
		return this.fukenArea;
	}

	public void setFukenArea(String fukenArea) {
		this.fukenArea = fukenArea;
	}

	public String getNewHousingArea() {
		return this.newHousingArea;
	}

	public void setNewHousingArea(String newHousingArea) {
		this.newHousingArea = newHousingArea;
	}

	public String getWhereAbouts() {
		return this.whereAbouts;
	}

	public void setWhereAbouts(String whereAbouts) {
		this.whereAbouts = whereAbouts;
	}

	public String getChanQuanzhengTupian() {
		return this.chanQuanzhengTupian;
	}

	public void setChanQuanzhengTupian(String chanQuanzhengTupian) {
		this.chanQuanzhengTupian = chanQuanzhengTupian;
	}

}
