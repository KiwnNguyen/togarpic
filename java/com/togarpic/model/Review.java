package com.togarpic.model;

public class Review {
	private long rev_id;
	private long usr_id;
	private long odt_id;
	private long pro_id;
	private String rev_content;
	private String usr_firstName;
	private String usr_lastName;
	private String usr_email;
	private String usr_password;
	private int usr_role;
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Review(long rev_id, long usr_id, long odt_id, long pro_id, String rev_content,String usr_firstNane,String usr_lastName,String usr_email,String usr_password ,int usr_role) {
		super();
		this.rev_id = rev_id;
		this.usr_id = usr_id;
		this.odt_id = odt_id;
		this.pro_id = pro_id;
		this.rev_content = rev_content;
		this.usr_firstName = usr_firstNane;
		this.usr_lastName = usr_lastName;
		this.usr_email =usr_email;
		this.usr_password = usr_password;
		this.usr_role = usr_role;
	}
	public long getRev_id() {
		return rev_id;
	}
	public void setRev_id(long rev_id) {
		this.rev_id = rev_id;
	}
	public long getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(long usr_id) {
		this.usr_id = usr_id;
	}
	public long getOdt_id() {
		return odt_id;
	}
	public void setOdt_id(long odt_id) {
		this.odt_id = odt_id;
	}
	public long getPro_id() {
		return pro_id;
	}
	public void setPro_id(long pro_id) {
		this.pro_id = pro_id;
	}
	public String getRev_content() {
		return rev_content;
	}
	public void setRev_content(String rev_content) {
		this.rev_content = rev_content;
	}
	
	public String getUsr_firstName() {
		return usr_firstName;
	}
	public void setUsr_firtName(String usr_firstName) {
		this.usr_firstName = usr_firstName;
	}
	public String getUsr_lastName() {
		return usr_lastName;
	}
	public void setUsr_lastName(String usr_lastName) {
		this.usr_lastName = usr_lastName;
	}
	
	public String getUsr_email() {
		return usr_email;
	}
	public void setUsr_email(String usr_email) {
		this.usr_email = usr_email;
	}
	public String getUsr_password() {
		return usr_password;
	}
	public void setUsr_password(String usr_password) {
		this.usr_password = usr_password;
	}
	public void setUsr_firstName(String usr_firstName) {
		this.usr_firstName = usr_firstName;
	}
	
	public int getUsr_role() {
		return usr_role;
	}
	public void setUsr_role(int usr_role) {
		this.usr_role = usr_role;
	}
	@Override
	public String toString() {
		return "Review [rev_id=" + rev_id + ", usr_id=" + usr_id + ", odt_id=" + odt_id + ", pro_id=" + pro_id
				+ ", rev_content=" + rev_content + ", usr_firtName=" + usr_firstName + ", usr_lastName=" + usr_lastName
				+ "]";
	}
	
	
	
}
