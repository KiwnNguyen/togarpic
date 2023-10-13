package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.RecipeDetailsProductView;
import com.togarpic.model.recipedetails.*;


@Repository
public class RecipeDetailsRepository {

	@Autowired
	JdbcTemplate db;

	class RecipeDetailsRowMapper implements RowMapper<RecipeDetails> {
		@Override
		public RecipeDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				RecipeDetails item = new RecipeDetails();
				item.setId(rs.getInt("rdt_id"));
				item.setRecipe_id(rs.getInt("rec_id"));
				item.setProduct_id(rs.getInt("pro_id"));
				item.setQuantity(rs.getString("rdt_quantity"));
				return item;
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	
	
	public List<RecipeDetails> findAll() {
		return db.query("exec showAllRecipeDetails", new RecipeDetailsRowMapper());
	}

	public RecipeDetails findById(int id) {
		return db.queryForObject("exec showRecipeDetailsById ?", new RecipeDetailsRowMapper(), new Object[] { id });
	}
	
	public int insert(RecipeDetails recipedetails) {
		return db.update("exec insertRecipeDetails ?,?,?",
				new Object[] { recipedetails.getRecipe_id(), recipedetails.getProduct_id(), recipedetails.getQuantity() });
	}
	
	public int deleteById(int id) {
		return db.update("exec deleteRecipeDetails ?", new Object[] { id });
	}
	
	public int update(RecipeDetails recipedetails) {
		return db.update("exec updateRecipeDetails ?, ?, ?, ?",
				new Object[] { recipedetails.getRecipe_id(), recipedetails.getProduct_id(), recipedetails.getQuantity(), recipedetails.getId() });
	}
	
	
}
