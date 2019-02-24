package dev.aura.lib.messagestranslator;

import java.util.Map;
import java.util.Optional;

/**
 * This class always returns an empty translation.<br>
 * Potentially useful as a fallback.
 *
 * @author BrainStone
 * @since 1.4.0
 */
public class DummyMessagesTranslator implements MessagesTranslator {
  @Override
  public Optional<String> translate(Message message, Map<String, String> replacements) {
    return Optional.empty();
  }
}
