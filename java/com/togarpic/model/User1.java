//package com.togarpic.model;
//
//import java.util.Collection;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//
//@Entity
//@Table(name="tbluser",uniqueConstraints = @UniqueConstraint(columnNames = "usr_email"))
//public class User1 {
//	private long id;
//	private String usr_firstName;
//	private String usr_lastName;
//	private String usr_telephone;
//	private String usr_email;
//	private String usr_image;
//	private String password;
//	
//	
//	
//	private Collection<Role> roles;
//
//
//
//	public User1() {
//		
//		// TODO Auto-generated constructor stub
//	}
//
//
//
//	public User1(String usr_firstName, String usr_lastName, String usr_telephone, String usr_email, String usr_image,
//			String password, Collection<Role> roles) {
//		super();
//		this.usr_firstName = usr_firstName;
//		this.usr_lastName = usr_lastName;
//		this.usr_telephone = usr_telephone;
//		this.usr_email = usr_email;
//		this.usr_image = usr_image;
//		this.password = password;
//		this.roles = roles;
//	}
//
//
//
//	public long getId() {
//		return id;
//	}
//
//
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//
//
//	public String getUsr_firstName() {
//		return usr_firstName;
//	}
//
//
//
//	public void setUsr_firstName(String usr_firstName) {
//		this.usr_firstName = usr_firstName;
//	}
//
//
//
//	public String getUsr_lastName() {
//		return usr_lastName;
//	}
//
//
//
//	public void setUsr_lastName(String usr_lastName) {
//		this.usr_lastName = usr_lastName;
//	}
//
//
//
//	public String getUsr_telephone() {
//		return usr_telephone;
//	}
//
//
//
//	public void setUsr_telephone(String usr_telephone) {
//		this.usr_telephone = usr_telephone;
//	}
//
//
//
//	public String getUsr_email() {
//		return usr_email;
//	}
//
//
//
//	public void setUsr_email(String usr_email) {
//		this.usr_email = usr_email;
//	}
//
//
//
//	public String getUsr_image() {
//		return usr_image;
//	}
//
//
//
//	public void setUsr_image(String usr_image) {
//		this.usr_image = usr_image;
//	}
//
//
//
//	public String getPassword() {
//		return password;
//	}
//
//
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//
//	public Collection<Role> getRoles() {
//		return roles;
//	}
//
//
//
//	public void setRoles(Collection<Role> roles) {
//		this.roles = roles;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//}
