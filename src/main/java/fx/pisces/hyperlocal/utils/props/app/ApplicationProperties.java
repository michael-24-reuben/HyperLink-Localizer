package fx.pisces.hyperlocal.utils.props.app;

import fx.pisces.hyperlocal.utils.props.ControlProperties;

import java.io.File;

public class ApplicationProperties extends ControlProperties {
    private static File PROPERTIES_FILE;

    private ApplicationProperties(String propertyName) {
        super(PROPERTIES_FILE = getPropertiesFile(propertyName));
    }

    public ApplicationProperties() {
        super(PROPERTIES_FILE = getPropertiesFile("app.properties"));
    }

    public String computeProperty(String key) {
        return computeValue(super.getProperty(key), this, System.getProperties());
    }

    @Deprecated
    public static ApplicationProperties fromProjectProperties() {
        return new ApplicationProperties("");
    }


    private static File getPropertiesFile(String propertyName) {
        if (PROPERTIES_FILE != null) return PROPERTIES_FILE;

        return getPropertiesFile(ApplicationProperties.class.getPackage(), propertyName);
    }

}

