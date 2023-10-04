package com.controller.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.controller.Model.orderdetails;


@Repository
public class OrderDetailsRepository {
	@Autowired
	JdbcTemplate db;
	
	class OrderRowMapper implements RowMapper<orderdetails> {
		@Override
		public orderdetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			orderdetails item = new orderdetails();
			item.setOdt_id(rs.getLong("odt_id"));
			item.setOrd_id(rs.getLong("ord_id"));
			item.setSto_id(rs.getLong("sto_id"));
			item.setOdt_quantity(rs.getInt("odt_quantity"));
			item.setOdt_importPrice(rs.getFloat("odt_importPrice"));
			item.setOdt_exportPrice(rs.getFloat("odt_exportPrice"));
			
			return item;
		}
	}
	public List<orderdetails> findAll1() {
		try {
			return db.query("EXEC dbo.GetOrderDetails", new OrderRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error!!");	
		}
	}
	
	
}
