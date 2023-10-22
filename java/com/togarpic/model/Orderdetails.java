package com.togarpic.model;

public class Orderdetails {
	private long odt_id;
	private long ord_id;
	private long sto_id;
	private int odt_quantity;
	private float odt_importPrice;
	private float odt_exportPrice;
	public Orderdetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Orderdetails(long odt_id, long ord_id, long sto_id, int odt_quantity, float odt_importPrice,
			float odt_exportPrice) {
		super();
		this.odt_id = odt_id;
		this.ord_id = ord_id;
		this.sto_id = sto_id;
		this.odt_quantity = odt_quantity;
		this.odt_importPrice = odt_importPrice;
		this.odt_exportPrice = odt_exportPrice;
	}
	public long getOdt_id() {
		return odt_id;
	}
	public void setOdt_id(long odt_id) {
		this.odt_id = odt_id;
	}
	public long getOrd_id() {
		return ord_id;
	}
	public void setOrd_id(long ord_id) {
		this.ord_id = ord_id;
	}
	public long getSto_id() {
		return sto_id;
	}
	public void setSto_id(long sto_id) {
		this.sto_id = sto_id;
	}
	public int getOdt_quantity() {
		return odt_quantity;
	}
	public void setOdt_quantity(int odt_quantity) {
		this.odt_quantity = odt_quantity;
	}
	public float getOdt_importPrice() {
		return odt_importPrice;
	}
	public void setOdt_importPrice(float odt_importPrice) {
		this.odt_importPrice = odt_importPrice;
	}
	public float getOdt_exportPrice() {
		return odt_exportPrice;
	}
	public void setOdt_exportPrice(float odt_exportPrice) {
		this.odt_exportPrice = odt_exportPrice;
	}
	@Override
	public String toString() {
		return "orderdetails [odt_id=" + odt_id + ", ord_id=" + ord_id + ", sto_id=" + sto_id + ", odt_quantity="
				+ odt_quantity + ", odt_importPrice=" + odt_importPrice + ", odt_exportPrice=" + odt_exportPrice + "]";
	}
	
	
	
	
}