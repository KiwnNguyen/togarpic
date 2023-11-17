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
import com.togarpic.repository.ReviewRepository.ReviewRowMapper;
import com.togarpic.repository.ReviewRepository.StorageRowMapper;




@Repository
public class OrderDetailsRepository {
	@Autowired
	JdbcTemplate db;
	
	class OrderRowMapper implements RowMapper<Orderdetails> {
		@Override
		public Orderdetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			Orderdetails item = new Orderdetails();

			item.setOdt_id(rs.getLong("odt_id"));
			item.setOrd_id(rs.getLong("ord_id"));
			item.setSto_id(rs.getLong("sto_id"));
			item.setOdt_quantity(rs.getInt("odt_quantity"));
			item.setOdt_importPrice(rs.getFloat("odt_importPrice"));
			item.setOdt_exportPrice(rs.getFloat("odt_exportPrice"));
			return item;
		}
	}
	class OrderviewRowMapper implements RowMapper<Orderdetails> {
		@Override
		public Orderdetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			Orderdetails item = new Orderdetails();
			item.setOdt_quantity(rs.getInt("odt_quantity"));
			item.setOdt_importPrice(rs.getFloat("odt_importPrice"));
			item.setOdt_exportPrice(rs.getFloat("odt_exportPrice"));
			item.setPro_name(rs.getString("pro_name"));

			return item;
		}
	}

	public List<Orderdetails> findAll1() {

		try {
			return db.query("EXEC dbo.GetOrderDetails", new OrderRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();

			throw new RuntimeException("Error!!");	
		}
	}

	
	public List<Orderdetails> findAllTop() {
		try {
			return db.query("EXEC select_top_orderdetails", new OrderRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error list order details!!");	
		}
	}
	public Orderdetails findById(long id) {
		try {
			return db.queryForObject("GetOrder_detailsID @id = ?", new OrderRowMapper(),
			new Object[] { id });
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");
			
			
		}
		
	}
	public int insert(Orderdetails Orderdetails) {
			try {
				  //return db.update("EXEC InsertOrderDetails ?,?,?,?,?,?", 
					return db.update("insert into tblorder_details(ord_id,sto_id,odt_quantity,odt_importPrice,odt_exportPrice)"+"values(?,?,?,?,?)",
				  new Object[] { Orderdetails.getOrd_id(),Orderdetails.getSto_id(),Orderdetails.getOdt_quantity(),Orderdetails.getOdt_importPrice(),Orderdetails.getOdt_exportPrice()}); 
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error insert orderdetails!!");
				
			}
		
	  
	  }
	public int insert1(Orderdetails Orderdetails) {
		try {
			  //return db.update("EXEC InsertOrderDetails ?,?,?,?,?,?", 
				return db.update("INSERT INTO tblorder_details (ord_id, sto_id, odt_quantity, odt_importPrice, odt_exportPrice)\r\n"
						+ "VALUES ((SELECT MAX(ord_id) FROM tblorder), ?, ?, ?, ?);",
			  new Object[] { Orderdetails.getSto_id(),Orderdetails.getOdt_quantity(),Orderdetails.getOdt_importPrice(),Orderdetails.getOdt_exportPrice()}); 
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error insert orderdetails!!");
			
		}
	
  
  }
	public int deleteById(long id) {
		try {
			return db.update("EXEC deleteOrder_details @order_dtId = ?", 
			new Object[] { id });
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error delete order details!!");
			
			
		}
	   
  }
	  public long update(Orderdetails orderdt) {
		    try {
		        Connection connection = db.getDataSource().getConnection();
		        connection.setAutoCommit(false);
		        int result = db.update("update tblorder_details\r\n"
		        		+ "set ord_id = ?,sto_id=?,odt_quantity=?,odt_importPrice = ?,odt_exportPrice = ? \r\n"
		        		+ "where odt_id = ?\r\n",      		
		         orderdt.getOrd_id(),orderdt.getSto_id(),orderdt.getOdt_quantity(),orderdt.getOdt_importPrice(),orderdt.getOdt_exportPrice(),orderdt.getOdt_id());
		        connection.commit();
		        connection.setAutoCommit(true);
		        connection.close();
		        return result;
		    } catch (Exception ec) {
		        ec.printStackTrace();
		        throw new RuntimeException("Error updating order details!!");
		    }
		}
	  public List<Orderdetails> findview(long id) {
		    try {
		        return db.query("EXEC GetviewOrder_dt @id=?", new OrderviewRowMapper(),
		                new Object[] { id });
		    } catch (Exception ec) {
		        ec.printStackTrace();
		        throw new RuntimeException("Error view!!");
		    }
		}
	  class Storage1RowMapper implements RowMapper<Storage1> {
			@Override
			public Storage1 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Storage1 item = new Storage1();
				item.setSto_id(rs.getInt("sto_id"));
				item.setPro_id(rs.getInt("pro_id"));
				item.setSto_date(rs.getTimestamp("sto_date"));
				item.setSto_price(rs.getFloat("sto_price"));
				item.setSto_quantity(rs.getInt("sto_quantity"));
				item.setPro_price(rs.getFloat("pro_price"));
				item.setPro_name(rs.getString("pro_name"));
				
				return item;
			}
		}
	  public Storage1  StorageFind(long id) {
			try {
				return db.queryForObject("select *from tblstorage as b\r\n"
						+ "inner join tblproduct as c\r\n"
						+ "on b.pro_id = c.pro_id\r\n"
						+ "where b.sto_id = ?\r\n", new Storage1RowMapper(),
				new Object[] { id });
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error!!");
				
				
			}
			
		}
	  public List<Storage1> findAllSto() {
			try {
				return db.query("select *from tblstorage as a\r\n"
						+ "inner join tblproduct as b\r\n"
						+ "on a.pro_id = b.pro_id", new Storage1RowMapper());
			}catch(Exception ec){
				ec.printStackTrace();
				throw new RuntimeException("Error!!");	
			}
		}
	  //
	  
	  
	  class StorageProductRowMapper implements RowMapper<StorageProductId> {
			@Override
			public StorageProductId mapRow(ResultSet rs, int rowNum) throws SQLException {
				StorageProductId item = new StorageProductId();
				item.setSto_id(rs.getInt("sto_id"));
				item.setPro_id(rs.getInt("pro_id"));
				item.setSto_price(rs.getFloat("sto_price"));
				item.setSto_quantity(rs.getInt("sto_quantity"));
				item.setPro_price(rs.getFloat("pro_price"));
				item.setPro_name(rs.getString("pro_name"));
				
				return item;
			}
		}
	  public List<StorageProductId> findStoProID(String tmp_proname1) {
		    try {
		        return db.query("select top 1 * from  tblstorage as b\r\n"
		        		+ "inner join tblproduct as c\r\n"
		        		+ "on b.pro_id = c.pro_id\r\n"
		        		+ "where c.pro_name = ?;", new StorageProductRowMapper(),
		                new Object[] { tmp_proname1 });
		    } catch (Exception ec) {
		        ec.printStackTrace();
		        throw new RuntimeException("Error view!!");
		    }
		}
	
}
