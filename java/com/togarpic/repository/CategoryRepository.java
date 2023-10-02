package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.Category;

@Repository
public class CategoryRepository {
	@Autowired
	JdbcTemplate db;

	class CategoryRowMapper implements RowMapper<Category> {
		@Override
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				Category item = new Category();
				item.setId(rs.getInt("cat_id"));
				item.setCat_name(rs.getString("cat_name"));
				return item;
			} catch (SQLException e) {
				throw e;
			}

		}
	}

	public List<Category> findAll() {
		return db.query("exec showAllCategory", new CategoryRowMapper());
	}

	public Category findById(int id) {
		return db.queryForObject("exec showCategoryById ?", new CategoryRowMapper(), new Object[] { id });
	}
	
	public int insert(Category category) {
		return db.update("exec insertCategory ?",
				new Object[] { category.getCat_name() });
	}
	
	public int deleteById(int id) {
		return db.update("exec deleteCategory ?", new Object[] { id });
	}
	
	public int update(Category Category) {
		return db.update("exec updateCategory ?, ?",
				new Object[] { Category.getCat_name(), Category.getId() });
	}

}
