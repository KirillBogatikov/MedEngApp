package ru.medeng.app.db.migrations;

import ru.medeng.app.db.SqlRepository;

import static ru.medeng.tools.Resources.sql;

import java.io.IOException;
import java.sql.SQLException;

public class Migrator {
	private SqlRepository repository;
		
	public Migrator(String jdbc) throws SQLException, IOException {
		repository = new SqlRepository(jdbc);
		
		if (repository.query(r -> 1, sql("init", "check")) == null) {
			System.out.println("Database is raw. Starting migration...");
			repository.execute(sql("init", "create"));
			repository.execute(sql("init", "product"));
		}
	}

}
