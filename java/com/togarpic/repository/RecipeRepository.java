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
				return item;
			} catch (SQLException e) {
				throw e;
			}
		}

	}

	public List<Recipe> findAll() {
		return db.query("exec showAllRecipe", new RecipeRowMapper());
	}

	public Recipe findById(int id) {
		return db.queryForObject("exec showRecipeById ?", new RecipeRowMapper(), new Object[] { id });
	}

	public int insert(Recipe recipe) {
		return db.update("exec insertRecipe ?",
				new Object[] { recipe.getRec_name() });
	}

	public int deleteById(int id) {
		return db.update("exec deleteRecipe ?", new Object[] { id });
	}

	public int update(Recipe recipe) {
		return db.update("exec updateRecipe ?, ?",
				new Object[] { recipe.getRec_name(), recipe.getId() });
	}

}
