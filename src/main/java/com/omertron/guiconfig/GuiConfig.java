package com.omertron.guiconfig;

import com.omertron.guiconfig.model.Libraries;
import com.omertron.guiconfig.model.Plugins;
import com.omertron.guiconfig.model.VersionInfo;
import com.omertron.guiconfig.tools.PropertiesUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Holds all the processing needed for the GUI Config program
 *
 * @author stuart.boston TODO: Read the devices page for USB drives:
 * http://pch-c200:8008/system?arg0=list_devices TODO: Allow the description to
 * be edited TODO: define where the YAMJ directory is TODO: Check for updates.
 * Read the source wiki page and download page
 */
public class GuiConfig {

    private static final Logger LOGGER = Logger.getLogger(GuiConfig.class);
    private static final String LOG_FILENAME = "GuiConfig.log";

    /*
     * Program definitions
     */
    private static final float GUI_VERSION = 1.0f;
    private static final String TITLE = "YAMJ GUI Config";
    private static final String VERSION_STRING = "v" + GUI_VERSION;
    private static final String GUI_PROPERTY_FILENAME = "guiconfig.properties";

    /*
     * YAMJ Information
     */
    private static String YAMJ_RELEASE_VERSION = "0.0";
    private static String YAMJ_SNAPSHOT_VERSION = "0.0";
    private static VersionInfo releaseInfo = new VersionInfo();
    private static VersionInfo revisionInfo = new VersionInfo();

    /*
     * URL Lists
     */
    private static final String URL_MEDIAPLAYERSITE = "http://MediaPlayerSite.com/YAMJ_GUI_Config";
    // TODO Create a custom Paypal URL (with source of GUI Config)
    protected static final String URL_PAYPAL_DONATE = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=8118392";

    /*
     * Date formats
     */
    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING);

    /*
     * Global variables
     */
    protected static Plugins plugins = new Plugins();
    protected static Libraries libraries = new Libraries();

    public static void main(String[] args) {
        System.setProperty("file.name", LOG_FILENAME);
        PropertyConfigurator.configure("properties/log4j.properties");
    }

    /**
     * Get the library list
     *
     * @return
     */
    public static Libraries getLibraries() {
        return libraries;
    }

    /**
     * Get the list of available plugins
     *
     * @return
     */
    public static Plugins getPlugins() {
        return plugins;
    }

    /**
     * Write a default command file (OS dependant)
     */
    public void writeCmdFile(String cmdFilename, String cmdPath, String library, String videoPath, boolean pauseAtEnd) {
        File file = new File(cmdFilename);

        FileWriter fileWriter = null;
        BufferedWriter bufWriter = null;

        try {
            // Open the file
            fileWriter = new FileWriter(file);
            bufWriter = new BufferedWriter(fileWriter);

            String cmdFile = createWindowsCommand(cmdPath, library, videoPath, pauseAtEnd);

            // Write the text
            bufWriter.write(cmdFile);
        } catch (IOException error) {
            LOGGER.error("Failed to write command file");
        } finally {
            if (bufWriter != null) {
                try {
                    bufWriter.close();
                } catch (IOException error) {
                    // Ignore error
                }
            }

            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException error) {
                    // Ignore error
                }
            }
        }
    }

    /**
     * Create the windows command file text
     *
     * @param cmdPath
     * @param library
     * @param videoPath
     * @return
     */
    private String createWindowsCommand(String cmdPath, String library, String videoPath, boolean pauseAtEnd) {
        StringBuilder cmd = new StringBuilder();
        cmd.append("@Echo OFF\n");
        cmd.append("REM Generated by Omertron's ").append(TITLE).append(" ").append(VERSION_STRING).append("\n");
        cmd.append("REM Created on ").append(DATE_FORMAT.format(new Date()).toString()).append("\n");
        cmd.append("REM ").append(URL_MEDIAPLAYERSITE).append("\n\n");

        // Change drive and path
        cmd.append(cmdPath.substring(0, 2)).append("\n");
        cmd.append("CD ").append(cmdPath.substring(2, cmdPath.length())).append("\n\n");

        // Call the jukebox
        cmd.append("CALL MovieJukebox ").append(library).append(" -o ").append(videoPath).append("\n\n");

        if (pauseAtEnd) {
            cmd.append("pause\n");
        }
        cmd.append("exit\n");

        return cmd.toString();
    }

    /**
     * Write a property file
     */
    public void writePropertyFile() {
    }

    /**
     * Load our properties file
     */
    public void readGuiPropertyFile() {
        boolean retValue = PropertiesUtil.loadPropertyFile(GUI_PROPERTY_FILENAME);
        if (!retValue) {
            // The property file wasn't found, so we should create one
            PropertiesUtil.clearProperties();
            writeGuiPropertyFile();
        }
    }

    /**
     * Save our properties file
     *
     * @param createEmptyFile
     */
    public void writeGuiPropertyFile() {
        PropertiesUtil.savePropertyFile(GUI_PROPERTY_FILENAME, "Gui Config Property File");
    }

    /**
     * Get the program title
     *
     * @return
     */
    public static String getTitle() {
        return TITLE;
    }

    /**
     * Get the program version
     *
     * @return
     */
    public static String getVersion() {
        return VERSION_STRING;
    }

    /**
     * Get the web URL
     *
     * @return
     */
    public static String getUrlMediaPlayerSite() {
        return URL_MEDIAPLAYERSITE;
    }

    /**
     * return the URL for the Paypal donation link
     *
     * @return
     */
    public static String getUrlPaypalDonate() {
        return URL_PAYPAL_DONATE;
    }

    /**
     * Convert a string to a URI
     *
     * @return
     */
    public static URI convertStringToUri() {
        try {
            return (new URI(URL_MEDIAPLAYERSITE));
        } catch (URISyntaxException error) {
            LOGGER.error("GUIConfig unable to convert URL: " + URL_MEDIAPLAYERSITE);
            return null;
        }
    }

    /**
     * Get the latest version information from the Google Code web page - Get
     * the release version (from downloads) - Get the snapshot version (from
     * source)
     *
     * @return
     */
    public static void getYamjVersionInfo() {
        // TODO: Search the Download page
        // TODO: Search the source page

        releaseInfo = YamjVersion.parseReleaseInformation();
        revisionInfo = YamjVersion.parseRevisionInformation();

    }

    public static Logger getLogger() {
        return LOGGER;
    }
}