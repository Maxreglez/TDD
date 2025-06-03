package de.oose.stattauto.geschaeftslogik;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MitgliedTest {

	@Test
	@DisplayName("Ein neues Mitglied wird mit den korrekten Attributen initialisiert")
	public void anlegen() {
		final String name = "Mitglied Name";
		int mitgliedsnr = 1;
		Mitglied mitglied = new Mitglied(name, mitgliedsnr);
		assertEquals(name, mitglied.getName());
		assertEquals(mitgliedsnr, mitglied.getMitgliedsnr());
	}
	
	@Test
	@DisplayName("NULL als Name wird nicht akzeptiert")
	public void nullNameNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, () -> new Mitglied(null, 4));
	}
	
	@Test
	@DisplayName("Leerstring als Name wird nicht akzeptiert")
	public void leererNameNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, () -> new Mitglied("", 34));
	}
	
	@Test
	@DisplayName("Negative Mitgliedsnummern werden nicht akzeptiert")
	public void negativeMitgliedsnummerNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, () -> new Mitglied("Peter", -234));
	}
	
	@Test
	@DisplayName("0 als Mitgliedsnummer wird nicht akzeptiert")
	public void mitgliedsnr0NichtErlaubt() {
		IllegalArgumentException expected = assertThrows(IllegalArgumentException.class, () -> new Mitglied("Peter", 0));
		assertEquals("mitgliedsnr > 0", expected.getMessage());
	}
}
