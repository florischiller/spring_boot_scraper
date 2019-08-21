package de.scraper.kitaverzeichnis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adresse {
    private String strasseHausnummer;
    private String plz;
    private String ort;
    private String bezirk;
}
