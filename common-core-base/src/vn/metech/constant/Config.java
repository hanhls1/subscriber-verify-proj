package vn.metech.constant;

public class Config {
    private Config() {}

    private static final String SYSTEM_CONFIG_CACHE_KEY_PREFIX = "config:system:";
    private static final String SERVICE_CONFIG_CACHE_KEY_PREFIX = "config:service";

    public static String systemConfigKey(String config) {
        return SYSTEM_CONFIG_CACHE_KEY_PREFIX + config;
    }

    public static String serviceConfigKey(String config) {
        return SERVICE_CONFIG_CACHE_KEY_PREFIX + config;
    }

    public static class System {

    }

    public static class Service {
        
    }
}
