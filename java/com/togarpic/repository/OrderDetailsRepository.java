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
	public List<Orderdetails> findAll1() {
		try {
			return db.query("EXEC dbo.GetOrderDetails", new OrderRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error list order details!!");	
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
	
	
}
