package ru.medeng.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.postgresql.Driver;

public class SqlRepository {
	private String url;

	public SqlRepository(String url) {
		this.url = url;
		try {
			Driver.register();
		} catch (Exception e) {}
		
	}
	
	private void prepare(PreparedStatement stat, Object... args) throws SQLException {
		for (int i = 0; i < args.length; i++) {
			stat.setObject(i + 1, args[i]);
		}
	}

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url);
	}
	
	public <T> T getResultValue(ResultSet r, String column, Class<T> type) throws SQLException {
		var md = r.getMetaData();
		
		for (int i = 0; i < md.getColumnCount(); i++) {
			if (md.getColumnName(i).equals(column)) {
				return r.getObject(i, type);
			}
		}
		
		return null;
	}
	
	public <T> T query(Mapper<T> map, String sql, Object... args) throws SQLException {
		try(var conn = connect();
			var stat = conn.prepareStatement(sql)) {
			prepare(stat, args);
			
			var result = stat.executeQuery();
			if (result.next()) {
				return map.apply(result);
			} else {
				return map.defaultValue();
			}
		}
	}
	
	public <T> Mapper<List<T>> listMapper(Mapper<T> map) {
		return new Mapper<List<T>>() {
			public List<T> apply(ResultSet result) throws SQLException {
				var list = new ArrayList<T>();
				
				do { 
					list.add(map.apply(result));
				} while(result.next());
				
				return list;
			}
			
			public List<T> defaultValue() {
				return Collections.emptyList();
			}
		};
	}
	
	public <T> List<T> queryList(Mapper<T> map, String sql, Object... args) throws SQLException {
		return query(listMapper(map), sql, args);
	}
	
	public void execute(String sql, Object... args) throws SQLException {
		try(var conn = connect();
			var stat = conn.prepareStatement(sql)) {
			prepare(stat, args);
			
			stat.execute();
		}
	}
}
