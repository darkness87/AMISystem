package com.cnu.ami.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyConfig {
	public static Properties properties;

	public PropertyConfig() {
		properties = new Properties();
	}

	public static void setString(String key, String value) {
		properties.setProperty(key, value);
	}

	public static String getString(String key) {
		return getString(key, null);
	}

	public static String getString(String key, String defaultValue) {
		String val = properties.getProperty(key);

		if (val == null || "".equals(val)) {
			return defaultValue;
		}

		return val;
	}

	public static int getInt(String key) {
		return getInt(key, 0);
	}

	public static int getInt(String key, int defaultValue) {
		String val = getString(key);

		if (val == null) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			// ignore exception
		}

		return defaultValue;
	}

	public static boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		String val = getString(key);

		if (val == null) {
			return defaultValue;
		}

		if ("true".equalsIgnoreCase(val)) {
			return true;
		}

		return false;
	}

	public static <T> List<T> getList(String key) {
		return getList(key, ",");
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(String key, String regex) {
		String val = getString(key);
		List<T> valueList = new ArrayList<T>();

		if (val == null) {
			return valueList;
		} else if (val.split(regex).length == 0) {
			return valueList;
		}

		for (String value : val.split(regex)) {
			valueList.add((T) value);
		}

		return valueList;
	}

	public static void remove(String key) {
		properties.remove(key);
	}
}