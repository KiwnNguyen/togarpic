package com.controller.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.controller.Model.recipe;




@Repository
public class RecipeRepository {
	@Autowired
	JdbcTemplate db;
	/***
	 * 
	 * @return Build objects in table recipe connection in SQL Server 
	 */
	class CategoryRowMapper implements RowMapper<recipe> {
		@Override
		public recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
			recipe item = new recipe();
			item.setRec_id(rs.getLong("rec_id"));
			item.setRec_name(rs.getString("rec_name"));
			return item;
		}
	}
	public List<recipe> findAll1() {
		try {
			return db.query("select * from tblrecipe", new CategoryRowMapper());
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
	public recipe findById(long id) {
		try {
			return db.queryForObject("select * from tblrecipe where rec_id=?", new CategoryRowMapper(),
			new Object[] { id });
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");
			
			
		}
		
	}
	 public int deleteById(long id) {
			try {
				return db.update("delete from tblrecipe where rec_id=?", 
				new Object[] { id });
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error delete!!");
				
				
			}
		   
	  }
}
