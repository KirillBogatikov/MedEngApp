package ru.medeng.app.db;

import static ru.medeng.tools.Resources.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import ru.medeng.models.user.AccessLevel;
import ru.medeng.models.user.Auth;

public class AuthRepository extends SqlRepository {
	private static final String SQL_DIR = "auth";
	public static final Mapper<Auth> auth = r -> {
		var a = new Auth();
		
		a.setId(r.getObject("auth_id", UUID.class));
		a.setLogin(r.getString("auth_login"));
		
		return a;
	};
	
	public AuthRepository(String url) {
		super(url);
	}

	public Auth login(String login, String password) throws SQLException, IOException {
		return query(auth, sql(SQL_DIR, "login"), login, password);
	}

	public AccessLevel getAccessLevel(UUID authId) throws SQLException, IOException {
		if (query(r -> 1, sql(SQL_DIR, "check_customer"), authId) != null) {
			return AccessLevel.Customer;
		}
		
		return query(r -> AccessLevel.valueOf(r.getString("employee_role")), sql(SQL_DIR, "check_employee"), authId);
	}
	
	public UUID getCustomerId(UUID authId) throws SQLException, IOException {
		return query(r -> r.getObject("user_id", UUID.class), sql(SQL_DIR, "get_customer_id"), authId);
	}
	
	public UUID getEmployeeId(UUID authId) throws SQLException, IOException {
		return query(r -> r.getObject("user_id", UUID.class), sql(SQL_DIR, "get_employee_id"), authId);
	}
}
