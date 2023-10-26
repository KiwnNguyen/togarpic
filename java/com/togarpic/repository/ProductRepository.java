package com.togarpic.repository;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.togarpic.model.Product;
import com.togarpic.model.ProductView;

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
				return item;
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public Product findById(long newparlong) {
		return db.queryForObject("exec showProductById ?", new ProductRowMapper(), new Object[] { newparlong });
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
	
	 public String saveProd_img(MultipartFile prod_img) throws IOException {
	        String fileName = (prod_img.getOriginalFilename());
	        System.out.println(fileName);
	        // Save image to folder
	        File file = new File("src/main/resources/static/asset/admin/assets/img/product/"+fileName);
	        prod_img.transferTo(file.toPath());
	        return fileName; // return image name
	    }
}
