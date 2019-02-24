package dev.aura.lib.messagestranslator;

import java.util.Map;
import java.util.Optional;

public interface MessagesTranslator {
  public static final String DEFAULT_LANGUAGE = "en_US";

  public default Optional<String> translate(Message message) {
    return translate(message, null);
  }

  public Optional<String> translate(Message message, Map<String, String> replacements);

  public default String translateWithFallback(Message message) {
    return translateWithFallback(message, null);
  }

  public default String translateWithFallback(Message message, Map<String, String> replacements) {
    return translate(message, replacements).orElse(message.getStringPath());
  }
}
