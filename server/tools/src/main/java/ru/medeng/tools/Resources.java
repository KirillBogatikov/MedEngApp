package ru.medeng.tools;

import java.io.IOException;

public class Resources {
	private static ClassLoader loader;
	
	public static void init(Class<?> launcher) {
		loader = launcher.getClassLoader();
	}
	
	public static String sql(String group, String name) throws IOException {
		return new String(loader.getResourceAsStream("sql/%s/%s.sql".formatted(group, name)).readAllBytes());
	}
}
