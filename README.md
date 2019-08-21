# spring_boot_scraper
Just some Experimations with Spring Boot, Scrapers and Clean Architecture.

Currently, this just scrapes around 2600 KiTas from Berlins
[Kita-Verzeichnis-Webpage](https://www.berlin.de/sen/jugend/familie-und-kinder/kindertagesbetreuung/kitas/verzeichnis/).

If I need another page in the future, i can add a new ScraperType and a corresponding scraper.

The output is a big file consisting of all found entries in the JSON-format.
