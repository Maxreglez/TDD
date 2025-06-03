package de.oose.stattauto.geschaeftslogik;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.oose.stattauto.geschaeftslogik.util.DateUtil;

public class ReservierungsueberlappungTest {

	private Kfz kfz;
	private Mitglied mitglied;
	private Reservierung reservierung;
	private static final LocalDateTime langeVorher = DateUtil.toDate("22.02.2021 15:00");
	private static final LocalDateTime vorher = DateUtil.toDate("06.02.2022 13:00");
	private static final LocalDateTime kurzVorher = DateUtil.toDate("22.02.2022 12:09");
	private static final LocalDateTime reservierungsbeginn = DateUtil.toDate("23.02.2022 12:10");
	private static final LocalDateTime kurzHinterBeginn = DateUtil.toDate("24.02.2022 12:11");
	private static final LocalDateTime mittendrin = DateUtil.toDate("25.02.2022 15:00");
	private static final LocalDateTime kurzVorEnde = DateUtil.toDate("27.02.2022 14:59");
	private static final LocalDateTime reservierungsende = DateUtil.toDate("28.02.2022 15:00");
	private static final LocalDateTime kurzHinterher = DateUtil.toDate("01.03.2022 15:01");
	private static final LocalDateTime hinterher = DateUtil.toDate("15.03.2022 12:00");
	private static final LocalDateTime langeHinterher = DateUtil.toDate("23.02.2023 16:00");

	@BeforeEach
	public void reservierungErzeugen() {
		kfz = new Kfz("Golf");
		mitglied = new Mitglied("Peter Meier", 1);
		reservierung = Reservierung.create(reservierungsbeginn, reservierungsende, kfz, mitglied);
	}

	@Test
	@DisplayName("Identische Zeiträume überlappen")
	public void identischerZeitraumUeberlappt() {
		assertTrue(reservierung.ueberlappt(reservierungsbeginn, reservierungsende));
	}
	
	@Test
	@DisplayName("Ein Zeitraum innerhalb des Reservierungszeitraums überlappt")
	public void komplettInnerhalbUeberlappt() {
		assertTrue(reservierung.ueberlappt(kurzHinterBeginn, kurzVorEnde));
	}
	
	@Test
	@DisplayName("Eine Reservierungsanfrage mit dem Beginn einer vorhandenen Reservierung als Ende überlappt nicht")
	public void vorneAngrenzendIdentischerZeitpunktUeberlapptNicht() {
		assertFalse(reservierung.ueberlappt(vorher, reservierungsbeginn));
	}
	
	@Test
	@DisplayName("Eine Reservierungsanfrage mit dem Ende einer vorhandenen Reservierung als Beginn überlappt nicht")
	public void hintenAngrenzendIdentischerZeitpunktUeberlapptNicht() {
		assertFalse(reservierung.ueberlappt(reservierungsende, hinterher));
	}
	
	@Test
	@DisplayName("Eine komplett überspannende Reservierungsanfrage überlappt eine vorhandene")
	public void komplettUeberspannendUeberlappt() {
		assertTrue(reservierung.ueberlappt(vorher, hinterher));
	}
	
	@Test
	public void vorneHereinragendUeberlappt() {
		assertTrue(reservierung.ueberlappt(vorher, mittendrin));
	}
	
	@Test
	public void hintenHereinragendUeberlappt() {
		assertTrue(reservierung.ueberlappt(mittendrin, hinterher));
	}
	
	@Test
	public void komplettVorherUeberlapptNicht() {
		assertFalse(reservierung.ueberlappt(langeVorher, vorher));
	}
	
	@Test
	public void komplettVorherAngrenzendUeberlapptNicht() {
		assertFalse(reservierung.ueberlappt(vorher, kurzVorher));
	}
	
	@Test
	public void komplettHinterherUeberlapptNicht() {
		assertFalse(reservierung.ueberlappt(hinterher, langeHinterher));
	}
	
	@Test
	public void komplettHinterherAngrenzendUeberlapptNicht() {
		assertFalse(reservierung.ueberlappt(kurzHinterher, hinterher));
	}
	
	@Test
	public void kfzReserviertGemaessUeberlappung() {
		assertTrue(kfz.istFrei(vorher, kurzVorher));
		assertFalse(kfz.istFrei(kurzHinterBeginn, kurzVorEnde));
	}
	
	@Test
	public void kfzMitMehrerenReservierungenFreiGemaessUeberlappung() {
		Reservierung.create(hinterher, langeHinterher, kfz, mitglied);
		Reservierung.create(vorher, kurzVorher, kfz, mitglied);
		assertTrue(kfz.istFrei(langeVorher, vorher));
		assertFalse(kfz.istFrei(hinterher, langeHinterher));
	}
	
}
