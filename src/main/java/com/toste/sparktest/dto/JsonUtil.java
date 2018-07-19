package com.toste.sparktest.dto;

import com.owlike.genson.*;
import com.owlike.genson.stream.JsonStreamException;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;
import spark.ResponseTransformer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class JsonUtil {

	private static final Genson genson = new GensonBuilder()
			.useIndentation(true)
			.withConverter(new LocalDateConverter(), LocalDate.class)
			.withConverter(new EmploymentStatusConverter(), EmployeeDto.EmploymentStatus.class)
			.create();

	public static String toJson(Object object) {
		return genson.serialize(object);
	}

	public static <T> T fromJson(String jsonString, Class<T> type) {
		try {
			T e = genson.deserialize(jsonString, type);
			return e;
		} catch (JsonStreamException | JsonBindingException ex) {
			throw new IllegalArgumentException("Could not parse JSON: " + jsonString + ". Reason: " + ex.getMessage() + ". Details: " + ex.getCause());
		}
	}

	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}

	private static class LocalDateConverter implements Converter<LocalDate> {

		@Override
		public void serialize(LocalDate localDate, ObjectWriter writer, Context context) throws Exception {
			if (localDate == null) {
				return;
			}
			writer.writeString(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
		}

		@Override
		public LocalDate deserialize(ObjectReader reader, Context context) throws Exception {

			final String dateString = reader.valueAsString();
			if (dateString == null || dateString.isEmpty()) {
				return null;
			}
			try {
				return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
			} catch (DateTimeParseException ex) {
				throw new IllegalArgumentException(ex);
			}

		}
	}

	private static class EmploymentStatusConverter implements Converter<EmployeeDto.EmploymentStatus> {

		@Override
		public void serialize(EmployeeDto.EmploymentStatus status, ObjectWriter writer, Context context) {
			writer.writeString(status.toString());
		}

		@Override
		public EmployeeDto.EmploymentStatus deserialize(ObjectReader reader, Context context) throws Exception {
			final String statusString = reader.valueAsString();
			if (statusString == null || statusString.isEmpty()) {
				return EmployeeDto.EmploymentStatus.ACTIVE;
			}

			return EmployeeDto.EmploymentStatus.valueOf(statusString.toUpperCase());
		}

	}

}
