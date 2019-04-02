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
  @SuppressWarnings("serial")
  public void missingLanguageTest() {
    PluginMessagesTranslator expected =
        new PluginMessagesTranslator(
            tempDir, PluginMessagesTranslator.DEFAULT_LANGUAGE, getClass(), ID);
    PluginMessagesTranslator testee =
        new PluginMessagesTranslator(tempDir, "unknown", getClass(), ID);

    final Map<String, String> dummySet =
        new HashMap<String, String>() {
          {
            put("dummy", "");
          }
        };
    final Map<String, String> set1 =
        new HashMap<String, String>() {
          {
            put("foo", "foo");
            put("bar", "bar");
            put("foobar", "foobar");
            put("placeholder", "placeholder");
          }
        };

    for (Message message : TestMessages.values()) {
      assertEquals(
          "Expected default language and missing language to be the same",
          expected.translateWithFallback(message),
          testee.translateWithFallback(message));
      assertEquals(
          "Expected default language and missing language to be the same",
          expected.translateWithFallback(message, dummySet),
          testee.translateWithFallback(message, dummySet));
      assertEquals(
          "Expected default language and missing language to be the same",
          expected.translateWithFallback(message, set1),
          testee.translateWithFallback(message, set1));
    }
  }

  @Test
  @SuppressWarnings("serial")
  public void placeholderTest() {
    PluginMessagesTranslator en_US =
        new PluginMessagesTranslator(
            tempDir, PluginMessagesTranslator.DEFAULT_LANGUAGE, getClass(), ID);
    PluginMessagesTranslator de_DE = new PluginMessagesTranslator(tempDir, "de_DE", getClass(), ID);

    final Map<String, String> emptySet = new HashMap<>();
    final Map<String, String> dummySet =
        new HashMap<String, String>() {
          {
            put("dummy", "");
          }
        };
    final Map<String, String> set1 =
        new HashMap<String, String>() {
          {
            put("foo", "foo");
            put("bar", "bar");
            put("foobar", "foobar");
            put("placeholder", "placeholder");
          }
        };
    final Map<String, String> set2 =
        new HashMap<String, String>() {
          {
            put("foo", "banana");
            put("bar", "banana");
            put("foobar", "banana");
            put("placeholder", "banana");
          }
        };

    assertEquals(
        "We test if %foo% + %bar% = %foobar% (%foo%%bar%)",
        en_US.translateWithFallback(TestMessages.PLACEHOLDER1));
    assertEquals(
        "We test if %foo% + %bar% = %foobar% (%foo%%bar%)",
        en_US.translateWithFallback(TestMessages.PLACEHOLDER1, emptySet));
    assertEquals(
        "We test if %foo% + %bar% = %foobar% (%foo%%bar%)",
        en_US.translateWithFallback(TestMessages.PLACEHOLDER1, dummySet));
    assertEquals(
        "We test if foo + bar = foobar (foobar)",
        en_US.translateWithFallback(TestMessages.PLACEHOLDER1, set1));
    assertEquals(
        "We test if banana + banana = banana (bananabanana)",
        en_US.translateWithFallback(TestMessages.PLACEHOLDER1, set2));
    assertEquals("Test %placeholder%", en_US.translateWithFallback(TestMessages.PLACEHOLDER2));
    assertEquals(
        "Test %placeholder%", en_US.translateWithFallback(TestMessages.PLACEHOLDER2, emptySet));
    assertEquals(
        "Test %placeholder%", en_US.translateWithFallback(TestMessages.PLACEHOLDER2, dummySet));
    assertEquals("Test placeholder", en_US.translateWithFallback(TestMessages.PLACEHOLDER2, set1));
    assertEquals("Test banana", en_US.translateWithFallback(TestMessages.PLACEHOLDER2, set2));

    assertEquals(
        "Wir testen, ob %foo% + %bar% = %foobar% (%foo%%bar%)",
        de_DE.translateWithFallback(TestMessages.PLACEHOLDER1));
    assertEquals(
        "Wir testen, ob %foo% + %bar% = %foobar% (%foo%%bar%)",
        de_DE.translateWithFallback(TestMessages.PLACEHOLDER1, emptySet));
    assertEquals(
        "Wir testen, ob %foo% + %bar% = %foobar% (%foo%%bar%)",
        de_DE.translateWithFallback(TestMessages.PLACEHOLDER1, dummySet));
    assertEquals(
        "Wir testen, ob foo + bar = foobar (foobar)",
        de_DE.translateWithFallback(TestMessages.PLACEHOLDER1, set1));
    assertEquals(
        "Wir testen, ob banana + banana = banana (bananabanana)",
        de_DE.translateWithFallback(TestMessages.PLACEHOLDER1, set2));
    assertEquals("Test %placeholder%", de_DE.translateWithFallback(TestMessages.PLACEHOLDER2));
    assertEquals(
        "Test %placeholder%", de_DE.translateWithFallback(TestMessages.PLACEHOLDER2, emptySet));
    assertEquals(
        "Test %placeholder%", de_DE.translateWithFallback(TestMessages.PLACEHOLDER2, dummySet));
    assertEquals("Test placeholder", de_DE.translateWithFallback(TestMessages.PLACEHOLDER2, set1));
    assertEquals("Test banana", de_DE.translateWithFallback(TestMessages.PLACEHOLDER2, set2));
  }
}
