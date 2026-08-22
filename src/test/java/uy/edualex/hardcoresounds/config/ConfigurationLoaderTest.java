package uy.edualex.hardcoresounds.config;

import static org.junit.jupiter.api.Assertions.*;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;

class ConfigurationLoaderTest {
    @Test
    void discoversNestedVersionProfiles() throws Exception {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.loadFromString("""
                resource-pack:
                  profiles:
                    '26':
                      '1':
                        url: 'https://example.test/26.1.zip'
                        sha1: '0000000000000000000000000000000000000000'
                        uuid: ''
                      '2':
                        url: ''
                        sha1: ''
                        uuid: ''
                """);
        var profiles = ConfigurationLoader.flattenProfileSections(
                yaml.getConfigurationSection("resource-pack.profiles"));
        assertEquals(2, profiles.size());
        assertEquals("https://example.test/26.1.zip", profiles.get("26.1").getString("url"));
        assertTrue(profiles.containsKey("26.2"));
    }
}
