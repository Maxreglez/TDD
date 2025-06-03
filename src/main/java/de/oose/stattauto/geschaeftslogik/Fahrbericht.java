package de.oose.stattauto.geschaeftslogik;

import de.oose.stattauto.geschaeftslogik.util.DateUtil;

import java.time.Duration;
import java.time.LocalDateTime;

public class Fahrbericht {
    int akm;
    int ekm;
    LocalDateTime startDatum;
    LocalDateTime endDatum;
    public Fahrbericht(int akm, int ekm, LocalDateTime startDatum, LocalDateTime endDatum) {
        this.akm = akm;
        this.ekm = ekm;
        this.startDatum = startDatum;
        this.endDatum = endDatum;
    }


    public int calculateDistance() {

        return ekm - akm;
    }


    public double calculateDuration(LocalDateTime startDatum, LocalDateTime endDatum) {
        Duration duration = Duration.between(startDatum, endDatum);
        return duration.toHours();
    }
}
