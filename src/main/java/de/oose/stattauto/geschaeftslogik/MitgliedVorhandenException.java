package de.oose.stattauto.geschaeftslogik;

public class MitgliedVorhandenException extends RuntimeException {

	private static final long serialVersionUID = -6862323721809754219L;

	private final Mitglied mitglied;
	
	public MitgliedVorhandenException(Mitglied vorhandenesMitglied) {
		mitglied = vorhandenesMitglied;
	}

	public Mitglied getMitglied() {
		return mitglied;
	}
}
