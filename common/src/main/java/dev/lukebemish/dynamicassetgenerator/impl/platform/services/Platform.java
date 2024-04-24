package dev.lukebemish.dynamicassetgenerator.impl.platform.services;

import java.nio.file.Path;

public interface Platform {
    Path getConfigFolder();
    Path getModDataFolder();
    String getModVersion();
}
