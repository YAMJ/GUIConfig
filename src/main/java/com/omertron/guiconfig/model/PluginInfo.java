package com.omertron.guiconfig.model;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 * Contains information about the plugins that are available for YAMJ
 * @author stuart.boston
 */
public class PluginInfo {
    private static final Logger LOGGER = Logger.getLogger(PluginInfo.class);

    protected static final String UNKNOWN = "UNKNOWN";
    protected PluginType pluginType;
    protected String site;
    protected String language;
    protected String property;
    protected URL url;
    protected String moviedb;

    /**
     * Constructor for the PluginInfo using all fields
     * @param pluginType
     * @param site
     * @param language
     * @param property
     * @param url
     * @param moviedb
     */
    public PluginInfo(PluginType pluginType, String site, String language, String property, URL url, String moviedb) {
        this.pluginType = pluginType;
        this.site = site;
        this.language = language;
        this.property = property;
        this.url = url;
        this.moviedb = moviedb;
    }

    /**
     * Constructor for the PluginInfo using just the type
     * @param pluginType
     */
    public PluginInfo(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    /**
     * Enum for the plugin type.
     * There are currently only three plugin types: Movie, TV & Person
     */
    public enum PluginType {

        MOVIE, TV, PERSON;
    }

    /**
     * Get the plugin type
     * @return
     */
    public PluginType getPluginType() {
        return pluginType;
    }

    /**
     * Set the plugin type to one of the defined types
     * @param pluginType
     */
    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    /**
     * Get the website for the plugin
     * @return
     */
    public String getSite() {
        return site;
    }

    /**
     * Set the website for the plugin
     * @param site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * Get the language for the plugin
     * @return
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Get the moviedb code for the plugin (used internally by YAMJ)
     * @return
     */
    public String getMoviedb() {
        return moviedb;
    }

    /**
     * Get the property to be used in the property files
     * @return
     */
    public String getProperty() {
        return property;
    }

    /**
     * Get the plugin website URL
     * @return
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Set the plugin language
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Set the moviedb value
     * @param moviedb
     */
    public void setMoviedb(String moviedb) {
        this.moviedb = moviedb;
    }

    /**
     * Set the property for the properties file
     * @param property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Set the plugin website URL
     * @param url
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * Set the plugin website URL
     * @param sUrl
     */
    public void setUrl(String sUrl) {
        try {
            this.url = new URL(sUrl);
        } catch (MalformedURLException ex) {
            LOGGER.info("Invalid URL: " + sUrl);
        }
    }

    /**
     * Generate the toString for the object
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PluginInfo =[");
        sb.append("pluginType=").append(pluginType);
        sb.append("],[site=").append(site);
        sb.append("],[language=").append(language);
        sb.append("],[property=").append(property);
        sb.append("],[url=").append(url);
        sb.append("],[moviedb=").append(moviedb);
        sb.append("]]");
        return sb.toString();
    }

    /**
     * Compare two objects
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PluginInfo other = (PluginInfo) obj;
        if (this.pluginType != other.pluginType) {
            return false;
        }
        if ((this.property == null) ? (other.property != null) : !this.property.equals(other.property)) {
            return false;
        }
        if ((this.moviedb == null) ? (other.moviedb != null) : !this.moviedb.equals(other.moviedb)) {
            return false;
        }
        return true;
    }

    /**
     * Generate the hashcode for the object
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.pluginType != null ? this.pluginType.hashCode() : 0);
        hash = 31 * hash + (this.property != null ? this.property.hashCode() : 0);
        hash = 31 * hash + (this.moviedb != null ? this.moviedb.hashCode() : 0);
        return hash;
    }
}
