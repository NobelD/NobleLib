package me.nobeld.minecraft.noblelib;

import de.leonhard.storage.internal.FlatFile;
import de.leonhard.storage.sections.FlatFileSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataResolver {
    public static <T> T getValue(FlatFile file, ConfigContainer<T> cont) {
        T val = file.get(cont.path(), cont.def());
        if (val instanceof String s) {
            if (s.isBlank() || s.isEmpty()) val = null;
        }
        return val;
    }
    public static <T> void setValue(FlatFile file, ConfigContainer<T> cont, T value) {
        file.set(cont.path(), value);
    }
    public static <T> List<T> getList(FlatFile file, ConfigContainer<T> container) {
        List<T> result;
        try {
            result = file.getListParameterized(container.path());
        } catch (Exception ignored) {
            result = null;
        }
        return result;
    }
    public static List<String> getStringList(FlatFile file, ConfigContainer<String> container) {
        List<String> result;
        try {
            result = file.getStringList(container.path());
        } catch (Exception ignored) {
            result = null;
        }
        return result;
    }
    public static <X extends Enum<X>> X getEnum(FlatFile file, ConfigContainer<X> container) {
        X result;
        try {
            result = file.getEnum(container.path(), container.def().getDeclaringClass());
        } catch (Exception ignored) {
            result = container.def();
        }
        return result;
    }
    public static <K, V> Map<K, V> getMap(FlatFile file, ConfigContainerDouble<K, V> container) {
        Map<K, V> result;
        try {
            result = file.getMapParameterized(container.path());
        } catch (Exception ignored) {
            result = null;
        }
        return result;
    }
    public static Map<String, String> getKeyValue(FlatFile file, ConfigContainerDouble<String, String> container) {
        Map<String, String> result = new HashMap<>();
        List<String> invalid = new ArrayList<>();
        try {
            FlatFileSection section = file.getSection(container.path());
            for (String line : section.singleLayerKeySet()) {
                try {
                    result.put(line, section.getString(line));
                } catch (Exception ignored) {
                    invalid.add(line);
                }
            }
            for (String line : invalid) {
                section.remove(line);
            }

        } catch (Exception ignored) {
            result = null;
        }
        return result;
    }
}
