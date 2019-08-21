package de.scraper;

import de.scraper.cli.ScrapingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CliRunner implements CommandLineRunner {
    private final ScrapingController scrapingController;

    private static final Logger LOG = LoggerFactory.getLogger(CliRunner.class);

    public CliRunner(final ScrapingController scrapingController) {
        this.scrapingController = scrapingController;
    }

    public static void main(final String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(CliRunner.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(final String... args) {
        LOG.info("EXECUTING : command line runner");

        scrapingController.scrapeData();
        //scrapingController.scrapeSingleData();
    }
}