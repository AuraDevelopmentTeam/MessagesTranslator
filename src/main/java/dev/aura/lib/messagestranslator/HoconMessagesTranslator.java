package dev.aura.lib.messagestranslator;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Translates all messages based on a HOCON object.<br>
 * Only child classes may access and manipulate the underlying HOCON object.
 *
 * @author BrainStone
 * @since 1.4.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class HoconMessagesTranslator extends BaseMessagesTranslator {
  public static final ConfigParseOptions DEFAULT_PARSE_OPTIONS =
      ConfigParseOptions.defaults().setAllowMissing(false).setSyntax(ConfigSyntax.CONF);

  private Config translation = ConfigFactory.empty();

  @Override
  public Optional<String> translate(Message message, Map<String, String> replacements) {
    String path = message.getStringPath();

    if (!translation.hasPath(path)) return Optional.empty();

    String result = translation.getString(path);

    if (replacements != null) {
      for (Map.Entry<String, String> replacement : replacements.entrySet()) {
        result = replacePlaceholder(result, replacement);
      }
    }

    return Optional.of(result);
  }
}
