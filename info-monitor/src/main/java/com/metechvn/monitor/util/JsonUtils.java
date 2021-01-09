package com.metechvn.monitor.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;

public class JsonUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(FAIL_ON_NULL_FOR_PRIMITIVES, false);
		objectMapper.configure(FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(FAIL_ON_IGNORED_PROPERTIES, false);
		objectMapper.configure(FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
		objectMapper.configure(FAIL_ON_NULL_CREATOR_PROPERTIES, false);
	}

	private JsonUtils() {
	}

	public static String toJson(Object o) {
		try {
			if (o == null) {
				return null;
			}

			if (o instanceof String) {
				return (String) o;
			}

			return new ObjectMapper().writeValueAsString(o);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T convert(Object obj, Class<T> clazz) {
		try {
			return objectMapper.convertValue(obj, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T convert(Object obj, TypeReference<T> typeReference) {
		try {
			return objectMapper.convertValue(obj, typeReference);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T toObject(String json, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(json, typeReference);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T toObjectIgnoreUnknownProperties(String json, Class<T> clazz) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}
}
