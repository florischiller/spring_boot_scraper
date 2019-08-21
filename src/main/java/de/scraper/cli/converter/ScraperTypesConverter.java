package de.scraper.cli.converter;

import de.scraper.core.model.ScraperType;
import org.springframework.stereotype.Component;

@Component
public class ScraperTypesConverter {

    public ScraperType convert(final String typeName) {
        if (typeName.equals("kita_verzeichnis")) {
            return ScraperType.KITA_VERZEICHNIS;
        }
        return null;
    }
}
