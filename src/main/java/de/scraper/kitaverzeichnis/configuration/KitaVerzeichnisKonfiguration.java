package de.scraper.kitaverzeichnis.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("kita-verzeichnis")
@Data
public class KitaVerzeichnisKonfiguration {

    private String baseUrl;
    private String linkPath;
}
