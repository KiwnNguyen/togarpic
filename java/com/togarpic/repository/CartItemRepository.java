package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.*;

@Repository
public class CartItemRepository {
	@Autowired
	JdbcTemplate db;

	class CartItemViewRowMapper implements RowMapper<CartVieww> {
		@Override
		public CartVieww mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				CartVieww item = new CartVieww();
				item.setCart_id(rs.getInt("car_id"));
				item.setPro_id(rs.getInt("pro_id"));
				item.setPro_name(rs.getString("pro_name"));
				item.setPro_price(rs.getFloat("pro_price"));
				item.setQuantity(rs.getInt("cai_quantity"));
				return item;
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	
	public List<Product> findProduct() {
		try {
			return db.query("select * from tblproduct", new ProductRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error!!");	
		}
	}
	
	
	public List<CartVieww> findProdOfCartByCartId(int idcart){
		return db.query("exec showProdOfCartByCartId ?", new CartItemViewRowMapper(), new Object[] { idcart });
		
	}
	
	
}