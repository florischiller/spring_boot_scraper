package de.scraper.kitaverzeichnis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plaetze {
    private int angeboten;
    private int unterDrei;
    private int ueberDrei;
    private int aufnahmealter;
}
