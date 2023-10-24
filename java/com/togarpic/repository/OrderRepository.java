package com.togarpic.repository;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;


import com.togarpic.model.Order;



@Repository
public class OrderRepository {
	@Autowired
	JdbcTemplate db;
	
	class OrderRowMapper implements RowMapper<Order> {
		@Override
		public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order item = new Order();
			item.setOrd_id(rs.getLong("ord_id"));
			item.setUsr_id(rs.getLong("usr_id"));
			item.setOrd_totalAmount(rs.getFloat("ord_totalAmount"));
			item.setOrd_status(rs.getInt("ord_status"));
			item.setOrd_date(rs.getDate("ord_date"));
			
		
			
			return item;
		}
	}

	public List<Order> findAll1() {
		try {
			return db.query("EXEC dbo.GetOrder", new OrderRowMapper());
		}catch(Exception ec){
			ec.printStackTrace();
			throw new RuntimeException("Error!!");	
		}
	}
	
	public List<Order> findAllTop() {
		try {
			return db.query("EXEC select_top_order", new OrderRowMapper());
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
	public Order findById(long id) {
		try {
			return db.queryForObject("dbo.GetOrderID @id = ?", new OrderRowMapper(),
			new Object[] { id });
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");
			
			
		}
		
	}
	
	
	
	
	//Function Delete Table Order
	
	public int deleteById(long id) {
		try {
			return db.update("EXEC dbo.DeleteOrder  @orderId = ?", 
			new Object[] { id });
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error delete order!!");
			
			
		}
	   
  }
	//Function Insert Table Order
	
	  public long insert(Order Order) {
		
			try {
//				  return db.update("EXEC InsertOrder ?,?,? ", 
//				  new Object[] {Order.getUsr_id(),Order.getOrd_date(),Order.getOrd_id(), }); 
				
				db.update("EXEC InsertOrder ?,?,? ",Order.getUsr_id(),Order.getOrd_date(),Order.getOrd_id());
//				db.update("insert into tblreview(usr_id) values(?)",Order.getUsr_id1());
				
				return 1L;
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error inserting order!!");
				
			}
		
	  
	  }
	//Function Update Table Order
	  public long update(Order order) {
		    try {
		        Connection connection = db.getDataSource().getConnection();
		        connection.setAutoCommit(false);
		        int result = db.update("EXEC UpdateOrder ?, ?, ?, @orderid = ?",
		         order.getUsr_id(), order.getOrd_totalAmount(), order.getOrd_date(), order.getOrd_id());
		        connection.commit();
		        connection.setAutoCommit(true);
		        connection.close();
		        return result;
		    } catch (Exception ec) {
		        ec.printStackTrace();
		        throw new RuntimeException("Error updating order!!");
		    }
		}
	
	///Open and Close status

	public long openStatus(long id) {
		try {
			
			return db.update("UPDATE tblorder SET ord_status='1'  WHERE ord_id= ?",
			new Object[] { id });		

		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error open status!!");
		}

	}
	public long closeStatus(long id) {
		try {
			return db.update("UPDATE tblorder SET ord_status='0'  WHERE ord_id= ?",
			new Object[] { id });	
		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error close status!!");
		}
		
		
	}
	
	public List<Order> getOrdByFilter(long usr_id){
		try {
			String sql = "select *from tblorder where usr_id = ? ";
			
			
			Object[] params= {usr_id};
			
			RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);
			
			return db.query(sql, params, rowMapper);
			
			
		}catch(Exception ex) {
			
			
		}
		return null;
		
	}
	
}
