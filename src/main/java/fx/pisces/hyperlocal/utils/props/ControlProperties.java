package fx.pisces.hyperlocal.utils.props;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControlProperties extends Properties {
    protected final Properties properties;
    private final File envFile;

    protected ControlProperties(File propertiesFile) {
        super();
        envFile = propertiesFile;
        try {
            properties = new Properties();
            properties.load(new FileReader(envFile));
            super.load(new FileReader(envFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ControlProperties fromProperties(Path propertiesFile) {
        return new ControlProperties(propertiesFile.toFile());
    }

    public Properties getProperties() {
        return properties;
    }

    protected Path getPropertyPath() {
        return Path.of(envFile.getAbsolutePath());
    }

    @Override
    public @NotNull Set<Object> keySet() {
        return properties.keySet();
    }

    @Override
    public @NotNull Set<Map.Entry<Object, Object>> entrySet() {
        return properties.entrySet();
    }

    public void forEach(BiConsumer<? super Object, ? super Object> action) {
        properties.forEach(action);
    }
    @Override
    public boolean containsKey(Object key) {
        // Check if the property contains the key
        return properties.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        // Retrieves property, returns default if not found
        return properties.getProperty(key, defaultValue);
    }

    public Map<String, String> getPropertyPrefix(String key) {
        Map<String, String> prefixes = new HashMap<>();

        for (String propKeys : properties.stringPropertyNames()) {
            if (Pattern.compile("^\\s*" + Pattern.quote(key) + "\\s*([=.])").matcher(propKeys).find()) {
                String value = properties.getProperty(propKeys);
                prefixes.put(propKeys, value);
            }
        }
        return prefixes;
    }

    @Override
    public Object setProperty(String key, String value) {
        properties.setProperty(key, value);
        return super.setProperty(key, value);
    }

    @Deprecated
    @Override
    public Object put(Object key, Object value) {
        properties.put(key, value);
        return super.put(key, value);
    }

    @Deprecated
    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    public Object setPropertyIfAbsent(String key, String value) {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        properties.setProperty(key, value);
        return super.setProperty(key, value);
    }

    @Override
    public Object remove(Object key) {
        // Removes the property
        properties.remove(key);
        return super.remove(key);
    }

    public void reload() throws IOException {
        // Reloads the properties from the file
        try (FileReader fileReader = new FileReader(envFile)) {
            clear();
            properties.load(fileReader);
            super.load(fileReader);
        }
    }

    @Override
    public void clear() {
        properties.clear();
        super.clear();
    }

    public void store() {
        try (FileOutputStream fos = new FileOutputStream(envFile)) {
            properties.store(fos, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        // Custom string representation of all properties
        return properties.toString();
    }

    @SafeVarargs
    protected static <P extends Properties> String computeValue(String value, P appProperties, P... more) {
        combineProperties(appProperties, more);
        Matcher propertyMatcher = Pattern.compile("\\$\\{([^}]+)}").matcher(value);

        StringBuilder result = new StringBuilder();
        while (propertyMatcher.find()) {
            String property = propertyMatcher.group(1);
            String aliasPropValue = appProperties.getProperty(property);
            String propValue = computeValue(aliasPropValue, appProperties).replace("\\", "/");

            propertyMatcher.appendReplacement(result, Matcher.quoteReplacement(propValue));
        }
        propertyMatcher.appendTail(result);

        return result.toString();
    }

    private static <P extends Properties> void combineProperties(P appProperties, P[] more) {
        for (P property : more) {
            appProperties.putAll(property);
        }
    }

    protected static File getPropertiesFile(Package classPkg, String fnNoExtension) {
        String packagePath = classPkg.getName().replace(".", "/");
        try {
            File resourceFolder = new File("./src/main/resources").getCanonicalFile();
            return Path.of(resourceFolder.toString(), packagePath, fnNoExtension).toFile();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        /*ProjectDomain projectDomain = new ProjectDomain(new Modular(SystemProperties.class), "com-Properties/src/main/resources");
        System.out.println("projectDomain = " + projectDomain.getRelativePath());
        System.out.println(projectDomain.packageAsPath(SystemProperties.class).toFile().getParentFile());*/
        Class<?> clazz = ControlProperties.class;
        String classLocation = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println("Class location: " + classLocation);
    }
}

