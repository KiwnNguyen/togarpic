package com.controller.Model;

public class recipe {
	private long rec_id;
	private String rec_name;
	public recipe() {
		super();
		// TODO Auto-generated constructor stub
	}
	public recipe(long rec_id, String rec_name) {
		super();
		this.rec_id = rec_id;
		this.rec_name = rec_name;
	}
	public long getRec_id() {
		return rec_id;
	}
	public void setRec_id(long rec_id) {
		this.rec_id = rec_id;
	}
	public String getRec_name() {
		return rec_name;
	}
	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}
	@Override
	public String toString() {
		return rec_id + "===" + rec_name;
	}
	
	
}
