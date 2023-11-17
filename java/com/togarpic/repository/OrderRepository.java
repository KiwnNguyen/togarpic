package com.togarpic.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.togarpic.model.*;
import com.togarpic.repository.OrderDetailsRepository.StorageProductRowMapper;
import com.togarpic.repository.ReviewRepository.ReviewRowMapper;
import com.togarpic.repository.UserRepository.UserRowMapper;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.togarpic.model.Order;
import com.togarpic.model.Order1;

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
			item.setOrd_date_formatted(rs.getString("ord_date_formatted"));
			item.setUsr_telephone(rs.getString("usr_telephone"));
			item.setUsr_firstName(rs.getString("usr_firstName"));
			item.setUsr_lastName(rs.getString("usr_lastName"));
			item.setOrd_address(rs.getString("ord_address"));
			
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
			throw new RuntimeException("Error ordertop!!");	
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
				db.update("EXEC InsertOrder ?,?,?,? ",Order.getUsr_id(),Order.getOrd_date(),Order.getOrd_address(),Order.getOrd_id());				
				return 1L;
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error inserting order!!");
			}
	  }

	  
	  public long insert1(Order Order) {
			
			try {				
				db.update("insert into tblorder(usr_id,ord_address) values(?,?) ",Order.getUsr_id(),Order.getOrd_address());				
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
	
	@SuppressWarnings("deprecation")
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
	public long updateStatus(int status,long id) {
		try {
			return db.update("UPDATE tblorder SET ord_status= ?  WHERE ord_id= ?",
			new Object[] { status+1 ,id });	

		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error close status!!");
		}
		
		
	}
	
	class OrderUserRowMapper implements RowMapper<Order1> {
		@Override
		public Order1 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order1 item = new Order1();

			item.setOrd_id(rs.getLong("ord_id"));
			item.setUsr_id(rs.getLong("usr_id"));
			item.setOrd_totalAmount(rs.getFloat("ord_totalAmount"));
			item.setOrd_status(rs.getInt("ord_status"));
			item.setOrd_date(rs.getDate("ord_date"));
			item.setOrd_address(rs.getString("ord_address"));
			
			return item;
		}
	}
	public List<Order1> findByIdUser(long id) {
	    try {
	        return db.query("Exec GetviewUser_ord @id=?", new OrderUserRowMapper(),
	                new Object[] { id });
	    } catch (Exception ec) {
	        ec.printStackTrace();
	        throw new RuntimeException("Error view!!");
	    }
	}
	
	public long CancelStatus(int status,long id) {
		try {
			return db.update("UPDATE tblorder SET ord_status= ?  WHERE ord_id= ?",
			new Object[] { status ,id });	

		}catch(Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error close status!!");
		}
		
		
	}
	  public long insertCart(CartAddTo1 CartAddTo) {
			
			try {				
				db.update("insert into tblcart(usr_id) values(?) ",CartAddTo.getUsr_id());				
				return 1L;
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error inserting order!!");
			}
	  }
	  class cartRowMapper implements RowMapper<CartVieww> {
			@Override
			public CartVieww mapRow(ResultSet rs, int rowNum) throws SQLException {
				CartVieww item = new CartVieww();
				item.setCart_id(rs.getInt("cart_id"));
				item.setUsr_id(rs.getInt("usr_id"));
				return item;
			}
		}
	  public CartVieww findByIdCart(long id) {
			try {
				return db.queryForObject("select *from tblcart where usr_id = ?", new cartRowMapper(),
				new Object[] { id });
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error!!");
				
				
			}
			
		}
	  
	  class ProRowMapper implements RowMapper<CartAddTo1> {
			@Override
			public CartAddTo1 mapRow(ResultSet rs, int rowNum) throws SQLException {
				CartAddTo1 item = new CartAddTo1();
				item.setPro_name(rs.getString("pro_name"));
				item.setPro_price(rs.getFloat("pro_price"));
				item.setPro_image(rs.getString("pro_image"));
				item.setPro_id(rs.getInt("pro_id"));
				return item;
			}
		}
	  public List<CartAddTo1> findAllPro1() {

			try {
				return db.query("select top 8 *from tblproduct ", new ProRowMapper());
			}catch(Exception ec){
				ec.printStackTrace();
				throw new RuntimeException("Error!!");	
			}
		}
	  
	  
	  
	  public List<CartVieww> getCartProduct(ArrayList<CartVieww> cartList) {
		  List<CartVieww> products = new ArrayList<CartVieww>();
		  try {
	            Connection connection = DriverManager.getConnection("jdbc:sqlserver://aptech_group1.mssql.somee.com;initial catalog=aptech_group1", "tannguyen18_SQLLogin_1", "2j192j2u1t");
	            CartVieww cartview = new CartVieww();
	          
	            
	            for (CartVieww item : cartList) {
	                // Thực hiện truy vấn
	                String sql = "SELECT * FROM tblproduct WHERE pro_id = ?";
	                PreparedStatement statement = connection.prepareStatement(sql);
	                statement.setInt(1, item.getPro_id());
	                ResultSet resultSet = statement.executeQuery();

	                // Xử lý kết quả truy vấn
	                if (resultSet.next()) {
	                    CartVieww cartPro = new CartVieww();
	                    cartPro.setPro_id(resultSet.getInt("pro_id"));
	                    cartPro.setPro_name(resultSet.getString("pro_name"));
	                    cartPro.setPro_price(resultSet.getFloat("pro_price"));
	                    cartPro.setPro_image(resultSet.getString("pro_image"));
	                    products.add(cartPro);
	                }

	                // Đóng các tài nguyên
	                resultSet.close();
	                statement.close();
	            }

	            // Đóng kết nối cơ sở dữ liệu
	            connection.close();
		  }catch(Exception ex) {
			  ex.printStackTrace();
			throw new RuntimeException("Error!!");	
		  }
		  return products;
	  }
	  
	  
	  public long insertOrderShop(OrderCheck OrderCheck1) {
			
			try {				
				db.update("EXEC OrderShop_Customer ?,?,?,?,?,?,?,?,?,? ",OrderCheck1.getPro_name(),OrderCheck1.getUsr_firstName(),OrderCheck1.getUsr_lastName(),OrderCheck1.getOrd_Address(),OrderCheck1.getUsr_email(),OrderCheck1.getUsr_phone(),OrderCheck1.getOdt_quantity(),OrderCheck1.getOrd_totalAmount(),OrderCheck1.getPrice1(),OrderCheck1.getDate());				
				return 1L;
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error inserting order!!");
			}
	  }
	  class UserTempMapper implements RowMapper<User> {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User item = new User();
				item.setUsr_email(rs.getString("usr_email"));
				item.setUsr_id(rs.getLong("usr_id"));
				return item;
			}
		}
	  public User findByEmail(String name) {
			return db.queryForObject("select  * from tbluser where usr_email = ? \r\n"
					, new UserTempMapper()
					, new Object[] { name});
		}
	  
	  public long insertODT(Orderdetails orderdetails) {	
			try {				
				db.update("EXEC InsertODT ?,? ",
						orderdetails.getPro_id(),orderdetails.getOdt_quantity());				
				return 1L;
			}catch(Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error inserting order!!");
			}
	  }

}
