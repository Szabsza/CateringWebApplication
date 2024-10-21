package edu.bbte.idde.csim2126.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ConfigurationProvider {
    private static final String DEFAULT_CONFIG_FILE_NAME = "/config.json";
    private static final String CONFIG_FILE_NAME = "CONFIG_FILE";
    private static Config config = new Config();

    static {
        ObjectMapper objectMapper = new ObjectMapper();

        String configFile = System.getenv(CONFIG_FILE_NAME);
        if (configFile == null || configFile.isEmpty()) {
            configFile = DEFAULT_CONFIG_FILE_NAME;
        }

        try (InputStream inputStream = ConfigurationProvider.class.getResourceAsStream(configFile)) {
            config = objectMapper.readValue(inputStream, Config.class);
            log.info("Read following configuration: {}", config);
        } catch (IOException e) {
            log.error("Error loading configuration", e);
        }
    }

    public static Config getMainConfig() {
        return config;
    }

}
