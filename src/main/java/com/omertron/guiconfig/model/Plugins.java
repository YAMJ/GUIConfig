package com.omertron.guiconfig.model;

import com.omertron.guiconfig.GuiConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Contains details about the list of plugins that are available
 * @author stuart.boston
 */
public class Plugins {
    private static final String PLUGIN_WEB_PAGE = "http://code.google.com/p/moviejukebox/wiki/Plugins";

    private List<PluginInfo> plugins = new ArrayList<>();

    /**
     * Get the list of plugins
     * @return
     */
    public List<PluginInfo> getPlugins() {
        return plugins;
    }

    /**
     * Set the list of plugins
     * @param plugins
     */
    public void setPlugins(List<PluginInfo> plugins) {
        this.plugins = plugins;
    }

    /**
     * Add a plugin to the list
     * @param pluginInfo
     */
    public void addPlugin(PluginInfo pluginInfo) {
        this.plugins.add(pluginInfo);
    }

    /**
     * Remove a single plugin from the list
     * @param pluginInfo
     */
    public void removePlugin(PluginInfo pluginInfo) {
        this.plugins.remove(pluginInfo);
    }

    /**
     * Clear the plugin list
     */
    public void clear() {
        this.plugins.clear();
    }

    /**
     * Parse the plugin wiki page for the details about the plugins
     */
    public void parsePluginWebPage() {
        Document doc;

        Connection jConn = Jsoup.connect(PLUGIN_WEB_PAGE);
        try {
            // Get the whole web page
            doc = jConn.get();
            String webPage = doc.toString();
            int start = webPage.indexOf("<div id=\"wikicontent\">");
            start = webPage.indexOf("<table ", start);
            int end = webPage.indexOf("</table>", start);

            // Just parse the table we want
            doc = Jsoup.parse(webPage.substring(start, end));

            // Clear the plugin list
            this.clear();

            for (Element eLine : doc.getElementsByTag("tr")) {
                /*
                 * The order of the elements is:
                 * 0:Type
                 * 1:Site
                 * 2:Language
                 * 3:Property
                 * 4:URL
                 * 5:MovieDb
                 */

                PluginInfo pluginInfo;
                String type = eLine.child(0).text();
                if (type.equalsIgnoreCase(PluginInfo.PluginType.MOVIE.toString())) {
                    pluginInfo = new PluginInfo(PluginInfo.PluginType.MOVIE);
                } else if (type.equalsIgnoreCase(PluginInfo.PluginType.TV.toString())) {
                    pluginInfo = new PluginInfo(PluginInfo.PluginType.TV);
                } else if (type.equalsIgnoreCase(PluginInfo.PluginType.PERSON.toString())) {
                    pluginInfo = new PluginInfo(PluginInfo.PluginType.PERSON);
                } else {
                    // Unknown plugin type (Might also be the first line)
                    continue;
                }

                pluginInfo.setSite(eLine.child(1).text());
                pluginInfo.setLanguage(eLine.child(2).text());
                pluginInfo.setProperty(eLine.child(3).text());
                pluginInfo.setUrl(eLine.child(4).text());
                pluginInfo.setMoviedb(eLine.child(5).text());

                addPlugin(pluginInfo);
            }
        } catch (IOException ex) {
            Logger.getLogger(GuiConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
