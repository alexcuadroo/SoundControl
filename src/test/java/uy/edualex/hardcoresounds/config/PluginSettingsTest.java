package uy.edualex.hardcoresounds.config;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.Map;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

class PluginSettingsTest {
    @Test
    void selectsProfileByMinecraftVersionFamily() {
        var profile = new PluginSettings.PackProfile("26.1", URI.create("https://example.test/pack.zip"), "0".repeat(40), UUID.randomUUID());
        var second = new PluginSettings.PackProfile("26.2", URI.create("https://example.test/pack2.zip"), "1".repeat(40), UUID.randomUUID());
        var settings = new PluginSettings(true, true, Component.empty(), Map.of("26.1", profile, "26.2", second), true, true, true, 500, 250);
        assertSame(profile, settings.profileFor("26.1.2").orElseThrow());
        assertSame(second, settings.profileFor("26.2").orElseThrow());
        assertTrue(settings.profileFor("26.3").isEmpty());
    }
}
