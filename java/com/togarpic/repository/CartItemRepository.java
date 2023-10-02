package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.CartItem;

@Repository
public class CartItemRepository {
	@Autowired
	JdbcTemplate db;

	/***
	 * 
	 * @return Build objects in table product connection in SQL Server 
	 */
	class CartItemRowMapper implements RowMapper<CartItem> {
		@Override
		public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			CartItem item = new CartItem();
			item.setCar_id(rs.getLong("car_id"));
			item.setPro_id(rs.getLong("pro_id"));
			item.setCai_quantity(rs.getFloat("cai_quantity"));
			return item;
		}
	}
	/***
	 * 
	 * @return select table category
	 */
	public List<CartItem> findAll2() {
		try {
			return db.query("SelectAllCartItem", new CartItemRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error!!");	
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
				return db.update("delete from tblcart_item where pro_id=?", 
				new Object[] { id });
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error delete!!");
				
				
			}
		   
	  }
}
