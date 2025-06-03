package de.oose.stattauto.geschaeftslogik;

import java.time.LocalDateTime;
import java.util.*;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public class  StattAuto {

	private final Map<Integer, Mitglied> mitglieder = new HashMap<>();
	private final List<Kfz> fahrzeuge = new ArrayList<>();
	private final List<Station> stationen = new ArrayList<>();
	
	public void addKfz(Kfz kfz) {
		Preconditions.checkArgument(kfz != null);
		fahrzeuge.add(kfz);
	}

	public void addStation(Station station) {
		Preconditions.checkArgument(station != null);
		stationen.add(station);
	}
	
	public Collection<Station> getStationen() {
		return Collections.unmodifiableCollection(stationen);
	}

	public void addMitglied(Mitglied mitglied) throws MitgliedVorhandenException {
		Preconditions.checkArgument(mitglied != null);

		Integer mitgliedsnr = mitglied.getMitgliedsnr();
		if (mitglieder.containsKey(mitgliedsnr)) {
			throw new MitgliedVorhandenException(mitglieder.get(mitgliedsnr));
		}
		mitglieder.put(mitgliedsnr, mitglied);
	}

	public Mitglied getMitglied(int mitgliedsnr) {
		return mitglieder.get(mitgliedsnr);
	}

	public Collection<Kfz> findeFreieFahrzeuge(LocalDateTime startDatum, LocalDateTime endeDatum, Station station, Kategorie kategorie) {
		Preconditions.checkNotNull(startDatum);
		Preconditions.checkNotNull(endeDatum);
		Preconditions.checkArgument(startDatum.isBefore(endeDatum), "von < bis");

		ArrayList<Kfz> list = new ArrayList<>();
		for(Kfz kfz: fahrzeuge){
			if(station != null  || kategorie != null) {
				if (kfz.istFrei(startDatum, endeDatum) && kfz.istAn(station) && kfz.hatKategorie(kategorie)) {
					list.add(kfz);
				}
			} else{
				if (kfz.istFrei(startDatum, endeDatum)) {
					list.add(kfz);
				}
			}
		}
		return list;
	}
}
