package com.omertron.guiconfig;

import com.omertron.guiconfig.model.VersionInfo;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class to determine the latest version of the YAMJ software
 * @author stuart.boston
 */
public class YamjVersion {

    private static final Logger LOGGER = Logger.getLogger(GuiConfig.class);
    private static final Pattern PATTERN_VERSION = Pattern.compile("(?:.*)[v](.+)(?:\\Q.zip\\E)(?:.*)");
    private static final Pattern PATTERN_REVISION = Pattern.compile("(?:.*)[r](\\d+)\\D(?:.*)");
    /*
     * Web page URLs
     */
    private static final String WEB_DOWNLOAD_PAGE = "http://code.google.com/p/moviejukebox/downloads/list";
    private static final String WEB_SOURCE_PAGE = "http://code.google.com/p/moviejukebox/source/list";
    private static final String URL_SNAPSHOT_DOWNLOAD = "http://mediaplayersite.com/sites/default/files/YAMJ/yamj-{VERSION}-SNAPSHOT-r{REVISION}-bin.zip";

    public YamjVersion() {
    }

    /**
     * Get the version information for the official releases from the download page
     * @return
     */
    public static VersionInfo parseReleaseInformation() {
        VersionInfo versionInfo;
        Document doc;

        Connection jConn = Jsoup.connect(WEB_DOWNLOAD_PAGE);

        try {
            // Get the whole web page
            doc = jConn.get();

            String webPage = doc.toString();
            int start = webPage.indexOf("resultstable");
            start = webPage.indexOf("<table ", start - 70);
            int end = webPage.indexOf("</table>", start);

            // Just parse the table we want
            doc = Jsoup.parse(webPage.substring(start, end));

            Elements links = doc.select("a[href]");
            for (Element link : links) {
                versionInfo = new VersionInfo();

                if (link.attr("href").contains("moviejukebox.googlecode.com/files")) {
                    versionInfo.setUrl("http:" + link.attr("href"));
                    Matcher matcher = PATTERN_VERSION.matcher(versionInfo.getUrl());
                    if (matcher.find()) {
                        versionInfo.setVersion(matcher.group(1));
                    }

                    matcher = PATTERN_REVISION.matcher(link.attr("onclick"));
                    if (matcher.find()) {
                        versionInfo.setRevision(Integer.parseInt(matcher.group(1)));
                    }

                    if (versionInfo.isValid()) {
                        return versionInfo;
                    }
                }
            }

        } catch (IOException ex) {
            LOGGER.error("Unable to process download page");
        }

        return new VersionInfo();
    }

    /**
     * Get the version information for the snapshot release from the source page
     * Note: this assumes that the code has been compiled and released
     * @return
     */
    public static VersionInfo parseRevisionInformation() {
        VersionInfo versionInfo;
        Document doc;

        Connection jConn = Jsoup.connect(WEB_SOURCE_PAGE);

        try {
            // Get the whole web page
            doc = jConn.get();

            String webPage = doc.toString();
            int start = webPage.indexOf("resultstable");
            start = webPage.indexOf("<table ", start - 70);
            int end = webPage.indexOf("</table>", start);

            // Just parse the table we want
            doc = Jsoup.parse(webPage.substring(start, end));

            Elements links = doc.select("a[href]");
            for (Element link : links) {
                versionInfo = new VersionInfo();

                if (link.attr("href").contains("detail?r=")) {
                    versionInfo.setRevision(Integer.parseInt(link.attr("href").substring(9)));
                    versionInfo.setUrl("none");
                    versionInfo.setVersion("none");
                    return versionInfo;
                }
            }

        } catch (IOException ex) {
            LOGGER.error("Unable to process source page for revision information");
        }

        return new VersionInfo();
    }

    /**
     * Scan the YAMJ version info file
     * @param yamjPath
     * @return
     */
    public static VersionInfo parseVersionInformation(String yamjPath) {
        File yamjVersionFile = new File(yamjPath + File.separator + "version.txt");
        BufferedReader reader = null;
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setUrl("none");

        try {
            reader = new BufferedReader(new FileReader(yamjVersionFile));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                if (text.startsWith("Version")) {
                    versionInfo.setVersion(text.substring(9));
                } else if (text.startsWith("Revision")) {
                    versionInfo.setRevision(Integer.parseInt(text.substring(11)));
                }
            }
        } catch (FileNotFoundException error) {
            LOGGER.warn("Unable to read YAMJ version info file");
        } catch (IOException error) {
            LOGGER.warn("Unable to read YAMJ version info file");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException error) {
            }
        }

        return versionInfo;
    }
}
