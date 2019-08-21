package de.scraper.core;

import de.scraper.core.model.PlatformData;
import de.scraper.core.model.ScraperType;

import java.util.stream.Stream;

public interface PlatformScraperPort {

    boolean kompatibelMit(ScraperType scraperType);

    Stream<String> scrapeLinks();

    PlatformData scrapeData(String link);
}
