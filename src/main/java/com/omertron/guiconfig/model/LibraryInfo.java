package com.omertron.guiconfig.model;

import com.omertron.guiconfig.tools.DOMHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Contains information about each library entry
 * @author stuart.boston
 */
public class LibraryInfo {
    private static final Logger LOGGER = Logger.getLogger(LibraryInfo.class);

    public static final String LIB_LIBRARY = "library";
    public static final String LIB_PATH = "path";
    public static final String LIB_PLAYER_PATH = "playerPath";
    public static final String LIB_EXCLUDE = "exclude";
    public static final String LIB_NAME = "name";
    public static final String LIB_DESCRIPTION = "description";
    public static final String LIB_PREBUF = "prebuf";
    public static final String LIB_SCRAPE_LIBRARY = "scrapeLibrary";
    /*
     * Library information
     */
    private String path;
    private String playerPath;
    private String exclude = "sample,tmp/,temp/,RECYCLE.BIN/,/._";
    private String description;
    private String prebuf;
    private boolean scrapeLibrary = Boolean.TRUE;

    /**
     * Construct an object from the parameters
     * @param path
     * @param playerPath
     * @param description
     * @param prebuf
     */
    public LibraryInfo(String path, String playerPath, String description, String prebuf) {
        this.path = path;
        this.playerPath = playerPath;
        this.description = description;
        this.prebuf = prebuf;
    }

    /**
     * Construct an empty object
     */
    public LibraryInfo() {
    }

    /**
     * Get the value of path
     *
     * @return the value of path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set the value of path
     *
     * @param path new value of path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get the value of playerPath
     *
     * @return the value of playerPath
     */
    public String getPlayerPath() {
        return playerPath;
    }

    /**
     * Set the value of playerPath
     *
     * @param playerPath new value of playerPath
     */
    public void setPlayerPath(String playerPath) {
        this.playerPath = playerPath;
    }

    /**
     * Get the value of exclude
     *
     * @return the value of exclude
     */
    public String getExclude() {
        return exclude;
    }

    /**
     * Set the value of exclude
     *
     * @param exclude new value of exclude
     */
    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of prebuf
     *
     * @return the value of prebuf
     */
    public String getPrebuf() {
        return prebuf;
    }

    /**
     * Set the value of prebuf
     *
     * @param prebuf new value of prebuf
     */
    public void setPrebuf(String prebuf) {
        this.prebuf = prebuf;
    }

    /**
     * Get the value of scrapeLibrary
     *
     * @return the value of scrapeLibrary
     */
    public boolean isScrapeLibrary() {
        return scrapeLibrary;
    }

    /**
     * Set the value of scrapeLibrary
     *
     * @param scrapeLibrary new value of scrapeLibrary
     */
    public void setScrapeLibrary(boolean scrapeLibrary) {
        this.scrapeLibrary = scrapeLibrary;
    }

    /**
     * Generate a String representation
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("library =[");
        sb.append("[").append(LIB_PATH).append("=").append(path);
        sb.append("],[").append(LIB_PLAYER_PATH).append("=").append(playerPath);
        sb.append("],[").append(LIB_EXCLUDE).append("=").append(exclude);
        sb.append("],[").append(LIB_DESCRIPTION).append("=").append(description);
        sb.append("],[").append(LIB_PREBUF).append("=").append(prebuf);
        sb.append("],[").append(LIB_SCRAPE_LIBRARY).append("=").append(scrapeLibrary);
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
        final LibraryInfo other = (LibraryInfo) obj;
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        if ((this.playerPath == null) ? (other.playerPath != null) : !this.playerPath.equals(other.playerPath)) {
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
        int hash = 7;
        hash = 11 * hash + (this.path != null ? this.path.hashCode() : 0);
        hash = 11 * hash + (this.playerPath != null ? this.playerPath.hashCode() : 0);
        return hash;
    }

    /**
     * Determine if the library entry is valid
     * @return
     */
    public boolean isValid() {
        if (StringUtils.isBlank(path) || StringUtils.isBlank(playerPath)) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * Return the library info object as a DOM element
     * @return
     */
    public Element getLibraryAsElement(Document docLibrary) {
        if (!isValid()) {
            return null;
        }

        Element eLibrary = docLibrary.createElement(LIB_LIBRARY);

        if (StringUtils.isNotBlank(path)) {
            DOMHelper.appendChild(docLibrary, eLibrary, LIB_PATH, path);
        }

        if (StringUtils.isNotBlank(playerPath)) {
            DOMHelper.appendChild(docLibrary, eLibrary, LIB_PLAYER_PATH, playerPath);
        }

        if (StringUtils.isNotBlank(exclude)) {
            DOMHelper.appendChild(docLibrary, eLibrary, LIB_EXCLUDE, "", LIB_NAME, exclude);
        }

        if (StringUtils.isNotBlank(description)) {
            DOMHelper.appendChild(docLibrary, eLibrary, LIB_DESCRIPTION, description);
        }

        if (StringUtils.isNotBlank(prebuf)) {
            DOMHelper.appendChild(docLibrary, eLibrary, LIB_PREBUF, prebuf);
        }

        DOMHelper.appendChild(docLibrary, eLibrary, LIB_SCRAPE_LIBRARY, String.valueOf(scrapeLibrary));

        return eLibrary;
    }
}
