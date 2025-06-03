package de.oose.stattauto.geschaeftslogik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

public class Kfz {

	private final String name;
	private final List<Reservierung> reservierungen = new ArrayList<>();
	private Station station;
	private Kategorie kategorie;
	
	public Kfz(String name) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(name));
		this.name = name;
	}

	public Kfz(String name, Station station) {
		this(name);
		setStation(station);
	}

	public Kfz(String name, Station station, Kategorie kategorie) {
		this(name, station);
		this.kategorie = kategorie;
	}

	public String getName() {
		return name;
	}

	public Kategorie getKategorie() {
		return this.kategorie;
	}
	public boolean istFrei(LocalDateTime von, LocalDateTime bis) {
		for (Reservierung reservierung : reservierungen) {
			if (reservierung.ueberlappt(von, bis)) {
				return false;
			}
		}
		return true;
	}

	public void addReservierung(Reservierung reservierung) {
		if (reservierung != null) {
			reservierungen.add(reservierung);
		}
	}

	public void setStation(Station station) {
		Preconditions.checkNotNull(station);
		this.station = station;
	}

	public boolean istAn(Station station) {
		if(this.station == null){
			return false;
		}
		return this.station.equals(station);
	}
	
	public boolean hatKategorie(Kategorie kategorie) {
		return this.kategorie == kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}
}
