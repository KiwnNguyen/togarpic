package com.controller.Repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.controller.Model.CateGory;



@Repository
public class CategoryRepository {
	@Autowired
	JdbcTemplate db;

	/***
	 * 
	 * @return Build object in table category connection in SQL Server 
	 */
	class CategoryRowMapper implements RowMapper<CateGory> {
		@Override
		public CateGory mapRow(ResultSet rs, int rowNum) throws SQLException {
			CateGory item = new CateGory();
			item.setId(rs.getInt("id"));
			item.setName(rs.getString("name"));
			item.setImageCate(rs.getString("imageCate"));
			item.setPrice(rs.getFloat("price"));
			
			
		
			return item;
		}
	}
	/***
	 * 
	 * @return select table category
	 */
	public List<CateGory> findAll() {
		try {
			return db.query("select * from category", new CategoryRowMapper());
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
	public CateGory findById(int id) {
		try {
			return db.queryForObject("select * from category where id=?", new CategoryRowMapper(),
			new Object[] { id });
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");
			
			
		}
		
	}
	/***
	 * 
	 * @param id
	 * @return Delete value category
	 */
	 public int deleteById(int id) {
			try {
				return db.update("delete from category where id=?", 
				new Object[] { id });
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error delete!!");
				
				
			}
		   
	  }
	 /***
	  * 
	  * @param Category
	  * @return insert value in table category
	  */
	  
	  public int insert(CateGory Category) {
		
		  return db.update("insert into category (id,name,imageCate,price) " +"values( ?, ?,?,?)", 
		  new Object[] { Category.getId(),Category.getName(), Category.getImageCate() , Category.getPrice(),}); 
	  
	  }
	  
	/***
	 * 	
	 * @param Category
	 * @return update value in table category
	 */
	 public int update(CateGory Category) { 
		  return db.update("update category "+" set name = ? , price = ? , imageCate = ? " + " where id = ?",
	     new Object[] { Category.getName(),Category.getPrice(),Category.getImageCate(), Category.getId() }); 
	}
	
	 /***
	  * 
	  * @param name
	  * @return search value in table category
	  */
	public List<CateGory> getCateByFilter(String name){
		try {
			String sql = "select *from category where name like ? ";
			
			
			Object[] params= {"%"+name+"%"};
			
			RowMapper<CateGory> rowMapper = new BeanPropertyRowMapper<>(CateGory.class);
			
			return db.query(sql, params, rowMapper);
			
			
		}catch(Exception ex) {
			
			
		}
		return null;
		
	}
	
	
	
	
	
	 

	 
}
