package com.controller.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.controller.Model.product;




@Repository
public class ProductRepository {
	@Autowired
	JdbcTemplate db;

	/***
	 * 
	 * @return Build objects in table product connection in SQL Server 
	 */
	class CategoryRowMapper implements RowMapper<product> {
		@Override
		public product mapRow(ResultSet rs, int rowNum) throws SQLException {
			product item = new product();
			item.setPro_id(rs.getLong("pro_id"));
			item.setPro_name(rs.getString("pro_name"));
			item.setPro_price(rs.getFloat("pro_price"));
			item.setPro_image(rs.getString("pro_image"));
			item.setPro_enable(rs.getBoolean("pro_enable"));
			item.setCat_id(rs.getLong("cat_id"));		
			return item;
		}
	}
	/***
	 * 
	 * @return select table category
	 */
	public List<product> findAll() {
		try {
			return db.query("select * from tblproduct", new CategoryRowMapper());
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
	public product findById(long id) {
		try {
			return db.queryForObject("select * from tblproduct where pro_id=?", new CategoryRowMapper(),
			new Object[] { id });
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");
			
			
		}
		
	}
	 public int deleteById(long id) {
			try {
				return db.update("delete from tblproduct where pro_id=?", 
				new Object[] { id });
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error delete!!");
				
				
			}
		   
	  }
	 public int insert(product Product) {
			try {
				//Chưa thêm cat_id vào
				  return db.update("insert into category (pro_name,pro_price,pro_image) " +"values( ?, ?,?)", 
				  new Object[] { Product.getPro_name(),Product.getPro_price(),Product.getPro_image()}); 
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error insert!!");
				
				
			}
		
	  
	  }
}
