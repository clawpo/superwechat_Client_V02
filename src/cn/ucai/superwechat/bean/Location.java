package cn.ucai.superwechat.bean;


/**
 * Location entity. @author MyEclipse Persistence Tools
 */
public class Location extends Avatar implements java.io.Serializable {
	private static final long serialVersionUID = 8197872390536640826L;

	// Fields

	/**
	 * 
	 */
	private Integer MLocationId;
	private Integer MLocationUserId;
	private String MLocationUserName;
	private Double MLocationLatitude;
	private Double MLocationLongitude;
	private Boolean MLocationIsSearched;
	private String MLocationLastUpdateTime;

	// Constructors

	/** default constructor */
	public Location() {
	}

	/** minimal constructor */
	public Location(Integer MLocationUserId, String MLocationUserName,
			Boolean MLocationIsSearched) {
		this.MLocationUserId = MLocationUserId;
		this.MLocationUserName = MLocationUserName;
		this.MLocationIsSearched = MLocationIsSearched;
	}

	/** full constructor */
	public Location(Integer MLocationUserId, String MLocationUserName,
			Double MLocationLatitude, Double MLocationLongitude,
			Boolean MLocationIsSearched, String MLocationLastUpdateTime) {
		this.MLocationUserId = MLocationUserId;
		this.MLocationUserName = MLocationUserName;
		this.MLocationLatitude = MLocationLatitude;
		this.MLocationLongitude = MLocationLongitude;
		this.MLocationIsSearched = MLocationIsSearched;
		this.MLocationLastUpdateTime = MLocationLastUpdateTime;
	}

	// Property accessors
	public Integer getMLocationId() {
		return this.MLocationId;
	}

	public void setMLocationId(Integer MLocationId) {
		this.MLocationId = MLocationId;
	}

	public Integer getMLocationUserId() {
		return this.MLocationUserId;
	}

	public void setMLocationUserId(Integer MLocationUserId) {
		this.MLocationUserId = MLocationUserId;
	}

	public String getMLocationUserName() {
		return this.MLocationUserName;
	}

	public void setMLocationUserName(String MLocationUserName) {
		this.MLocationUserName = MLocationUserName;
	}

	public Double getMLocationLatitude() {
		return this.MLocationLatitude;
	}

	public void setMLocationLatitude(Double MLocationLatitude) {
		this.MLocationLatitude = MLocationLatitude;
	}

	public Double getMLocationLongitude() {
		return this.MLocationLongitude;
	}

	public void setMLocationLongitude(Double MLocationLongitude) {
		this.MLocationLongitude = MLocationLongitude;
	}

	public Boolean getMLocationIsSearched() {
		return this.MLocationIsSearched;
	}

	public void setMLocationIsSearched(Boolean MLocationIsSearched) {
		this.MLocationIsSearched = MLocationIsSearched;
	}

	public String getMLocationLastUpdateTime() {
		return this.MLocationLastUpdateTime;
	}

	public void setMLocationLastUpdateTime(String MLocationLastUpdateTime) {
		this.MLocationLastUpdateTime = MLocationLastUpdateTime;
	}

	@Override
	public String toString() {
		return "Location [MLocationId=" + MLocationId + ", MLocationUserId="
				+ MLocationUserId + ", MLocationUserName=" + MLocationUserName
				+ ", MLocationLatitude=" + MLocationLatitude
				+ ", MLocationLongitude=" + MLocationLongitude
				+ ", MLocationIsSearched=" + MLocationIsSearched
				+ ", MLocationLastUpdateTime=" + MLocationLastUpdateTime + "]";
	}
	

}