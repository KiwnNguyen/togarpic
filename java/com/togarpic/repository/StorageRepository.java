package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.Storage;


@Repository
public class StorageRepository {
	@Autowired
	JdbcTemplate db;
	class StorageRowMapper implements RowMapper<Storage> {
		@Override
		public Storage mapRow(ResultSet rs, int rowNum) throws SQLException {
			Storage item = new Storage();
			item.setSto_id(rs.getLong("sto_id"));
			item.setPro_id(rs.getLong("pro_id"));
			item.setSto_date(rs.getDate("sto_date"));
			item.setSto_price(rs.getFloat("sto_price"));
			item.setSto_quantity(rs.getInt("sto_quantity"));
			item.setSto_enable(rs.getBoolean("sto_enable"));
			
			
			return item;
		}
	}

	public List<Storage> findAll1() {
		try {
			return db.query("select *from tblstorage", new StorageRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error!!");	
		}
	}
	
}
