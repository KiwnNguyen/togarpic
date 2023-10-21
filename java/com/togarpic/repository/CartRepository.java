package com.togarpic.repository;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.Cart;
import com.togarpic.model.CartView;

@Repository
public class CartRepository {
	@Autowired
	JdbcTemplate db;

	/***
	 * 
	 * @return Build objects in table product connection in SQL Server 
	 */
	class CartRowMapper implements RowMapper<Cart> {
		@Override
		public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
			Cart item = new Cart(rowNum, rowNum);
			item.setCar_id(rs.getLong("car_id"));
			item.setUsr_id(rs.getLong("usr_id"));
			return item;
		}
	}
	
	class CartNewRowMapper implements RowMapper<CartView> {

		@Override
		public CartView mapRow(ResultSet rs, int rowNum) throws SQLException {
			CartView item = new CartView();
			item.setCar_id(rs.getLong("car_id"));
			item.setUsr_id(rs.getLong("usr_id"));
			item.setUsr_name(rs.getString("usr_lastName"));
			return item;
		}
		
	}
	/***
	 * 
	 * @return select table category
	 */
	public List<Cart> findAll1() {
		try {
			return db.query("showAllCart", new CartRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error!!");	
		}
	}
	public List<CartView> findCart() {
		try {
			return db.query("showCart", new CartNewRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error!!");	
		}
	}
	
	
	
	public Cart findById(long id) {
		return db.queryForObject("exec showAllCartById ?", new CartRowMapper(), new Object[] { id });
	}
	
	  public int insert(Cart cart) {
			try {
			  return db.update("exec insertCart ?", new Object[] {cart.getUsr_id()});} 
			catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error inserting cart!!");
				
			}
		  }
	/***
	 * 
	 * @param id
	 * @return select value in table category with id specify 
	 */
//	public User findById(long id) {
//		try {
//			return db.queryForObject("select * from tbluser where usr_id=?", new CategoryRowMapper(),
//			new Object[] { id });
//		}catch(Exception ec) {
//			ec.printStackTrace();
//			throw new RuntimeException("Error!!");
//			
//			
//		}
//		
//	}
	 public int deleteById(long id) {
			try {
				return db.update("exec DeleteCart ?", 
				new Object[] { id });
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error delete!!");
				
				
			}
		   
	  }
	 public long update(Cart cart) {
			return db.update("exec updateCart ?, @car_id = ?",
					new Object[] { cart.getUsr_id(), cart.getCar_id()});
		}
	
	}

