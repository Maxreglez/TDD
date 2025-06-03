package de.oose.stattauto.geschaeftslogik;

public interface Tarif {
	double berechnePreis(int km, int stunden, Kategorie kategorie);
	double getStornopauschale();
}
