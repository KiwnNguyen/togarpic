package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.Product;
<<<<<<< Updated upstream
=======
import com.togarpic.model.ProductView;
>>>>>>> Stashed changes

@Repository
public class ProductRepository {
	@Autowired
	JdbcTemplate db;

	class ProductRowMapper implements RowMapper<Product> {

		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

			try {
				Product item = new Product();
				item.setPro_id(rs.getInt("pro_id"));
				item.setPro_name(rs.getString("pro_name"));
				item.setPro_price(rs.getFloat("pro_price"));
				item.setPro_image(rs.getString("pro_image"));
				item.setPro_enable(rs.getInt("pro_enable"));
				item.setCat_id(rs.getInt("cat_id"));
<<<<<<< Updated upstream
				item.setStt(rs.getInt("stt"));
=======
>>>>>>> Stashed changes
				return item;
			} catch (SQLException e) {
				throw e;
			}
		}
<<<<<<< Updated upstream

	}

	public Product findById(int id) {
		return db.queryForObject("exec showProductById ?", new ProductRowMapper(), new Object[] { id });
=======
	}

	public Product findById(long newparlong) {
		return db.queryForObject("exec showProductById ?", new ProductRowMapper(), new Object[] { newparlong });
>>>>>>> Stashed changes
	}

	public int insert(Product product) {
		return db.update("exec insertProduct ?,?,?,?", new Object[] { product.getPro_name(), product.getPro_price(),
				product.getPro_image(), product.getCat_id() });
	}

	public long deleteById(long id) {
		return db.update("exec deleteProduct ?", new Object[] { id });
	}

	public int update(Product product) {
		return db.update("exec updateProduct ?, ?, ?, ?, ?", new Object[] { product.getPro_id(), product.getPro_name(),
				product.getPro_price(), product.getPro_image(), product.getCat_id() });
	}

	public List<Product> findAll() {
		return db.query("exec showAllProduct", new ProductRowMapper());
	}

<<<<<<< Updated upstream
=======
	class ProductViewRowMapper implements RowMapper<ProductView> {

		@Override
		public ProductView mapRow(ResultSet rs, int rowNum) throws SQLException {

			try {
				ProductView item = new ProductView();
				item.setPro_id(rs.getInt("pro_id"));
				item.setPro_name(rs.getString("pro_name"));
				item.setPro_price(rs.getFloat("pro_price"));
				item.setPro_image(rs.getString("pro_image"));
				item.setPro_enable(rs.getInt("pro_enable"));
				item.setCat_name(rs.getString("cat_name"));
				return item;
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public List<ProductView> findAll1() {
		return db.query("exec showAllProductName", new ProductViewRowMapper());
	}
>>>>>>> Stashed changes
}
