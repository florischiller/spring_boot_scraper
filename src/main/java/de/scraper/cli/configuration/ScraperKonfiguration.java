package de.scraper.cli.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("scraper")
@Data
public class ScraperKonfiguration {

    private List<String> toScrape;
}