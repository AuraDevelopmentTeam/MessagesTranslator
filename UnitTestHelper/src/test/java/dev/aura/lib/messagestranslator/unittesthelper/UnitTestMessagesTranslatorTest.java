package dev.aura.lib.messagestranslator.unittesthelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import dev.aura.lib.messagestranslator.Message;
import org.junit.Test;

public class UnitTestMessagesTranslatorTest {
  public static final String ID = "test";

  @Test
  public void translationTest() {
    UnitTestMessagesTranslator translatorSelf = new UnitTestMessagesTranslator(ID);
    UnitTestMessagesTranslator translatorThis = new UnitTestMessagesTranslator(getClass(), ID);

    for (Message message : TestMessages.values()) {
      assertNotEquals(
          "Expected string path and translated message not to be the same",
          message.getStringPath(),
          translatorSelf.translateWithFallback(message));
      assertEquals(
          "Expected translated message to be the same for both translators",
          translatorSelf.translateWithFallback(message),
          translatorThis.translateWithFallback(message));
    }
  }
}
