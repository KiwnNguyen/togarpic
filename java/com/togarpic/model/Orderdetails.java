package com.togarpic.model;

public class Orderdetails {
	private long odt_id;
	private long ord_id;
	private long sto_id;
	private int odt_quantity;
	private float odt_importPrice;
	private float odt_exportPrice;
	private String pro_name;
	private float pro_price;
	private long pro_id;
	
	public long getPro_id() {
		return pro_id;
	}

	public void setPro_id(long pro_id) {
		this.pro_id = pro_id;
	}

	public Orderdetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public float getPro_price() {
		return pro_price;
	}

	public void setPro_price(float pro_price) {
		this.pro_price = pro_price;
	}

	

	public Orderdetails(long odt_id, long ord_id, long sto_id, int odt_quantity, float odt_importPrice,
			float odt_exportPrice, String pro_name, float pro_price, float sto_price, long pro_id) {
		super();
		this.odt_id = odt_id;
		this.ord_id = ord_id;
		this.sto_id = sto_id;
		this.odt_quantity = odt_quantity;
		this.odt_importPrice = odt_importPrice;
		this.odt_exportPrice = odt_exportPrice;
		this.pro_name = pro_name;
		this.pro_price = pro_price;
//		this.sto_price = sto_price;
		this.pro_id = pro_id;
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
