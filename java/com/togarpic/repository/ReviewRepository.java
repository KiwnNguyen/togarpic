package com.togarpic.repository;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.core.io.ClassPathResource;
import com.togarpic.model.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Repository
public class ReviewRepository {
	@Autowired
	JdbcTemplate db;
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private JavaMailSender mailSender;

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
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");
		}
	}

	public List<Review> findAllTop() {
		try {
			return db.query("EXEC select_top_review", new ReviewRowMapper());
		} catch (Exception ec) {
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
			return db.queryForObject("dbo.GetReviewID @id = ?", new ReviewRowMapper(), new Object[] { id });
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");

		}

	}

	// Function Delete Table Order

	public int deleteById(long id) {
		try {
			return db.update("EXEC DeleteReview @revId = ?", new Object[] { id });
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error delete review!");

		}

	}
	// Function Insert Table Order

	public long insert(Review Review) {

		try {
			return db.update("EXEC InsertReview ?,?,?,?,? ", new Object[] { Review.getUsr_id(), Review.getOdt_id(),
					Review.getPro_id(), Review.getRev_content(), Review.getRev_id() });
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error inserting review!!");

		}

	}

	// Function Update Table Review
	public long update(Review review) {
		try {
			Connection connection = db.getDataSource().getConnection();
			connection.setAutoCommit(false);
			int result = db.update("EXEC update_review ?, ?, ?, ?, @rev_id = ?", review.getUsr_id(), review.getOdt_id(),
					review.getPro_id(), review.getRev_content(), review.getRev_id());
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			return result;
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error updating order!!");
		}
	}

	// Get information for product
	class ProductRowMapper implements RowMapper<Product> {
		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product item = new Product();
			item.setPro_id(rs.getInt("pro_id"));
			item.setPro_image(rs.getString("pro_image"));
			item.setPro_name(rs.getString("pro_name"));
			item.setPro_price(rs.getFloat("pro_price"));

			return item;
		}
	}

	public List<Product> findAllPro() {
		try {
			return db.query("select *from tblproduct", new ProductRowMapper());
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");

		}

	}

	// Get information for User
	class UserRowMapper implements RowMapper<Review> {
		@Override
		public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
			Review item = new Review();

			item.setUsr_id(rs.getLong("usr_id"));
			item.setUsr_lastName(rs.getString("usr_lastName"));
			item.setUsr_firstName(rs.getString("usr_firstName"));
			item.setUsr_email(rs.getString("usr_email"));
			item.setUsr_password(rs.getString("usr_password"));
			item.setUsr_role(rs.getString("usr_role"));

			return item;
		}
	}

	public List<Review> findAllUser() {
		try {
			return db.query("select *from tbluser", new UserRowMapper());
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");

		}

	}

	public Review findByIdUser(long id) {
		try {
			return db.queryForObject("select *from tbluser where usr_id = ?", new ReviewRowMapper(),
					new Object[] { id });
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");

		}

	}

	public int insertUser(Review review) {
		try {
			String temp = review.toString();
			String hashedPassword = encryptPassword(temp);

			String randomCode = UUID.randomUUID().toString().replace(hashedPassword, "");
			review.setVerificationCode(randomCode);

			return db.update(
					"insert into tbluser(usr_firstName,usr_lastName,usr_telephone,usr_email,usr_password,usr_role) values(?,?,?,?,?,?)",
					new Object[] { review.getUsr_firstName(), review.getUsr_lastName(), review.getUsr_telephone(),
							review.getUsr_email(), review.getUsr_password(), review.getUsr_role() });
		}

		catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error inserting order!!");

		}

	}

	public void sendVerificationEmail(Review review, String siteURL) throws MessagingException, MessagingException {
		// TODO Auto-generated method stub
		// Tạo đường dẫn tới tệp ảnh trong thư mục static/image
		String imagePath = "";

		// Tải tệp ảnh từ đường dẫn
		ClassPathResource resource = new ClassPathResource(imagePath, applicationContext.getClassLoader());

		// Lấy URL của tệp ảnh

		try {
			String imageUrl;
			imageUrl = resource.getURL().toString();
			String email = "dvo31666@gmail.com";
			String review1 = "This is my review.";

			String verifyURL = "mailto:" + email + "?subject=Review&body=" + URLEncoder.encode(review1, "UTF-8");

			String subject = "Welcome To Our Shop";
			String senderName = "Shop Recipe";

			// Tạo form nội dung email
			String mailContent = "<div style=\"font-family: Arial, sans-serif;\">"
					+ "<div style=\"background-color:  rgb(185, 255, 255);border-radius: 5px solid rgb(225, 255, 245); padding: 20px;\">"
					+ "<h3 style=\"font-size: 18px;\">Dear " + review.getUsr_firstName() + ",</h3>"
					+ "<p>We would like to express our sincere gratitude to our valued customers for visiting Shop Recipe. We are delighted to have the opportunity to welcome and serve you.</p>"
					+ "<p>We are committed to providing you with an excellent shopping experience, along with high-quality products and dedicated services. Your trust and support are great motivations for us to constantly improve and develop.</p>"
					+ "<p>If you have any questions, requests, or suggestions, please feel free to contact us. The Shop Recipe team is always ready to listen and assist you.</p>"
					+ "<p>Once again, we would like to sincerely thank you for choosing and trusting Shop Recipe. We wish you a joyful and successful day!</p>"
					+ "<p>Best regards,<br>The Shop Recipe Team</p>"
					+ "<img src=\"https://images-platform.99static.com/rGKrHrUIGP_E7ETgTcZ-TtIOjDo=/883x178:1624x919/500x500/top/smart/99designs-contests-attachments/80/80029/attachment_80029787\" alt=\"Shopme Logo\" style=\"max-width: 300px; margin-top: 20px;\">"
					+ "<p>Questions?\r\n" + "<br>Reply to this email or get in touch with us at <a href=" + verifyURL
					+ ">vo31666@gmail.com</a>.\r\n" + "<br>+84-902345552   |   M-F 7am-21pm PST</p>" + "</div>";

			String htmlContent = "<html><head><style>"
					+ "body { font-family: Arial, sans-serif; background-color:  rgb(185, 255, 255);border-radius: 5px solid rgb(225, 255, 245) }"
					+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #FFFFFF; border-radius: 5px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }"
					+ "h1 { font-size: 24px; color: #333333; }" + "p { margin-bottom: 10px; }"
					+ "a { color: #0000FF; text-decoration: none; }" + "</style></head><body>"
					+ "<div class=\"container\">" + mailContent + "</div>" + "</body></html>";

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("dvo31666@gmail.com", senderName);
			helper.setTo(review.getUsr_email());
			helper.setSubject(subject);
			helper.setText(mailContent, true);
			helper.setText(htmlContent, true);

			mailSender.send(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String encryptPassword(String review) {
		String salt = BCrypt.gensalt(12);
		return BCrypt.hashpw(review, salt);
	}

	// Get Storange
	class StorageRowMapper implements RowMapper<Storage> {
		@Override
		public Storage mapRow(ResultSet rs, int rowNum) throws SQLException {
			Storage item = new Storage();
			item.setSto_id(rs.getInt("sto_id"));
			item.setPro_id(rs.getInt("pro_id"));
			item.setSto_date(rs.getTimestamp("sto_date"));
			item.setSto_price(rs.getFloat("sto_price"));
			item.setSto_quantity(rs.getInt("sto_quantity"));

			return item;
		}
	}

	public List<Storage> findAllSto() {
		try {
			return db.query(
					"select *from tblstorage as a\r\n" + "inner join tblproduct as b\r\n" + "on a.pro_id = b.pro_id",
					new StorageRowMapper());
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");
		}
	}

	public Review StorageFind(long id) {
		try {
			return db.queryForObject(
					"select *from  tblstorage as b\\r\\n\"\r\n"
							+ "						+ \"inner join tblproduct as c\\r\\n\"\r\n"
							+ "						+ \"on b.pro_id = c.pro_id\\r\\n\"\r\n"
							+ "						+ \"where b.sto_id = ?",
					new ReviewRowMapper(), new Object[] { id });
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error!!");

		}

	}

}