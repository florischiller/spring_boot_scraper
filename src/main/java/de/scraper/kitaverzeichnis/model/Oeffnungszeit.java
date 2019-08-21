package de.scraper.kitaverzeichnis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Oeffnungszeit {
    @JsonIgnore
    private DayOfWeek tag;
    private LocalTime von;
    private LocalTime bis;
}
