package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.User;

@Repository
public class UserRepository {
	@Autowired
	JdbcTemplate db;

	/***
	 * 
	 * @return Build objects in table product connection in SQL Server 
	 */
	class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User item = new User();
			item.setUsr_id(rs.getLong("usr_id"));
			item.setUsr_firstName(rs.getString("usr_firstName"));
			item.setUsr_lastName(rs.getString("usr_lastName"));
			item.setUsr_telephone(rs.getString("usr_telephone"));
			item.setUsr_email(rs.getString("usr_email"));
			item.setUsr_image(rs.getString("usr_image"));
			item.setUsr_password(rs.getString("usr_password"));
			item.setUser_Enable(rs.getBoolean("User_Enable"));
			return item;
		}
	}
	/***
	 * 
	 * @return select table category
	 */
	public List<User> findAll() {
		try {
			return db.query("SelectAllUsers", new UserRowMapper());
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
//	public User findById(long id) {
//		try {
//			return db.queryForObject("select * from tbluser where usr_id=?", new CategoryRowMapper(),
//			new Object[] { id });
//		}catch(Exception ec) {
//			ec.printStackTrace();
//			throw new RuntimeException("Error!!");
//			
//			
//		}
//		
//	}
	  public int insert(User user) {
			
		  return db.update("insert into tbluser (usr_id, usr_firstName, usr_lastName, usr_telephone, usr_email, usr_image, usr_password, usr_role, User_Enable ) " +"values( ?, ?,?,?,?,?,?,?,?)", 
		  new Object[] { user.getUsr_id(),user.getUsr_firstName(), user.getUsr_telephone() , user.getUsr_email(),user.getUsr_image(), user.getUsr_password(), user.getUsr_role(), user.getUser_Enable(),}); 
	  
	  }
	 public int deleteById(long id) {
			try {
				return db.update("exec DeleteUsers @usr_id ?", 
				new Object[] { id });
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error delete!!");
				
				
			}
		   
	  }
	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}
