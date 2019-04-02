package dev.aura.lib.messagestranslator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: More and better tests
public class PluginMessagesTranslatorTest {
  public static final String ID = "test";

  private static File tempDir;

  @SuppressFBWarnings(
    value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE",
    justification =
        "If deleting fails there is no point in doing anything. Files just get delete to prevent buildup on disk"
  )
  private static void cleanTempDir() {
    if (tempDir.exists()) {
      final File[] files = tempDir.listFiles();

      if (files != null) {
        for (File f : files) {
          f.delete();
        }
      }

      tempDir.delete();
    }
  }

  @BeforeClass
  public static void setUpClass() {
    tempDir = new File(System.getProperty("java.io.tmpdir"), "messagestranslator");
  }

  @AfterClass
  public static void tearDownClass() {
    cleanTempDir();
  }

  @Before
  public void setUp() {
    if (!tempDir.mkdirs())
      throw new IllegalStateException(String.valueOf("Could not create temp folder"));
  }

  @After
  public void tearDown() {
    cleanTempDir();
  }

  @Test
  public void fileCopyTest() {
    new PluginMessagesTranslator(
        tempDir, PluginMessagesTranslator.DEFAULT_LANGUAGE, getClass(), ID);

    File en_US = new File(tempDir, "en_US.lang");
    File de_DE = new File(tempDir, "de_DE.lang");

    assertTrue("Expected en_US.lang to exist", en_US.exists());
    assertTrue("Expected de_DE.lang to exist", de_DE.exists());
  }

  @Test
  public void missingLanguageTest() {
    PluginMessagesTranslator expected =
        new PluginMessagesTranslator(
            tempDir, PluginMessagesTranslator.DEFAULT_LANGUAGE, getClass(), ID);
    PluginMessagesTranslator testee =
        new PluginMessagesTranslator(tempDir, "unknown", getClass(), ID);

    for (Message message : TestMessages.values()) {
      assertEquals(
          "Expected default language and missing language to be the same",
          expected.translateWithFallback(message),
          testee.translateWithFallback(message));
    }
  }

  @Test
  @SuppressWarnings("serial")
  public void placeholderTest() {
    PluginMessagesTranslator en_US =
        new PluginMessagesTranslator(
            tempDir, PluginMessagesTranslator.DEFAULT_LANGUAGE, getClass(), ID);
    PluginMessagesTranslator de_DE = new PluginMessagesTranslator(tempDir, "de_DE", getClass(), ID);

    Map<String, String> set1 =
        new HashMap<String, String>() {
          {
            put("foo", "foo");
            put("bar", "bar");
            put("foobar", "foobar");
            put("placeholder", "placeholder");
          }
        };
    Map<String, String> set2 =
        new HashMap<String, String>() {
          {
            put("foo", "banana");
            put("bar", "banana");
            put("foobar", "banana");
            put("placeholder", "banana");
          }
        };

    assertEquals(
        "We test if foo + bar = foobar",
        en_US.translateWithFallback(TestMessages.PLACEHOLDER1, set1));
    assertEquals(
        "We test if banana + banana = banana",
        en_US.translateWithFallback(TestMessages.PLACEHOLDER1, set2));
    assertEquals("Test placeholder", en_US.translateWithFallback(TestMessages.PLACEHOLDER2, set1));
    assertEquals("Test banana", en_US.translateWithFallback(TestMessages.PLACEHOLDER2, set2));

    assertEquals(
        "Wir testen, ob foo + bar = foobar",
        de_DE.translateWithFallback(TestMessages.PLACEHOLDER1, set1));
    assertEquals(
        "Wir testen, ob banana + banana = banana",
        de_DE.translateWithFallback(TestMessages.PLACEHOLDER1, set2));
    assertEquals("Test placeholder", de_DE.translateWithFallback(TestMessages.PLACEHOLDER2, set1));
    assertEquals("Test banana", de_DE.translateWithFallback(TestMessages.PLACEHOLDER2, set2));
  }
}
