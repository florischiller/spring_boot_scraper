package de.scraper.core;

import de.scraper.core.model.PlatformData;
import de.scraper.core.model.ScraperType;

import java.util.stream.Stream;

public interface ScrapingService {

    Stream<String> scrapeLinks(ScraperType scraperType);

    PlatformData scrapeData(String link, ScraperType scraperType);
}
