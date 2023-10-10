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
			try {
				Storage item = new Storage();
				item.setPro_id(rs.getInt("pro_id"));
				item.setSto_date(rs.getTimestamp("sto_date"));
				item.setSto_id(rs.getInt("sto_id"));
				item.setSto_price(rs.getFloat("sto_price"));
				item.setSto_quantity(rs.getInt("sto_quantity"));
				item.setStt(rs.getInt("stt"));
				return item;
			} catch (SQLException e) {
				throw e;
			}
		}
	}
		public List<Storage> findAll() {
			return db.query("exec showAllStorage", new StorageRowMapper());
		}

		public Storage findById(int id) {
			return db.queryForObject("exec showStorageById ?", new StorageRowMapper(), new Object[] { id });
		}

		public int insert(Storage storage) {
			return db.update("exec insertStorage ?,?,?",
					new Object[] { storage.getPro_id(), storage.getSto_price(), storage.getSto_quantity() });
		}

		public long deleteById(long id) {
			return db.update("exec deleteStorage ?", new Object[] { id });
		}

		public int update(Storage storage) {
			return db.update("exec updateStorage ?, ?, ?, ?", new Object[] { storage.getPro_id(),
					storage.getSto_price(), storage.getSto_quantity(), storage.getSto_id() });
		}

	}