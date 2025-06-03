package de.oose.stattauto.geschaeftslogik;

import de.oose.stattauto.geschaeftslogik.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FahrberichtTest {

    @Test
    public void shouldCalculateDistance(){
        // Arrange
        int Akm = 100;
        int Ekm = 600;

        Fahrbericht fahrbericht = new Fahrbericht(Akm, Ekm, null, null);
        // Act
        int gesamt = Ekm - Akm;

        // Assert
        assertEquals(fahrbericht.calculateDistance(), gesamt);
    }


    @Test
    public void shouldCalculateDuration(){
        // Arrange
        LocalDateTime startDatum = DateUtil.toDate("23.02.2022 12:10");
        LocalDateTime endDatum = DateUtil.toDate("28.02.2022 12:10");;
        int Akm = 100;
        int Ekm = 600;
        Fahrbericht fahrbericht = new Fahrbericht(Akm, Ekm, startDatum, endDatum);

        // Assert
        assertEquals(120.0, fahrbericht.calculateDuration(startDatum, endDatum), 0.001);
    }

    @Test
    public void shouldCreateAFahrbericht(){


    }
}
