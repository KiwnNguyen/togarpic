package com.togarpic.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.*;


@Repository
public class ReviewRepository {
	@Autowired
	JdbcTemplate db;
	class ReviewRowMapper implements RowMapper<Review> {
		@Override
		public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
			Review item = new Review();
			item.setRev_id(rs.getLong("rev_id"));
			item.setUsr_id(rs.getLong("usr_id"));
			item.setOdt_id(rs.getLong("odt_id"));
			item.setPro_id(rs.getLong("pro_id"));
			item.setRev_content(rs.getString("rev_content"));
			
			
			return item;
		}
	}

	public List<Review> findAll1() {
		try {
			return db.query("EXEC dbo.GetReview", new ReviewRowMapper());
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
	public Review findById(long id) {
		try {
			return db.queryForObject("dbo.GetReviewID @id = ?", new ReviewRowMapper(),
			new Object[] { id });
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");
			
			
		}
		
	}
	
	
	
	
	//Function Delete Table Order
	
	public int deleteById(long id) {
		try {
			return db.update("EXEC DeleteReview @revId = ?", 
			new Object[] { id });
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error delete review!");
			
			
		}
	   
  }
	//Function Insert Table Order
	
	  public long insert(Review Review) {
		
			try {
				  return db.update("EXEC InsertReview ?,?,?,?,? ", 
				  new Object[] {Review.getUsr_id(),Review.getOdt_id(),Review.getPro_id(),Review.getRev_content(),Review.getRev_id() }); 
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error inserting review!!");
				
			}
		
	  
	  }
	//Function Update Table Order
	  public long update(Review review) {
		    try {
		        Connection connection = db.getDataSource().getConnection();
		        connection.setAutoCommit(false);
		        int result = db.update("EXEC update_review ?, ?, ?, ?, @rev_id = ?",
		         review.getUsr_id(),review.getOdt_id(),review.getPro_id(),review.getRev_content(),review.getRev_id());
		        connection.commit();
		        connection.setAutoCommit(true);
		        connection.close();
		        return result;
		    } catch (Exception ec) {
		        ec.printStackTrace();
		        throw new RuntimeException("Error updating order!!");
		    }
	  }
}