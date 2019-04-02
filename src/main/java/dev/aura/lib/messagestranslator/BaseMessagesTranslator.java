package dev.aura.lib.messagestranslator;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * This abstract base class implements the {@link MessagesTranslator} to be able to provide common
 * replacement capabilities to its child classes. <br>
 * The methods are implemented to replace placeholders in the string as fast as possible. Subclasses
 * only need to provide the base {@link String}.
 *
 * @author BrainStone
 * @since 1.4.0
 */
public abstract class BaseMessagesTranslator implements MessagesTranslator {
  private static final Pattern SPLITTER = Pattern.compile("%");

  @Override
  public Optional<String> translate(Message message, Map<String, String> replacements) {
    final Optional<String> rawTranslation = translateRaw(message);

    if (replacements == null) return rawTranslation;
    if (!rawTranslation.isPresent()) return Optional.empty();

    CharSequence[] splits = SPLITTER.split(rawTranslation.get());
    int length = 0;

    for (int i = 0; i < splits.length; ++i) {
      if ((i & 0x01) == 1) {
        final String lookup = replacements.get(splits[i]);

        if (lookup == null) {
          splits[i] =
              new StringBuilder(splits[i].length() + 2).append('%').append(splits[i]).append('%');
        } else {
          splits[i] = lookup;
        }
      }

      length += splits[i].length();
    }

    final StringBuilder result = new StringBuilder(length);

    for (final CharSequence split : splits) {
      result.append(split);
    }

    return Optional.of(result.toString());
  }

  protected abstract Optional<String> translateRaw(Message message);
}
