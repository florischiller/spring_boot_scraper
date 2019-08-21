package de.scraper.kitaverzeichnis;

import de.scraper.core.PlatformScraperPort;
import de.scraper.core.model.PlatformData;
import de.scraper.core.model.ScraperType;
import de.scraper.kitaverzeichnis.configuration.KitaVerzeichnisKonfiguration;
import de.scraper.kitaverzeichnis.service.KitaVerzeichnisPageInterpreter;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class KitaVerzeichnisScraperAdapterImpl implements PlatformScraperPort {
    private final KitaVerzeichnisKonfiguration kitaVerzeichnisKonfiguration;
    private final KitaVerzeichnisPageInterpreter kitaVerzeichnisPageInterpreter;

    private static final Logger LOG = LoggerFactory.getLogger(KitaVerzeichnisScraperAdapterImpl.class);


    public KitaVerzeichnisScraperAdapterImpl(final KitaVerzeichnisKonfiguration kitaVerzeichnisKonfiguration,
                                             final KitaVerzeichnisPageInterpreter kitaVerzeichnisPageInterpreter) {
        this.kitaVerzeichnisKonfiguration = kitaVerzeichnisKonfiguration;
        this.kitaVerzeichnisPageInterpreter = kitaVerzeichnisPageInterpreter;
    }


    @Override
    public boolean kompatibelMit(final ScraperType scraperType) {
        return scraperType == ScraperType.KITA_VERZEICHNIS;
    }

    @Override
    public Stream<String> scrapeLinks() {
        final String path = getKitaVerzeichnisPath();

        final Optional<Document> pageFromPath = getPageFromPath(path);

        return pageFromPath.map(
                document -> document.select("a[id^=DataList_Kitas_HLinkKitaName]").stream()
                        .map(elements -> elements.attr("href"))
                        .map(s -> s.replaceAll("\\s", ""))
                        .map(s -> "/" + s)
                        .map(String::trim)
        ).orElseGet(Stream::empty);
    }

    private String getKitaVerzeichnisPath() {
        return kitaVerzeichnisKonfiguration.getBaseUrl() + kitaVerzeichnisKonfiguration.getLinkPath();
    }

    @Override
    public PlatformData scrapeData(final String link) {
        final String path = kitaVerzeichnisKonfiguration.getBaseUrl() + link;

        final Optional<Document> pageFromPath = getPageFromPath(path);

        try {
            Thread.sleep(100);
            return pageFromPath
                    .map(kitaVerzeichnisPageInterpreter::interpretPage)
                    .orElse(null);
        } catch (final InterruptedException | RuntimeException e) {
            LOG.error("Fehler für die KiTa " + link, e);
        }
        return null;
    }

    private Optional<Document> getPageFromPath(final String path) {
        try {
            return Optional.of(Jsoup.connect(path)
                    .header("Host", "brickseek.com")
                    .header("Connection", "keep-alive")
//              .header("Content-Length", ""+c.request().requestBody().length())
                    .header("Cache-Control", "max-age=0")
                    .header("Origin", getKitaVerzeichnisPath())
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.48 Safari/537.36")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .referrer(getKitaVerzeichnisPath())
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "en-US,en;q=0.8")
                    .maxBodySize(1024 * 1024 * 1024).get());
        } catch (final HttpStatusException e) {
            LOG.error("Fehler beim Laden der Seite von " + path + "\nmit Status Code " + e.getStatusCode(), e);
        } catch (final IOException e) {
            LOG.error("Fehler beim Laden der Seite von " + path, e);
        }

        return Optional.empty();
    }
}
