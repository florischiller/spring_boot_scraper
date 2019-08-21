package de.scraper.kitaverzeichnis.model;

import de.scraper.core.model.PlatformData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kita implements PlatformData {
    private String name;
    private String art;
    private String telefon;
    private String email;
    private String link;
    private String paedagogischeSchwerpunkt;
    private String paedagogischeAnsaetze;
    private String thematischeSchwerpunkt;
    private String mehrsprachigkeit;
    private Oeffnungszeit montag;
    private Oeffnungszeit dienstag;
    private Oeffnungszeit mittwoch;
    private Oeffnungszeit donnerstag;
    private Oeffnungszeit freitag;
    private Traeger traeger;
    private Adresse adresse;
    private Plaetze plaetze;
}
