package io.saso.dash.config;

import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Singleton
public class DashConfig implements Config
{
    private static final String CONFIG_PATH = "config.yml";

    private static final Logger logger = LogManager.getLogger();

    private Optional<Map<String, Object>> map = Optional.empty();

    @Override
    public String getString(String key, String def)
    {
        initialize();

        return getValue(key, def);
    }

    @Override
    public Integer getInteger(String key, Integer def)
    {
        initialize();

        return getValue(key, def);
    }

    private <T> T getValue(String key, T def)
    {
        final List<String> keyParts = Arrays.asList(key.split(
                Pattern.quote(".")));

        int lastIndex = keyParts.size() - 1;
        Map subMap = this.map.get();

        for (String part : keyParts.subList(0, lastIndex)) {
            subMap = ((Map) subMap.get(part));
        }

        // noinspection unchecked
        return (T) subMap.getOrDefault(keyParts.get(lastIndex), def);
    }

    private void initialize()
    {
        if (map.isPresent()) return;

        final Yaml yaml = new Yaml();
        final Object o;

        try {
            o = yaml.load(new FileInputStream(CONFIG_PATH));
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            return;
        }

        // noinspection unchecked
        map = Optional.of((Map<String, Object>) o);

        logger.debug("Loaded config: {}", map.get());
    }
}
