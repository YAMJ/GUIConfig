/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.guiconfig.model;

import junit.framework.TestCase;

/**
 * Test for the plugins code
 * @author stuart.boston
 */
public class PluginsTest extends TestCase {

    public PluginsTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of parsePluginWebPage method, of class Plugins.
     */
    public void testParsePluginWebPage() {
        System.out.println("parsePluginWebPage");
        Plugins instance = new Plugins();
        instance.parsePluginWebPage();
        assertTrue("Plugin list is empty", !instance.getPlugins().isEmpty());
    }
}
