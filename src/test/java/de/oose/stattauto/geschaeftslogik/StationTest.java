package de.oose.stattauto.geschaeftslogik;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StationTest {

	@Test
	@DisplayName("Eine Station wird korrekt mit dem uebergebenen Stationsnamen angelegt")
	public void anlegen() {
		final String name = "Name der Station";
		Station station = new Station(name);
		assertEquals(name, station.getName());
	}
	
	@Test
	@DisplayName("Stationsname NULL wird nicht akzeptiert")
	public void nullNameNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, 
			() -> new Station(null)
		);
	}
	
	@Test
	@DisplayName("Leerstring als Stationsname wird nicht akzeptiert")
	public void leererNameNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, 
				() -> new Station("")
			);
	}
}
