package de.scraper.cli;


import de.scraper.cli.configuration.ScraperKonfiguration;
import de.scraper.cli.converter.ScraperTypesConverter;
import de.scraper.core.DataOutputPort;
import de.scraper.core.ScrapingService;
import de.scraper.core.model.PlatformData;
import de.scraper.core.model.ScraperType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class ScrapingController {
    private final ScraperKonfiguration scraperKonfiguration;
    private final DataOutputPort dataOutputPort;

    private final ScrapingService scrapingService;

    private final ScraperTypesConverter scraperTypesConverter;

    private static final Logger LOG = LoggerFactory.getLogger(ScrapingController.class);

    public ScrapingController(
            final ScraperKonfiguration scraperKonfiguration,
            final DataOutputPort dataOutputPort,
            final ScrapingService scrapingService,
            final ScraperTypesConverter scraperTypesConverter) {
        this.scraperKonfiguration = scraperKonfiguration;
        this.dataOutputPort = dataOutputPort;
        this.scrapingService = scrapingService;
        this.scraperTypesConverter = scraperTypesConverter;
    }

    public void scrapeData() {
        for (final String typeName : scraperKonfiguration.getToScrape()) {
            LOG.info("Scraping: " + typeName);
            final ScraperType scraperType = scraperTypesConverter.convert(typeName);
            final Stream<PlatformData> platformDataStream = scrapingService.scrapeLinks(scraperType)
                    .map(s -> scrapingService.scrapeData(s, scraperType))
                    .filter(Objects::nonNull);
            LOG.info("Scraped: " + dataOutputPort.writeData(platformDataStream, scraperType));

        }
    }

    public void scrapeSingleData() {
        for (final String typeName : scraperKonfiguration.getToScrape()) {
            final ScraperType scraperType = scraperTypesConverter.convert(typeName);
            final Stream<PlatformData> platformDataStream = Stream.of(scrapingService.scrapeData(
                    "/KitaDetailsNeu.aspx?ID=477129", scraperType));
            LOG.info("Scraped: " + dataOutputPort.writeData(platformDataStream, scraperType));
        }
    }
}
