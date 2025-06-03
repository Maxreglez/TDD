package de.oose.stattauto.geschaeftslogik;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.oose.stattauto.geschaeftslogik.util.DateUtil;

public class KfzTest {
	
	@Test
	@DisplayName("Ein KFZ wird mit dem übergebenen Namen angelegt")
	public void anlegen() {
		final String name = "KfzName";
		Kfz kfz = new Kfz(name);
		assertEquals(name, kfz.getName());
	}

	@Test
	@DisplayName("NULL als Name wird nicht akzeptiert")
	public void nullNameNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, () -> new Kfz(null));
	}

	@Test
	@DisplayName("Leerstring als Name wird nicht akzeptiert")
	public void leererNameNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, () -> new Kfz(""));
	}

	@Test
	@DisplayName("NULL als Station wird nicht akzeptiert")
	public void nullStationEintragenNichtErlaubt() {
		Kfz kfz = new Kfz("Golf");
		assertThrows(NullPointerException.class, () -> kfz.setStation(null));
	}
	
	@Test
	@DisplayName("NULL als Reservierung hat keine Auswirkungen")
	public void nullReservierungEinfuegenHatKeinenEffekt() {
		Kfz kfz = new Kfz("Golf");
		kfz.addReservierung(null);
		assertTrue(kfz.istFrei(DateUtil.toDate("23.03.2022 10:00"), DateUtil.toDate("25.03.2022 12:00")));
	}
	
	@Test
	@DisplayName("Ein KFZ ohne Station ist nicht einer konkreten Station zugeordnet")
	public void kfzOhneStationIstNichtAnStation() {
		Kfz kfz = new Kfz("Name");
		assertFalse(kfz.istAn(new Station("Station")));
	}
	
	@Test
	@DisplayName("Ein Stationsobjekt ist eindeutig für ein KFZ")
	public void kfzMitStationIstNurAnDieserStation() {
		Station station = new Station("Station");
		Kfz kfz = new Kfz("Name", station);
		assertTrue(kfz.istAn(station));
		assertFalse(kfz.istAn(new Station("Station")));
	}
	
	@Test
	public void kfzIstNichtAnStationNull() {
		Kfz kfz = new Kfz("Name", new Station("Station"));
		assertFalse(kfz.istAn(null));
	}
	
	@Test
	public void kfzOhneStationIstNichtAnStationNull() {
		Kfz kfz = new Kfz("Name");
		assertFalse(kfz.istAn(null));
	}
	
	@Test
	public void kfzOhneKategorieHatKeineKategorie() {
		Kfz kfz = new Kfz("Name", new Station("Station"));
		assertFalse(kfz.hatKategorie(Kategorie.KLEINWAGEN));
		assertFalse(kfz.hatKategorie(Kategorie.EXTRA));
		assertFalse(kfz.hatKategorie(Kategorie.KOMPAKT));
		assertFalse(kfz.hatKategorie(Kategorie.KOMFORT));
	}

	@Test
	public void kfzHatNurEineKategorie() {
		Kfz kfz = new Kfz("Name", new Station("XYZ"), Kategorie.KLEINWAGEN);
		assertTrue(kfz.hatKategorie(Kategorie.KLEINWAGEN));
		assertFalse(kfz.hatKategorie(Kategorie.EXTRA));
		assertFalse(kfz.hatKategorie(Kategorie.KOMPAKT));
		assertFalse(kfz.hatKategorie(Kategorie.KOMFORT));
	}
	
	@Test
	@DisplayName("Kategorien sind beliebig austauschbar")
	public void kategorieNachtraeglichEintragen() {
		Kfz kfz = new Kfz("Name");
		assertFalse(kfz.hatKategorie(Kategorie.EXTRA));
		kfz.setKategorie(Kategorie.EXTRA);
		assertTrue(kfz.hatKategorie(Kategorie.EXTRA));
		kfz.setKategorie(Kategorie.KOMPAKT);
		assertTrue(kfz.hatKategorie(Kategorie.KOMPAKT));
	}

	@Test
	public void shouldGetKategorieOfCar() {
		// Arrange
		Kfz kfz1 = new Kfz("Golf", new Station("Hauptbahnhof"), Kategorie.KOMPAKT);
		Kfz kfz2 = new Kfz("Audi A4", new Station("Hauptbahnhof"), Kategorie.KOMFORT);

		// Act
		Kategorie kategorie1 = kfz1.getKategorie();
		Kategorie kategorie2 = kfz2.getKategorie();

		// Assert
		assertEquals(Kategorie.KOMPAKT, kategorie1);
		assertEquals(Kategorie.KOMFORT, kategorie2);
	}
}
