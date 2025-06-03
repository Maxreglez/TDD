package de.oose.stattauto.geschaeftslogik.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

	@Test
	void ungueltigerStringWirftIllegalargumentException() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> DateUtil.toDate("01.01.2019"));
	}

	@Test
	void validerStringGibtKorrektesDatum() throws Exception {
		LocalDateTime datum = LocalDateTime.of(2019, Month.FEBRUARY, 12, 10, 00);
		assertEquals(datum, DateUtil.toDate("12.02.2019 10:00"));
	}

}
