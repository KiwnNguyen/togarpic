package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.Recipe;

@Repository
public class RecipeRepository {
	@Autowired
	JdbcTemplate db;

	class RecipeRowMapper implements RowMapper<Recipe> {
		@Override
		public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				Recipe item = new Recipe();
				item.setId(rs.getInt("rec_id"));
				item.setRec_name(rs.getString("rec_name"));
				item.setRec_image(rs.getString("rec_image"));
				item.setRec_content(rs.getString("rec_content"));
				return item;
			} catch (SQLException e) {
				throw e;
			}
		}

	}

	public List<Recipe> findAll() {
		return db.query("exec showRecipeClient", new RecipeRowMapper());
	}
	
	

	public Recipe findById(int id) {
		return db.queryForObject("exec showRecipeById ?", new RecipeRowMapper(), new Object[] { id });
	}

	public Recipe findByName(String rec_name) {
		return db.queryForObject("exec findRecipeByName ?", new RecipeRowMapper(), new Object[] { rec_name });
	}
	
	public int insert(Recipe recipe) {
		return db.update("exec insertRecipe ?, ?, ?",
				new Object[] { recipe.getRec_name(), recipe.getRec_image(), recipe.getRec_content() });
	}

	public int deleteById(int id) {
		return db.update("exec deleteRecipe ?", new Object[] { id });
	}

	public int update(Recipe recipe) {
		return db.update("exec updateRecipe ?, ?, ?",
				new Object[] { recipe.getRec_name(), recipe.getRec_content(), recipe.getId() });
	}

}
