package com.omertron.guiconfig.model;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author stuart.boston
 */
public class VersionInfo {

    protected String url;
    protected String version;
    protected int revision;

    /**
     * Default constructor
     */
    public VersionInfo() {
        this.url = "";
        this.version = "";
        this.revision = 0;
    }

    /**
     * Constructor with parameters
     * @param url
     * @param version
     * @param revision
     */
    public VersionInfo(String url, String version, int revision) {
        this.url = url;
        this.version = version;
        this.revision = revision;
    }

    /**
     * Get the value of URL
     *
     * @return the value of URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the value of URL
     *
     * @param url new value of URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the value of version
     *
     * @return the value of version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the value of version
     *
     * @param version new value of version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Get the value of revision
     *
     * @return the value of revision
     */
    public int getRevision() {
        return revision;
    }

    /**
     * Set the value of revision
     *
     * @param revision new value of revision
     */
    public void setRevision(int revision) {
        this.revision = revision;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("VersionInfo [");
        sb.append("[url=").append(url);
        sb.append("],[version=").append(version);
        sb.append("], [revision=").append(revision);
        sb.append("]]");
        return sb.toString();
    }

    /**
     * Check if the VersionInfo is valid
     * @return
     */
    public boolean isValid() {
        return StringUtils.isNotBlank(url) && StringUtils.isNotBlank(version) && revision > 0;
    }
}
