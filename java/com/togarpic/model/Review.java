package com.togarpic.model;

public class Review {
	private long rev_id;
	private long usr_id;
	private long odt_id;
	private long pro_id;
	private String rev_content;
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Review(long rev_id, long usr_id, long odt_id, long pro_id, String rev_content) {
		super();
		this.rev_id = rev_id;
		this.usr_id = usr_id;
		this.odt_id = odt_id;
		this.pro_id = pro_id;
		this.rev_content = rev_content;
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
	@Override
	public String toString() {
		return "review [rev_id=" + rev_id + ", usr_id=" + usr_id + ", odt_id=" + odt_id + ", pro_id=" + pro_id
				+ ", rev_content=" + rev_content + "]";
	}
	
	
	
}
