package dev.aura.lib.messagestranslator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This abstract base class implements the {@link MessagesTranslator} (whithout implementing the
 * abstract method of) to be able to provide common replacement capabilities to its child classes.
 * <br>
 * The methods and cache are static to increase performance by making sure there's only ever one
 * cache active at a time.
 *
 * @author BrainStone
 * @since 1.4.0
 */
public abstract class BaseMessagesTranslator implements MessagesTranslator {
  private static final Map<String, Pattern> replacementCache = new HashMap<>();

  protected static final String replacePlaceholder(
      String orginal, Map.Entry<String, String> replacement) {
    return replacePlaceholder(orginal, replacement.getKey(), replacement.getValue());
  }

  protected static final String replacePlaceholder(
      String orginal, String placeholder, String replacement) {
    final Pattern pattern = getPattern(placeholder);

    return pattern.matcher(orginal).replaceAll(replacement);
  }

  private static final Pattern getPattern(String placeholder) {
    return replacementCache.computeIfAbsent(placeholder, BaseMessagesTranslator::generatePattern);
  }

  private static final Pattern generatePattern(String placeholder) {
    return Pattern.compile('%' + Pattern.quote(placeholder) + '%', Pattern.CASE_INSENSITIVE);
  }
}
