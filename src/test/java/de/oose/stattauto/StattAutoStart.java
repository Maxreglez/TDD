package de.oose.stattauto;

import de.oose.stattauto.geschaeftslogik.StattAuto;

public class StattAutoStart {
	public static void main(String[] args) {
		StattAuto stattAuto = new StattAuto();
		TestdatenGenerator generator = new TestdatenGenerator();
		generator.fill(stattAuto);
	}
}
