package de.scraper.kitaverzeichnis.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreiePlaetze {
    @CsvBindByName
    private int angeboten;
    @CsvBindByName
    private int unterDrei;
    @CsvBindByName
    private int ueberDrei;
    @CsvBindByName
    private int aufnahmealter;
}