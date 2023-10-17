package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.UserDB;
import com.togarpic.repository.StorageRepository.StorageRowMapper;

@Repository
public class UserRepository {
	@Autowired
	JdbcTemplate db;
	class UserRowMapper implements RowMapper<UserDB> {
		@Override
		public UserDB mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserDB item = new UserDB();

			item.setUsr_id(rs.getLong("usr_id"));
			
			return item;
		}
	}

	public List<UserDB> findAll1() {
		try {
			return db.query("select *from tbluser", new UserRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error!!");	
		}
	}
}
