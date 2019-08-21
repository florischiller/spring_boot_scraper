package de.scraper.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.scraper.core.DataOutputPort;
import de.scraper.core.model.PlatformData;
import de.scraper.core.model.ScraperType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JsonDataOutputAdapterImpl implements DataOutputPort {

    private final ObjectMapper objectMapper;


    @Value("${csv.output-directory}")
    private String outputDirectory;

    public JsonDataOutputAdapterImpl(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public int writeData(final Stream<PlatformData> platformDataStream, final ScraperType scraperType) {
        final List<PlatformData> platformDataList = platformDataStream.collect(Collectors.toList());

        try {
            objectMapper.writeValue(Paths.get(buildFilePath(scraperType)).toFile(), platformDataList);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return platformDataList.size();
    }

    private String buildFilePath(final ScraperType scraperType) {
        return outputDirectory + scraperType.name() + "-" + LocalDate.now() + ".json";
    }
}
