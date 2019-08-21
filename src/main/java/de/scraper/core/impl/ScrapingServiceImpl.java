package de.scraper.core.impl;

import de.scraper.core.PlatformScraperPort;
import de.scraper.core.ScrapingService;
import de.scraper.core.model.PlatformData;
import de.scraper.core.model.ScraperType;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ScrapingServiceImpl implements ScrapingService {
    private final PlatformScraperPort platformScraperPort;

    public ScrapingServiceImpl(final PlatformScraperPort platformScraperPort) {
        this.platformScraperPort = platformScraperPort;
    }

    @Override
    public Stream<String> scrapeLinks(final ScraperType scraperType) {
        if (platformScraperPort.kompatibelMit(scraperType)) {
            return platformScraperPort.scrapeLinks();
        }

        return Stream.empty();
    }

    @Override
    public PlatformData scrapeData(final String link, final ScraperType scraperType) {
        if (platformScraperPort.kompatibelMit(scraperType)) {
            return platformScraperPort.scrapeData(link);
        }

        return null;
    }
}
