package ru.medeng.app.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<R> {
	public R apply(ResultSet result) throws SQLException;
	
	public default R defaultValue() {
		return null;
	}
}
