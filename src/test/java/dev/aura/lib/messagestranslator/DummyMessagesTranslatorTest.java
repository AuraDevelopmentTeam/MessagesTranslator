package dev.aura.lib.messagestranslator;

import static org.junit.Assert.*;

import java.util.Optional;
import org.junit.Test;

public class DummyMessagesTranslatorTest {
  @Test
  public void dummyMessagesTranslatorTest() {
    DummyMessagesTranslator translator = new DummyMessagesTranslator();

    for (Message message : TestMessages.values()) {
      assertEquals("Expected result to be empty", Optional.empty(), translator.translate(message));
    }
  }
}
