/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.guiconfig;

import com.omertron.guiconfig.model.VersionInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stuart
 */
public class YamjVersionTest {
    
    public YamjVersionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of parseReleaseInformation method, of class YamjVersion.
     */
    @Test
    public void testParseReleaseInformation() {
        System.out.println("parseReleaseInformation");
        VersionInfo result = YamjVersion.parseReleaseInformation();
        System.out.println("Result: " + result.toString()); // XXX DEBUG
        assertTrue(result.isValid());
    }

    /**
     * Test of parseRevisionInformation method, of class YamjVersion.
     */
    @Test
    public void testParseRevisionInformation() {
        System.out.println("parseRevisionInformation");
        VersionInfo result = YamjVersion.parseRevisionInformation();
        System.out.println("Result: " + result.toString()); // XXX DEBUG
        assertTrue(result.isValid());
    }

    /**
     * Test of parseVersionInformation method, of class YamjVersion.
     */
    @Test
    public void testParseVersionInformation() {
        System.out.println("parseVersionInformation");
        String yamjPath = "D:\\YAMJ\\YAMJ";
        VersionInfo result = YamjVersion.parseVersionInformation(yamjPath);
        System.out.println("Result: " + result.toString()); // XXX DEBUG
        assertTrue(result.isValid());
    }
}
