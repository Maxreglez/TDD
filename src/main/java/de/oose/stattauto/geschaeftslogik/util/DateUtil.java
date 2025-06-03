package de.oose.stattauto.geschaeftslogik.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.common.base.Preconditions;

public class DateUtil {

	private static final String LONG_TIME_FORMAT_STRING = "dd.MM.yyyy HH:mm";

	public static LocalDateTime toDate(String datum) {
		Preconditions.checkArgument(datum.matches("\\d{1,2}\\.\\d{1,2}\\.\\d+ +\\d{1,2}:\\d{1,2}"));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LONG_TIME_FORMAT_STRING);
		return LocalDateTime.parse(datum, formatter);
	}

}
