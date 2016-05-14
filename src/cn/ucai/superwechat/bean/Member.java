package cn.ucai.superwechat.bean;


/**
 * Member entity. @author MyEclipse Persistence Tools
 */
public class Member extends User implements java.io.Serializable {
	private static final long serialVersionUID = 6913484375413577556L;

	// Fields

	/**
	 * 
	 */
	private Integer MMemberId;
	private Integer MMemberUserId;
	private String MMemberUserName;
	private Integer MMemberGroupId;
	private String MMemberGroupHxid;
	private Integer MMemberPermission;

	// Constructors

	/** default constructor */
	public Member() {
	}

	/** full constructor */
	public Member(Integer MMemberUserId, String MMemberUserName,
			Integer MMemberGroupId, String MMemberGroupHxid,
			Integer MMemberPermission) {
		this.MMemberUserId = MMemberUserId;
		this.MMemberUserName = MMemberUserName;
		this.MMemberGroupId = MMemberGroupId;
		this.MMemberGroupHxid = MMemberGroupHxid;
		this.MMemberPermission = MMemberPermission;
	}

	// Property accessors
	public Integer getMMemberId() {
		return this.MMemberId;
	}

	public void setMMemberId(Integer MMemberId) {
		this.MMemberId = MMemberId;
	}

	public Integer getMMemberUserId() {
		return this.MMemberUserId;
	}

	public void setMMemberUserId(Integer MMemberUserId) {
		this.MMemberUserId = MMemberUserId;
	}

	public String getMMemberUserName() {
		return this.MMemberUserName;
	}

	public void setMMemberUserName(String MMemberUserName) {
		this.MMemberUserName = MMemberUserName;
	}

	public Integer getMMemberGroupId() {
		return this.MMemberGroupId;
	}

	public void setMMemberGroupId(Integer MMemberGroupId) {
		this.MMemberGroupId = MMemberGroupId;
	}

	public String getMMemberGroupHxid() {
		return this.MMemberGroupHxid;
	}

	public void setMMemberGroupHxid(String MMemberGroupHxid) {
		this.MMemberGroupHxid = MMemberGroupHxid;
	}

	public Integer getMMemberPermission() {
		return this.MMemberPermission;
	}

	public void setMMemberPermission(Integer MMemberPermission) {
		this.MMemberPermission = MMemberPermission;
	}

	@Override
	public String toString() {
		return "Member [MMemberId=" + MMemberId + ", MMemberUserId="
				+ MMemberUserId + ", MMemberUserName=" + MMemberUserName
				+ ", MMemberGroupId=" + MMemberGroupId + ", MMemberGroupHxid="
				+ MMemberGroupHxid + ", MMemberPermission=" + MMemberPermission
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Member)) return false;
		if (!super.equals(o)) return false;

		Member member = (Member) o;

		return getMMemberId().equals(member.getMMemberId());

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + getMMemberId().hashCode();
		return result;
	}
}