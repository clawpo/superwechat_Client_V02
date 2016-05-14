package cn.ucai.superwechat.bean;


/**
 * Group entity. @author MyEclipse Persistence Tools
 */
public class Group extends Location implements java.io.Serializable {
	private static final long serialVersionUID = -2853820541320732366L;

	// Fields

	/**
	 * 
	 */
	private Integer MGroupId;
	private String MGroupHxid;
	private String MGroupName;
	private String MGroupDescription;
	private String MGroupOwner;
	private String MGroupLastModifiedTime;
	private Integer MGroupMaxUsers;
	private Integer MGroupAffiliationsCount;
	private Boolean MGroupIsPublic;
	private Boolean MGroupAllowInvites;

	// Constructors

	/** default constructor */
	public Group() {
	}

	/** minimal constructor */
	public Group(String MGroupOwner, String MGroupLastModifiedTime,
			Integer MGroupAffiliationsCount, Boolean MGroupIsPublic,
			Boolean MGroupAllowInvites) {
		this.MGroupOwner = MGroupOwner;
		this.MGroupLastModifiedTime = MGroupLastModifiedTime;
		this.MGroupAffiliationsCount = MGroupAffiliationsCount;
		this.MGroupIsPublic = MGroupIsPublic;
		this.MGroupAllowInvites = MGroupAllowInvites;
	}

	/** full constructor */
	public Group(String MGroupHxid, String MGroupName,
			String MGroupDescription, String MGroupOwner,
			String MGroupLastModifiedTime, Integer MGroupMaxUsers,
			Integer MGroupAffiliationsCount, Boolean MGroupIsPublic,
			Boolean MGroupAllowInvites) {
		this(MGroupOwner, MGroupLastModifiedTime, MGroupAffiliationsCount,
				MGroupIsPublic, MGroupAllowInvites);
		this.MGroupHxid = MGroupHxid;
		this.MGroupName = MGroupName;
		this.MGroupDescription = MGroupDescription;
		this.MGroupMaxUsers = MGroupMaxUsers;
	}

	public Group(boolean result, int msg) {
		this.setResult(result);
		this.setMsg(msg);
	}

	// Property accessors
	public Integer getMGroupId() {
		return this.MGroupId;
	}

	public void setMGroupId(Integer MGroupId) {
		this.MGroupId = MGroupId;
	}

	public String getMGroupHxid() {
		return this.MGroupHxid;
	}

	public void setMGroupHxid(String MGroupHxid) {
		this.MGroupHxid = MGroupHxid;
	}

	public String getMGroupName() {
		return this.MGroupName;
	}

	public void setMGroupName(String MGroupName) {
		this.MGroupName = MGroupName;
	}

	public String getMGroupDescription() {
		return this.MGroupDescription;
	}

	public void setMGroupDescription(String MGroupDescription) {
		this.MGroupDescription = MGroupDescription;
	}

	public String getMGroupOwner() {
		return this.MGroupOwner;
	}

	public void setMGroupOwner(String MGroupOwner) {
		this.MGroupOwner = MGroupOwner;
	}

	public String getMGroupLastModifiedTime() {
		return this.MGroupLastModifiedTime;
	}

	public void setMGroupLastModifiedTime(String MGroupLastModifiedTime) {
		this.MGroupLastModifiedTime = MGroupLastModifiedTime;
	}

	public Integer getMGroupMaxUsers() {
		return this.MGroupMaxUsers;
	}

	public void setMGroupMaxUsers(Integer MGroupMaxUsers) {
		this.MGroupMaxUsers = MGroupMaxUsers;
	}

	public Integer getMGroupAffiliationsCount() {
		return this.MGroupAffiliationsCount;
	}

	public void setMGroupAffiliationsCount(Integer MGroupAffiliationsCount) {
		this.MGroupAffiliationsCount = MGroupAffiliationsCount;
	}

	public Boolean getMGroupIsPublic() {
		return this.MGroupIsPublic;
	}

	public void setMGroupIsPublic(Boolean MGroupIsPublic) {
		this.MGroupIsPublic = MGroupIsPublic;
	}

	public Boolean getMGroupAllowInvites() {
		return this.MGroupAllowInvites;
	}

	public void setMGroupAllowInvites(Boolean MGroupAllowInvites) {
		this.MGroupAllowInvites = MGroupAllowInvites;
	}

	@Override
	public String toString() {
		return "Group [MGroupId=" + MGroupId + ", MGroupHxid=" + MGroupHxid
				+ ", MGroupName=" + MGroupName + ", MGroupDescription="
				+ MGroupDescription + ", MGroupOwner=" + MGroupOwner
				+ ", MGroupLastModifiedTime=" + MGroupLastModifiedTime
				+ ", MGroupMaxUsers=" + MGroupMaxUsers
				+ ", MGroupAffiliationsCount=" + MGroupAffiliationsCount
				+ ", MGroupIsPublic=" + MGroupIsPublic
				+ ", MGroupAllowInvites=" + MGroupAllowInvites + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Group)) return false;

		Group group = (Group) o;

		return getMGroupId().equals(group.getMGroupId());

	}

	@Override
	public int hashCode() {
		return getMGroupId().hashCode();
	}
}