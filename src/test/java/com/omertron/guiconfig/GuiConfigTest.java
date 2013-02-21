package com.omertron.guiconfig;

import org.apache.log4j.Logger;
import com.omertron.guiconfig.tools.PropertiesUtil;
import java.io.File;
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
public class GuiConfigTest {

    private static final Logger LOGGER = GuiConfig.getLogger();

    private static final String TEST_DIR = "./src/test/java/TestFiles/";
    private static final String READ_LIBRARY = TEST_DIR + "TestLibrary.xml";
    private static final String WRITE_LIBRARY = TEST_DIR + "TestLibrary_OUTPUT.xml";
    private static final String CMD_FILENAME = TEST_DIR + "My_YAMJ.cmd";

    public GuiConfigTest() {
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
     * Test of readLibraryFile method, of class GuiConfig.
     */
    @Test
    public void testReadLibraryFile() {
        System.out.println("readLibraryFile");

        boolean response = GuiConfig.libraries.readLibraryFile(READ_LIBRARY);
        assertTrue("Problem reading the library file", response);

        assertEquals(3, GuiConfig.libraries.getLibrary().size());
    }

    /**
     * Test of writeLibraryFile method, of class GuiConfig.
     */
    @Test
    public void testWriteLibraryFile() {
        System.out.println("writeLibraryFile");
        File file = new File(WRITE_LIBRARY);

        if (file.exists()) {
            file.delete();
        }

        if (GuiConfig.libraries.getLibrary().isEmpty()) {
            GuiConfig.libraries.readLibraryFile(READ_LIBRARY);
        }

        GuiConfig.libraries.writeLibraryFile(WRITE_LIBRARY);

        assertTrue(file.exists());
    }

    /**
     * Test of writeCmdFile method, of class GuiConfig.
     */
    @Test
    public void testWriteCmdFile() {
        System.out.println("writeCmdFile");
        GuiConfig instance = new GuiConfig();
        File file = new File(CMD_FILENAME);

        if (file.exists()) {
            file.delete();
        }

        instance.writeCmdFile(CMD_FILENAME, "C:\\YAMJ", "My_Library.xml", "D:\\VideoPath", true);

        assertTrue(file.exists());
    }

    /**
     * Test of writePropertyFile method, of class GuiConfig.
     */
//    @Test
    public void testWritePropertyFile() {
        System.out.println("writePropertyFile");
        GuiConfig instance = new GuiConfig();
        instance.writePropertyFile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readGuiPropertyFile method, of class GuiConfig.
     */
//    @Test
    public void testGetGuiPropertyFile() {
        System.out.println("getGuiPropertyFile");
        GuiConfig instance = new GuiConfig();
        instance.readGuiPropertyFile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeGuiPropertyFile method, of class GuiConfig.
     */
    @Test
    public void testWriteGuiPropertyFile() {
        System.out.println("writeGuiPropertyFile");
        GuiConfig instance = new GuiConfig();
        PropertiesUtil.setProperty("TestProperty", "True");
        instance.writeGuiPropertyFile();
    }

}
