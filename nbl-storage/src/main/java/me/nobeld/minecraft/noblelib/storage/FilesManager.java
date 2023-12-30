package me.nobeld.minecraft.noblelib.storage;

import de.leonhard.storage.SimplixBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.exceptions.SimplixValidationException;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class FilesManager {
    public static String separator() {
        return FileSystems.getDefault().getSeparator();
    }
    /**
     * Create a new instance of a Yaml File.
     * @param path where the file will be saved.
     * @param resource the name of the resource from the plugin.
     * @return The yaml file instance.
     * @throws SimplixValidationException if the resource is not founded or another error.
     */
    public static Yaml registerFile(Path path, String resource) throws SimplixValidationException {
        Yaml yaml = SimplixBuilder.fromPath(path)
                .addInputStreamFromResource(resource)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setDataType(DataType.SORTED)
                .createYaml()
                .addDefaultsFromInputStream();
        yaml.forceReload();
        return yaml;
    }
    /**
     * Create a new instance of a Yaml File.
     * @param path where the file will be saved.
     * @return The yaml file instance.
     * @throws SimplixValidationException an error occurred.
     */
    public static Yaml registerFile(Path path) throws SimplixValidationException {
        Yaml yaml = SimplixBuilder.fromPath(path)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setDataType(DataType.SORTED)
                .createYaml()
                .addDefaultsFromInputStream();
        yaml.forceReload();
        return yaml;
    }
}
