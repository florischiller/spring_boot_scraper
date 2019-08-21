package de.scraper.core;

import de.scraper.core.model.PlatformData;
import de.scraper.core.model.ScraperType;

import java.util.stream.Stream;

public interface DataOutputPort {

    int writeData(Stream<PlatformData> platformDataStream, ScraperType scraperType);
}
