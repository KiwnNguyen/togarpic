package com.togarpic.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.Listinfo;


@Repository
public class ListInfoRepository {
	@Autowired
	JdbcTemplate db;
	
	class ListInfoRowMapper implements RowMapper<Listinfo> {
		@Override
		public Listinfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			Listinfo item = new Listinfo();
			item.setPro_name(rs.getString("pro_name"));
			item.setOdt_quantity(rs.getInt("odt_quantity"));
			item.setOdt_exportPrice(rs.getFloat("odt_exportPrice"));
			item.setOrd_totalAmount(rs.getFloat("ord_totalAmount"));
			item.setOrd_date(rs.getDate("ord_date"));
			item.setRev_content(rs.getString("rev_content"));
			
			return item;
		}
	}
	public List<Listinfo> findAll1() {
		try {
			return db.query("select * from tblorder a\r\n"
					+ "inner join tblorder_details b\r\n"
					+ "on a.ord_id = b.ord_id\r\n"
					+ "inner join tblreview c\r\n"
					+ "on b.odt_id = c.odt_id\r\n"
					+ "inner join tblstorage d\r\n"
					+ "on b.sto_id = d.sto_id\r\n"
					+ "inner join tblproduct e\r\n"
					+ "on d.pro_id = e.pro_id", new ListInfoRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error list order details!!");	
		}
	}
}
