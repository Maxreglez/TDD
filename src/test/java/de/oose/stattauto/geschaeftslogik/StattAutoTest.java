package de.oose.stattauto.geschaeftslogik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import de.oose.stattauto.geschaeftslogik.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class StattAutoTest {

	private static final int MITGLIEDSNR = 234;
	private StattAuto stattAuto;
	private Mitglied peter;
	private Station station;
	private Kfz golfAnStation = new Kfz("Golf");
	private Kfz fiat = new Kfz("Fiat");
	private Kfz opel = new Kfz("Opel");

	@BeforeEach
	public void before() {
		peter = new Mitglied("Peter Putz", MITGLIEDSNR);
		stattAuto = new StattAuto();
		station = new Station("Christuskirche");
		stattAuto.addStation(station);
		stattAuto.addKfz(golfAnStation);
		golfAnStation.setStation(station);
		stattAuto.addKfz(opel);
		stattAuto.addKfz(fiat);
	}
	
	@Test
	@DisplayName("Eine neu angelegte Station ist in StattAuto vorhanden")
	public void stationHinzufuegen() {
		Collection<Station> stationen = stattAuto.getStationen();
		assertNotNull(stationen);
		assertEquals(1, stationen.size());

		Station station = new Station("Hauptbahnhof");
		stattAuto.addStation(station);
		stationen = stattAuto.getStationen();
		assertNotNull(stationen);
		assertEquals(2, stationen.size());
		assertTrue(stationen.contains(station));
	}
	
	@Test
	@DisplayName("NULL als Station wird nicht akzeptiert")
	public void nullStationHinzufuegenNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, 
			() -> stattAuto.addStation(null)
		);
	}
	
	@Test
	@DisplayName("NULL als KFZ wird nicht akzeptiert")
	public void nullKfzHinzufuegenNichtErlaubt() {
		assertThrows(IllegalArgumentException.class, 
			() -> stattAuto.addKfz(null)
		);
	}
	
	@Test
	@DisplayName("Ein neues Mitglied wird anhand seiner Mitgliedsnummer gefunden")
	public void mitgliedHinzufuegen() throws MitgliedVorhandenException {
		stattAuto.addMitglied(peter);
		Mitglied mitglied = stattAuto.getMitglied(MITGLIEDSNR);
		assertNotNull(mitglied);
		assertEquals(peter, mitglied);
	}

	@Test
	@DisplayName("NULL als Mitglied wird nichz akzeptiert")
	public void nullMitgliedHinzufuegenNichtErlaubt() throws MitgliedVorhandenException {
		assertThrows(IllegalArgumentException.class, 
			() -> stattAuto.addMitglied(null)
		);
	}

	@Test
	@DisplayName("Ein neues Mitglied mit bereits vorhandener Mitgliedsnummer wird nicht akzeptiert")
	public void mitgliedMitNummerVorhanden() throws MitgliedVorhandenException {
		stattAuto.addMitglied(peter);

		MitgliedVorhandenException exception = assertThrows(MitgliedVorhandenException.class, () -> stattAuto.addMitglied(new Mitglied("Zweiter", peter.getMitgliedsnr())));
		assertEquals(peter, exception.getMitglied());
	}

	@Test
	public void shouldReturnAllCarsWithoutBookings () {
		Collection<Kfz> expectedCollection = new ArrayList<>();
		LocalDateTime startDatum = DateUtil.toDate("23.02.2022 12:10");
		LocalDateTime endeDatum = DateUtil.toDate("28.02.2022 12:10");;

		expectedCollection.add(opel);
		expectedCollection.add(fiat);
		expectedCollection.add(golfAnStation);

		Collection<Kfz> freieKfz = stattAuto.findeFreieFahrzeuge(startDatum, endeDatum, null , null);
		assertEquals(expectedCollection.size(), freieKfz.size());

	}

	@Test
	public void shouldReturnNonEmptyCollectionNonConflictBookingWereAdded () {
		Collection<Kfz> expectedCollection = new ArrayList<>();
		expectedCollection.add(opel);
		expectedCollection.add(fiat);
		expectedCollection.add(golfAnStation);

		LocalDateTime startDatum = DateUtil.toDate("23.02.2022 12:10");
		LocalDateTime endeDatum = DateUtil.toDate("28.02.2022 12:10");

		LocalDateTime bookingStartDatum = DateUtil.toDate("20.02.2022 12:10");
		LocalDateTime bookingEndeDatum = DateUtil.toDate("22.02.2022 12:10");

		Reservierung.create(bookingStartDatum, bookingEndeDatum, fiat, peter);

		Collection<Kfz> freieKfz = stattAuto.findeFreieFahrzeuge(startDatum, endeDatum, null, null);
		assertEquals(expectedCollection.size(), freieKfz.size());

	}

	@Test
	public void shouldReturnNonEmptyCollectionConflictBookingWereAdded () {
		Collection<Kfz> expectedCollection = new ArrayList<>();
		expectedCollection.add(opel);
		expectedCollection.add(golfAnStation);

		LocalDateTime startDatum = DateUtil.toDate("23.02.2022 12:10");
		LocalDateTime endeDatum = DateUtil.toDate("28.02.2022 12:10");

		LocalDateTime bookingStartDatum = DateUtil.toDate("23.02.2022 12:10");
		LocalDateTime bookingEndeDatum = DateUtil.toDate("25.02.2022 12:10");

		Reservierung.create(bookingStartDatum, bookingEndeDatum, fiat, peter);

		Collection<Kfz> freieKfz = stattAuto.findeFreieFahrzeuge(startDatum, endeDatum, null, null);
		assertEquals(expectedCollection.size(), freieKfz.size());

	}


	@Test
	public void shouldTrowExecptionStartDateEmpty() {

		LocalDateTime endeDatum = DateUtil.toDate("28.02.2022 12:10");

		assertThrows(NullPointerException.class,
				() -> stattAuto.findeFreieFahrzeuge(null, endeDatum, null, null)
		);
	}

	@Test
	public void shouldTrowExecptionEndDateEmpty() {
		LocalDateTime startDatum = DateUtil.toDate("28.02.2022 12:10");

		assertThrows(NullPointerException.class,
				() -> stattAuto.findeFreieFahrzeuge(startDatum, null, null, null)
		);
	}

	@Test
	public void shouldTrowExecptionEndDateBigger() {
		LocalDateTime startDatum = DateUtil.toDate("28.02.2022 12:10");
		LocalDateTime endDatum = DateUtil.toDate("26.02.2022 12:10");

		assertThrows(IllegalArgumentException.class,
				() -> stattAuto.findeFreieFahrzeuge(startDatum, endDatum, null, null)
		);

	}


	@Test
	public void shouldReturnNonEmptyCollectionOnStationWithAKategorie() {
		LocalDateTime startDatum = DateUtil.toDate("23.02.2022 12:10");
		LocalDateTime endeDatum = DateUtil.toDate("28.02.2022 12:10");
		Station station = new Station("Station");
		Kategorie kategorie = Kategorie.KOMPAKT;

		Collection<Kfz> freieKfz = stattAuto.findeFreieFahrzeuge(startDatum, endeDatum, station, kategorie);

	}



	@ParameterizedTest
	@EnumSource
	public void shouldThrowExpectationWhenEndDateSmallerStartDate(Kategorie kategorie) {
		LocalDateTime startDatum = DateUtil.toDate("28.02.2022 12:10");
		LocalDateTime endeDatum = DateUtil.toDate("26.02.2022 12:10");
		Station station = new Station("Station");

		opel.setStation(station);
		opel.setKategorie(kategorie);
		assertThrows(IllegalArgumentException.class,
				() -> stattAuto.findeFreieFahrzeuge(startDatum, endeDatum, station, kategorie)
		);
	}

	@ParameterizedTest
	@EnumSource
	public void shouldThrowExpectationWhenStationIsNull(Kategorie kategorie) {
		LocalDateTime startDatum = DateUtil.toDate("28.02.2022 12:10");
		LocalDateTime endeDatum = DateUtil.toDate("26.02.2022 12:10");
		Station station = new Station("Station");

		opel.setStation(station);
		opel.setKategorie(kategorie);

		assertThrows(IllegalArgumentException.class,
				() -> stattAuto.findeFreieFahrzeuge(startDatum, endeDatum, null, kategorie)
		);
	}

	@ParameterizedTest
	@EnumSource
	public void shouldThrowExpectationWhenKategorieIsNull(Kategorie kategorie) {
		LocalDateTime startDatum = DateUtil.toDate("28.02.2022 12:10");
		LocalDateTime endeDatum = DateUtil.toDate("26.02.2022 12:10");
		Station station = new Station("Station");

		opel.setStation(station);
		opel.setKategorie(kategorie);

		assertThrows(IllegalArgumentException.class,
				() -> stattAuto.findeFreieFahrzeuge(startDatum, endeDatum, station, null)
		);
	}

	@ParameterizedTest
	@EnumSource
	public void shouldReturnNonEmptyCollectionConflictBookingWereAddedWithStationAndKategorie(Kategorie kategorie) {
		Collection<Kfz> expectedCollection = new ArrayList<>();
		Station station = new Station("Station");

		LocalDateTime startDatum = DateUtil.toDate("23.02.2022 12:10");
		LocalDateTime endeDatum = DateUtil.toDate("28.02.2022 12:10");

		LocalDateTime bookingStartDatum = DateUtil.toDate("23.02.2022 12:10");
		LocalDateTime bookingEndeDatum = DateUtil.toDate("25.02.2022 12:10");

		opel.setStation(station);
		opel.setKategorie(kategorie);

		Reservierung.create(bookingStartDatum, bookingEndeDatum, opel, peter);

		Collection<Kfz> freieKfz = stattAuto.findeFreieFahrzeuge(startDatum, endeDatum, station, kategorie);
		assertEquals(expectedCollection.size(), freieKfz.size());

	}


	@ParameterizedTest
	@EnumSource
	public void shouldReturnNonEmptyCollectionNonConflictBookingWereAddedWithStationAndKategorie(Kategorie kategorie) {
		Collection<Kfz> expectedCollection = new ArrayList<>();
		Station station = new Station("Station");
		opel.setStation(station);
		opel.setKategorie(kategorie);
		expectedCollection.add(opel);

		LocalDateTime startDatum = DateUtil.toDate("23.02.2022 12:10");
		LocalDateTime endeDatum = DateUtil.toDate("28.02.2022 12:10");

		LocalDateTime bookingStartDatum = DateUtil.toDate("20.02.2022 12:10");
		LocalDateTime bookingEndeDatum = DateUtil.toDate("22.02.2022 12:10");

		Reservierung.create(bookingStartDatum, bookingEndeDatum, opel, peter);

		Collection<Kfz> freieKfz = stattAuto.findeFreieFahrzeuge(startDatum, endeDatum, station, kategorie);
		assertEquals(expectedCollection.size(), freieKfz.size());

	}
}
