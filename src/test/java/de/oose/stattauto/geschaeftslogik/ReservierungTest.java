package de.oose.stattauto.geschaeftslogik;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.oose.stattauto.geschaeftslogik.util.DateUtil;

import static org.junit.jupiter.api.Assertions.*;

public class ReservierungTest {

	private static final LocalDateTime reservierungsbeginn = DateUtil.toDate("23.02.2022 12:10");
	private static final LocalDateTime reservierungsende = DateUtil.toDate("28.02.2022 15:00");
	private Kfz kfz = new Kfz("Golf");
	private Mitglied mitglied = new Mitglied("Peter Meier", 1);

	@Test
	public void reservierungNullMinutenNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, 
				() -> Reservierung.create(reservierungsbeginn, reservierungsbeginn, kfz, mitglied)
		);
	}
	
	@Test
	public void reservierungVonNachBisNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, 
				() -> Reservierung.create(reservierungsende, reservierungsbeginn, kfz, mitglied)
		);
	}
	
	@Test
	public void reservierungVonNullNichtErlaubt() {
		assertThrows(NullPointerException.class, 
				() -> Reservierung.create(null, reservierungsende, kfz, mitglied)
		);
	}
	
	@Test
	public void reservierungBisNullNichtErlaubt() {
		assertThrows(NullPointerException.class, 
				() -> Reservierung.create(reservierungsbeginn, null, kfz, mitglied)
		);
	}
	
	@Test
	public void reservierungKfzNullNichtErlaubt() {
		assertThrows(NullPointerException.class, 
				() -> Reservierung.create(reservierungsbeginn, reservierungsende, null, mitglied)
		);
	}
	
	@Test
	public void reservierungMitgliedNullNichtErlaubt() {
		assertThrows(NullPointerException.class, 
				() -> Reservierung.create(reservierungsbeginn, reservierungsende, kfz, null)
		);
	}

	@Test
	@DisplayName("Erzeugte Reservierung ist am Mitglied verfügbar")
	public void mitgliedHatDieReservierung() {
		Reservierung reservierung = Reservierung.create(reservierungsbeginn, reservierungsende, kfz, mitglied);
		assertTrue(mitglied.getReservierungen().contains(reservierung));
	}

	@Test
	public void shouldSetStatus(){
		Reservierung reservierung = Reservierung.create(reservierungsbeginn, reservierungsende, kfz, mitglied);
		assertEquals(reservierung.getStatus(), Status.AUSSTEHEND);
	}

	@Test
	public void shouldgetKFZ(){
		Reservierung reservierung = Reservierung.create(reservierungsbeginn, reservierungsende, kfz, mitglied);
		assertEquals(reservierung.getKFZ(), kfz);
	}


	@Test
	public void shouldAddAFahrbericht(){
		Reservierung reservierung = Reservierung.create(reservierungsbeginn, reservierungsende, kfz, mitglied);
		LocalDateTime startDatum = DateUtil.toDate("23.02.2022 12:10");
		LocalDateTime endDatum = DateUtil.toDate("28.02.2022 12:10");;
		int Akm = 100;
		int Ekm = 600;
		Fahrbericht fahrbericht = new Fahrbericht(Akm, Ekm, startDatum, endDatum);
		reservierung.addFahrbericht(fahrbericht);
		assertEquals(reservierung.getFahrbericht(),fahrbericht);
	}
}
