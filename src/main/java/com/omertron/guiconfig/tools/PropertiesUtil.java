package com.omertron.guiconfig.tools;

import com.omertron.guiconfig.GuiConfig;
import java.io.FileNotFoundException;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PropertiesUtil {

    private static final Logger LOGGER = Logger.getLogger(GuiConfig.class);
    private static final String PROPERTIES_CHARSET = "UTF-8";
    private static Properties guiConfigProperties = new Properties();

    public static boolean loadPropertyFile(String propertyFilename) {
        LOGGER.info("Using properties file " + propertyFilename);
        InputStream propertiesStream = ClassLoader.getSystemResourceAsStream(propertyFilename);
        Reader reader = null;

        try {
            if (propertiesStream == null) {
                propertiesStream = new FileInputStream(propertyFilename);
            }

            reader = new InputStreamReader(propertiesStream, PROPERTIES_CHARSET);
            guiConfigProperties.load(reader);
        } catch (IOException error) {
            // Output a warning if required.
            LOGGER.error("Failed loading file " + propertyFilename + ": Please check your configuration.");
            return false;
        } finally {
            try {
                if (propertiesStream != null) {
                    propertiesStream.close();
                }
            } catch (IOException e) {
                // Ignore
            }

            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // Ignore
            }
        }
        return true;
    }
    
    /**
     * Save properties to a file
     * @param propertyFilename
     * @return 
     */
    public static boolean savePropertyFile(String propertyFilename, String propertyComments) {
        FileOutputStream fos = null;
        Writer writer = null;
        
        try {
            LOGGER.info("Saving to property file " + propertyFilename);
            fos = new FileOutputStream(propertyFilename);
            writer = new OutputStreamWriter(fos);
            guiConfigProperties.store(writer, propertyComments);
            return true;
        } catch (FileNotFoundException error) {
            LOGGER.error("Failed to save properties to " + propertyFilename);
            return false;
        } catch (IOException ex) {
            LOGGER.error("Failed to save properties to " + propertyFilename);
            return false;
        } finally {
            try {
                fos.close();
            } catch (IOException error) {
                // Ignore
            }
            
            try {
                writer.close();
            } catch (IOException error) {
                // Ignore
            }
        }
    }

    /**
     * Get string value of a key
     * @param key
     * @return 
     */
    public static String getProperty(String key) {
        return guiConfigProperties.getProperty(key);
    }

    /**
     * Get string value of a key with default value
     * @param key
     * @param defaultValue
     * @return 
     */
    public static String getProperty(String key, String defaultValue) {
        return guiConfigProperties.getProperty(
                key, defaultValue);
    }

    /**
     * Return the key property as an integer
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getIntProperty(String key, String defaultValue) {
        String property = getProperty(key, defaultValue).trim();
        try {
            return Integer.parseInt(property);
        } catch (NumberFormatException nfe) {
            return Integer.parseInt(defaultValue);
        }
    }

    /**
     * Return the key property as an long
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLongProperty(String key, String defaultValue) {
        String property = getProperty(key, defaultValue).trim();
        try {
            return Long.parseLong(property);
        } catch (NumberFormatException nfe) {
            return Long.parseLong(defaultValue);
        }
    }

    /**
     * Return the key property as a boolean
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBooleanProperty(String key, String defaultValue) {
        String property = getProperty(key, defaultValue).trim();
        try {
            return Boolean.parseBoolean(property);
        } catch (NumberFormatException nfe) {
            return Boolean.parseBoolean(defaultValue);
        }
    }

    /**
     * Return the key property as a float
     * @param key
     * @param defaultValue
     * @return
     */
    public static float getFloatProperty(String key, String defaultValue) {
        String property = getProperty(key, defaultValue).trim();

        try {
            return Float.parseFloat(property);
        } catch (NumberFormatException nfe) {
            return Float.parseFloat(defaultValue);
        }
    }

    public static Set<Entry<Object, Object>> getEntrySet() {
        // Shamelessly adapted from: http://stackoverflow.com/questions/54295/how-to-write-java-util-properties-to-xml-with-sorted-keys
        return new TreeMap<Object, Object>(guiConfigProperties).entrySet();
    }

    public static void setProperty(String key, String value) {
        guiConfigProperties.setProperty(key, value);
    }

    /**
     * Clear all the properties
     */
    public static void clearProperties() {
        guiConfigProperties.clear();
    }

    /**
     * Store list (ordered) and keyword map.
     */
    @SuppressWarnings("serial")
    public static class KeywordMap extends HashMap<String, String> {

        private final List<String> keywords = new ArrayList<String>();

        public List<String> getKeywords() {
            return keywords;
        }
    }

    /**
     * Collect keywords list and appropriate keyword values.
     * Example:
     * my.languages = EN,FR
     * my.languages.EN = English
     * my.languages.FR = French
     *
     * @param prefix Key for keywords list and prefix for value searching.
     * @return Ordered keyword list and map.
     */
    public static KeywordMap getKeywordMap(String prefix, String defaultValue) {
        KeywordMap m = new KeywordMap();

        String languages = getProperty(prefix, defaultValue);
        if (!isBlank(languages)) {
            for (String lang : languages.split("[ ,]+")) {
                lang = StringUtils.trimToNull(lang);
                if (lang == null) {
                    continue;
                }
                m.keywords.add(lang);
                String values = getProperty(prefix + "." + lang);
                if (values != null) {
                    m.put(lang, values);
                }
            }
        }

        return m;
    }
}
