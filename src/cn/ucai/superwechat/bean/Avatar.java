package cn.ucai.superwechat.bean;



public class Avatar extends Message implements java.io.Serializable {
	private static final long serialVersionUID = 2137809455396377048L;

	// Fields

	/**
	 * 
	 */
	private Integer MAvatarId;
	private Integer MAvatarUserId;
	private String MAvatarUserName;
	private String MAvatarPath;
	private Integer MAvatarType;

	// Constructors

	/** default constructor */
	public Avatar() {
	}

	/** full constructor */
	public Avatar(Integer MAvatarUserId, String MAvatarUserName,
			String MAvatarPath, Integer MAvatarType) {
		this.MAvatarUserId = MAvatarUserId;
		this.MAvatarUserName = MAvatarUserName;
		this.MAvatarPath = MAvatarPath;
		this.MAvatarType = MAvatarType;
	}

	// Property accessors
	public Integer getMAvatarId() {
		return this.MAvatarId;
	}

	public void setMAvatarId(Integer MAvatarId) {
		this.MAvatarId = MAvatarId;
	}

	public Integer getMAvatarUserId() {
		return this.MAvatarUserId;
	}

	public void setMAvatarUserId(Integer MAvatarUserId) {
		this.MAvatarUserId = MAvatarUserId;
	}

	public String getMAvatarUserName() {
		return this.MAvatarUserName;
	}

	public void setMAvatarUserName(String MAvatarUserName) {
		this.MAvatarUserName = MAvatarUserName;
	}

	public String getMAvatarPath() {
		return this.MAvatarPath;
	}

	public void setMAvatarPath(String MAvatarPath) {
		this.MAvatarPath = MAvatarPath;
	}

	public Integer getMAvatarType() {
		return this.MAvatarType;
	}

	public void setMAvatarType(Integer MAvatarType) {
		this.MAvatarType = MAvatarType;
	}

	@Override
	public String toString() {
		return "Avatar [MAvatarId=" + MAvatarId + ", MAvatarUserId="
				+ MAvatarUserId + ", MAvatarUserName=" + MAvatarUserName
				+ ", MAvatarPath=" + MAvatarPath + ", MAvatarType="
				+ MAvatarType + "]";
	}
	
	

}