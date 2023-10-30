package com.togarpic.model;

public class User {
	private long usr_id;
	private String usr_firstName;
	private String usr_lastName;
	private String usr_telephone;
	private String usr_email;
	private String usr_image;
	private String usr_password;
	private String usr_role;
	private int usr_enable;
	public User() {
		super();
	}
	
	public User(long usr_id, String usr_firstName, String usr_lastName, String usr_telephone, String usr_email,
			String usr_image, String usr_password, String usr_role, int usr_enable) {
		super();
		this.usr_id = usr_id;
		this.usr_firstName = usr_firstName;
		this.usr_lastName = usr_lastName;
		this.usr_telephone = usr_telephone;
		this.usr_email = usr_email;
		this.usr_image = usr_image;
		this.usr_password = usr_password;
		this.usr_role = usr_role;
		this.usr_enable = usr_enable;
	}


	public long getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(long usr_id) {
		this.usr_id = usr_id;
	}
	public String getUsr_firstName() {
		return usr_firstName;
	}
	public void setUsr_firstName(String usr_firstName) {
		this.usr_firstName = usr_firstName;
	}
	public String getUsr_lastName() {
		return usr_lastName;
	}
	public void setUsr_lastName(String usr_lastName) {
		this.usr_lastName = usr_lastName;
	}
	public String getUsr_telephone() {
		return usr_telephone;
	}
	public void setUsr_telephone(String usr_telephone) {
		this.usr_telephone = usr_telephone;
	}
	public String getUsr_email() {
		return usr_email;
	}
	public void setUsr_email(String usr_email) {
		this.usr_email = usr_email;
	}
	public String getUsr_image() {
		return usr_image;
	}
	public void setUsr_image(String usr_image) {
		this.usr_image = usr_image;
	}
	public String getUsr_password() {
		return usr_password;
	}
	public void setUsr_password(String usr_password) {
		this.usr_password = usr_password;
	}
	public String getUsr_role() {
		return usr_role;
	}
	public void setUsr_role(String role) {
		this.usr_role = role;
	}
	


	public int getUsr_enable() {
		return usr_enable;
	}

	public void setUsr_enable(int usr_enable) {
		this.usr_enable = usr_enable;
	}

	@Override
	public String toString() {
		return "User [usr_id=" + usr_id + ", usr_firstName=" + usr_firstName + ", usr_lastName=" + usr_lastName
				+ ", usr_telephone=" + usr_telephone + ", usr_email=" + usr_email + ", usr_image=" + usr_image
				+ ", usr_password=" + usr_password + ", usr_role=" + usr_role + ", usr_enable=" + usr_enable
				+ ", getUsr_id()=" + getUsr_id() + ", getUsr_firstName()=" + getUsr_firstName() + ", getUsr_lastName()="
				+ getUsr_lastName() + ", getUsr_telephone()=" + getUsr_telephone() + ", getUsr_email()="
				+ getUsr_email() + ", getUsr_image()=" + getUsr_image() + ", getUsr_password()=" + getUsr_password()
				+ ", getUsr_role()=" + getUsr_role() + ", getUsr_enable()=" + getUsr_enable() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}



	


}
