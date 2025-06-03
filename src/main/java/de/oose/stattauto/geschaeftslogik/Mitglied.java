package de.oose.stattauto.geschaeftslogik;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

public class Mitglied {

	private final String name;
	private final List<Reservierung> reservierungen = new ArrayList<>();
	private final int mitgliedsnr;
	
	public Mitglied(String name, int mitgliedsnr) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(name));
		Preconditions.checkArgument(mitgliedsnr > 0, "mitgliedsnr > 0");
		this.name = name;
		this.mitgliedsnr = mitgliedsnr;
	}

	public String getName() {
		return name;
	}

	public void addReservierung(Reservierung reservierung) {
		reservierungen.add(reservierung);
	}

	public Collection<Reservierung> getReservierungen() {
		return Collections.unmodifiableCollection(reservierungen );
	}

	public int getMitgliedsnr() {
		return mitgliedsnr;
	}

}
