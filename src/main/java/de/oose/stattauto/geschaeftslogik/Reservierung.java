package de.oose.stattauto.geschaeftslogik;

import java.time.LocalDateTime;

import com.google.common.base.Preconditions;

public class Reservierung {

	private final LocalDateTime von;
	private final LocalDateTime bis;

	private Status status;

	private Kfz kfz;

	private Fahrbericht fahrbericht;
	
	public static Reservierung create(LocalDateTime von, LocalDateTime bis, Kfz kfz, Mitglied mitglied) {
		Preconditions.checkNotNull(von);
		Preconditions.checkNotNull(bis);
		Preconditions.checkNotNull(kfz);
		Preconditions.checkNotNull(mitglied);
		Preconditions.checkArgument(von.isBefore(bis), "von < bis");

		Reservierung reservierung = new Reservierung(von, bis);
		kfz.addReservierung(reservierung);
		reservierung.kfz = kfz;
		reservierung.status = Status.AUSSTEHEND;
		mitglied.addReservierung(reservierung);
		return reservierung;
	}
	
	private Reservierung(LocalDateTime von, LocalDateTime bis) {
		this.von = von;
		this.bis = bis;
	}

	public boolean ueberlappt(LocalDateTime anfrageVon, LocalDateTime anfrageBis) {
		boolean zeitraumLiegtVorher = anfrageBis.isBefore(this.von) || anfrageBis.isEqual(this.von) ;
		boolean zeitraumLiegtHinterher = anfrageVon.isAfter(this.bis) || anfrageVon.isEqual(this.bis);
		return !zeitraumLiegtVorher && !zeitraumLiegtHinterher;
	}

	public Status getStatus() {
		return this.status;
	}

	public Kfz getKFZ() {
		return this.kfz;
	}

	public void addFahrbericht(Fahrbericht fahrbericht) {
		this.fahrbericht = fahrbericht;
	}

	public Fahrbericht getFahrbericht() {
		return this.fahrbericht;
	}
}
