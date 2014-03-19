package com.omertron.guiconfig.model;

import com.omertron.guiconfig.GuiConfig;
import com.omertron.guiconfig.tools.DOMHelper;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Contains the list of library entries
 * @author stuart.boston
 */
public class Libraries {
    private static final Logger LOGGER = Logger.getLogger(GuiConfig.class);

    private List<LibraryInfo> libraries = new ArrayList<>();

    /**
     * Get the list of library entries
     * @return
     */
    public List<LibraryInfo> getLibrary() {
        return libraries;
    }

    /**
     * Set the list of library entries
     * @param library
     */
    public void setLibrary(List<LibraryInfo> library) {
        this.libraries = library;
    }

    /**
     * Add a single library entry to the list
     * @param library
     */
    public void addLibrary(LibraryInfo library) {
        this.libraries.add(library);
    }

    /**
     * Remove a library from the list
     * @param library
     */
    public void removeLibrary(LibraryInfo library) {
        this.libraries.remove(library);
    }

    /**
     * Clear the library
     */
    public void clear() {
        this.libraries.clear();
    }

    /**
     * Return the size of the libraries
     * @return
     */
    public int count() {
        return libraries.size();
    }

    /**
     * Read the library file and store it in a library object
     * @param libraryFilename
     * @return
     */
    public boolean readLibraryFile(String libraryFilename) {
        File libraryFile = new File(libraryFilename);
        if (!libraryFile.exists()) {
            LOGGER.error("File does not exist! " + libraryFile.getAbsolutePath());
            return false;
        }

        Document libDoc;
        try {
            libDoc = DOMHelper.getEventDocFromUrl(libraryFile);
        } catch (MalformedURLException ex) {
            LOGGER.error("Failed to read " + libraryFilename + ": " + ex.getLocalizedMessage());
            return false;
        } catch (IOException ex) {
            LOGGER.error("Failed to read " + libraryFilename + ": " + ex.getLocalizedMessage());
            return false;
        } catch (ParserConfigurationException | SAXException ex) {
            LOGGER.error("Failed to read " + libraryFilename + ": " + ex.getLocalizedMessage());
            return false;
        }

        NodeList nlLibraries = libDoc.getElementsByTagName(LibraryInfo.LIB_LIBRARY);
        if (nlLibraries.getLength() > 0) {
            for (int looper = 0; looper < nlLibraries.getLength(); looper++) {
                Node nLibrary = nlLibraries.item(looper);
                if (nLibrary.getNodeType() == Node.ELEMENT_NODE) {
                    Element eLibrary = (Element) nLibrary;
                    LibraryInfo library = new LibraryInfo();

                    String value = DOMHelper.getValueFromElement(eLibrary, LibraryInfo.LIB_PATH);
                    if (StringUtils.isNotBlank(value)) {
                        library.setPath(value);
                    }

                    value = DOMHelper.getValueFromElement(eLibrary, LibraryInfo.LIB_PLAYER_PATH);
                    if (StringUtils.isNotBlank(value)) {
                        library.setPlayerPath(value);
                    }

                    NodeList nlExclude = eLibrary.getElementsByTagName(LibraryInfo.LIB_EXCLUDE);
                    if (nlExclude.getLength() > 0) {
                        Element eExclude = (Element) nlExclude.item(0);
                        value = eExclude.getAttribute("name");
                        if (StringUtils.isNotBlank(value)) {
                            library.setExclude(value);
                        }
                    }

                    value = DOMHelper.getValueFromElement(eLibrary, LibraryInfo.LIB_DESCRIPTION);
                    if (StringUtils.isNotBlank(value)) {
                        library.setDescription(value);
                    }

                    value = DOMHelper.getValueFromElement(eLibrary, LibraryInfo.LIB_PREBUF);
                    if (StringUtils.isNotBlank(value)) {
                        library.setPrebuf(value);
                    }

                    value = DOMHelper.getValueFromElement(eLibrary, LibraryInfo.LIB_SCRAPE_LIBRARY);
                    if (StringUtils.isNotBlank(value)) {
                        library.setScrapeLibrary(Boolean.parseBoolean(value));
                    }

                    if (library.isValid()) {
                        addLibrary(library);
                    } else {
                        LOGGER.error("Library is not valid!");
                    }
                }
            }
        } else {
            LOGGER.error("No library entries in the file");
        }

        return true;
    }

    /**
     * Persist a library object to disk
     * @param libraryFilename
     */
    public void writeLibraryFile(String libraryFilename) {
        File libraryFile = new File(libraryFilename);
        Document docLibrary;

        try {
            docLibrary = DOMHelper.createDocument();
        } catch (ParserConfigurationException error) {
            LOGGER.error("Failed to create library file");
            return;
        }

        Element eLibraries = docLibrary.createElement("libraries");
        docLibrary.appendChild(eLibraries);

        // Create the comment lines
        StringBuilder sb = new StringBuilder();
        sb.append("Library file generated by Omertron's ").append(GuiConfig.getTitle()).append(" ").append(GuiConfig.getVersion());
        eLibraries.appendChild(docLibrary.createComment(sb.toString()));
        eLibraries.appendChild(docLibrary.createComment(GuiConfig.getUrlMediaPlayerSite()));
        sb = new StringBuilder("Created on ");
        sb.append(GuiConfig.DATE_FORMAT.format(new Date()).toString());
        eLibraries.appendChild(docLibrary.createComment(sb.toString()));

        // Write the library entries
        for (LibraryInfo libraryInfo : getLibrary()) {
            LOGGER.info("Processing library: " + libraryInfo.getPath());
            eLibraries.appendChild(libraryInfo.getLibraryAsElement(docLibrary));
        }

        // Save the library to file
        DOMHelper.writeDocumentToFile(docLibrary, libraryFile.getAbsolutePath());
    }
}
