package cn.ucai.superwechat.bean;

/**
 * Contact entity. @author MyEclipse Persistence Tools
 */
public class Contact extends User implements java.io.Serializable {
	private static final long serialVersionUID = -2183229871248294573L;

	/**
	 * 
	 */
	// Fields

	private Integer MContactId;
	private Integer MContactUserId;
	private String MContactUserName;
	private Integer MContactCid;
	private String MContactCname;

	// Constructors

	/** default constructor */
	public Contact() {
	}

	/** full constructor */
	public Contact(Integer MContactId, Integer MContactUserId, String MContactUserName,
			Integer MContactCid, String MContactCname) {
		this.MContactId = MContactId;
		this.MContactUserId = MContactUserId;
		this.MContactUserName = MContactUserName;
		this.MContactCid = MContactCid;
		this.MContactCname = MContactCname;
	}

	// Property accessors
	public Integer getMContactId() {
		return this.MContactId;
	}

	public void setMContactId(Integer MContactId) {
		this.MContactId = MContactId;
	}

	public Integer getMContactUserId() {
		return this.MContactUserId;
	}

	public void setMContactUserId(Integer MContactUserId) {
		this.MContactUserId = MContactUserId;
	}

	public String getMContactUserName() {
		return this.MContactUserName;
	}

	public void setMContactUserName(String MContactUserName) {
		this.MContactUserName = MContactUserName;
	}

	public Integer getMContactCid() {
		return this.MContactCid;
	}

	public void setMContactCid(Integer MContactCid) {
		this.MContactCid = MContactCid;
	}

	public String getMContactCname() {
		return this.MContactCname;
	}

	public void setMContactCname(String MContactCname) {
		this.MContactCname = MContactCname;
	}

	@Override
	public String toString() {
		return "Contact [MContactId=" + MContactId + ", MContactUserId="
				+ MContactUserId + ", MContactUserName=" + MContactUserName
				+ ", MContactCid=" + MContactCid + ", MContactCname="
				+ MContactCname + "]";
	}

	
}