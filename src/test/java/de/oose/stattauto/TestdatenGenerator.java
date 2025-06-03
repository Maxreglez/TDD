package de.oose.stattauto;

import de.oose.stattauto.geschaeftslogik.Kfz;
import de.oose.stattauto.geschaeftslogik.Mitglied;
import de.oose.stattauto.geschaeftslogik.MitgliedVorhandenException;
import de.oose.stattauto.geschaeftslogik.Reservierung;
import de.oose.stattauto.geschaeftslogik.Station;
import de.oose.stattauto.geschaeftslogik.StattAuto;
import de.oose.stattauto.geschaeftslogik.util.DateUtil;

public class TestdatenGenerator {

	public void fill(StattAuto stattAuto) {
		Mitglied peter = new Mitglied("Peter Putz", 1000);
		Mitglied sonja = new Mitglied("Sonja Sonnig", 1001);
		try {
			stattAuto.addMitglied(peter);
		} catch (MitgliedVorhandenException ignore) {/*ignore*/}
		try {
			stattAuto.addMitglied(sonja);
		} catch (MitgliedVorhandenException ignore) {/*ignore*/}
		
		Station hauptbahnhof = new Station("Hauptbahnhof");
		Station flughafen = new Station("Flughafen");
		Station bubbelweg = new Station("Bubbelweg");
		Station tripptrapp = new Station("Tripptrapp");

		stattAuto.addStation(hauptbahnhof);
		stattAuto.addStation(flughafen);
		stattAuto.addStation(bubbelweg);
		stattAuto.addStation(tripptrapp);
		
		Kfz golfHbf = new Kfz("Golf", hauptbahnhof);
		Kfz opelHbf = new Kfz("Opel", hauptbahnhof);
		stattAuto.addKfz(golfHbf);
		stattAuto.addKfz(opelHbf);
		
		Kfz golfFlugh1 = new Kfz("Golf", flughafen);
		Kfz golfFlugh2 = new Kfz("Golf", flughafen);
		Kfz toyotaFlugh1 = new Kfz("Toyota", flughafen);
		Kfz toyotaFlugh2 = new Kfz("Toyota", flughafen);
		stattAuto.addKfz(golfFlugh1);
		stattAuto.addKfz(golfFlugh2);
		stattAuto.addKfz(toyotaFlugh1);
		stattAuto.addKfz(toyotaFlugh2);

		stattAuto.addKfz(new Kfz("Golf", bubbelweg));
		stattAuto.addKfz(new Kfz("Opel", bubbelweg));

		stattAuto.addKfz(new Kfz("Golf", tripptrapp));
		
		Reservierung.create(DateUtil.toDate("23.04.2022 13:00"), DateUtil.toDate("25.04.2022 15:00"), golfHbf, peter);
		Reservierung.create(DateUtil.toDate("29.04.2022 10:00"), DateUtil.toDate("02.05.2022 18:00"), opelHbf, peter);
		
		Reservierung.create(DateUtil.toDate("04.05.2022 09:00"), DateUtil.toDate("04.05.2022 20:00"), toyotaFlugh1, sonja);
	}
}
