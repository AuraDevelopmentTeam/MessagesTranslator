package dev.aura.lib.messagestranslator;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import dev.aura.lib.messagestranslator.util.FileUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

// TODO: Javadoc!
public class MessagesTranslator {
  public static final String DEFAULT_LANGUAGE = "en_US";
  protected static final ConfigParseOptions PARSE_OPTIONS =
      ConfigParseOptions.defaults().setAllowMissing(false).setSyntax(ConfigSyntax.CONF);
  protected static final String INHERIT = "inherit";

  protected SortedSet<String> loadedLanguages = new TreeSet<>();
  protected Map<String, Pattern> replacementCache = new HashMap<>();

  protected final Config defaultLang;
  protected final Config translation;

  protected static void copyDefaultLanguageFiles(File dir, Class<?> resourceClass, String ID) {
    FileUtils.copyResourcesRecursively(resourceClass.getResource("/assets/" + ID + "/lang"), dir);
  }

  public MessagesTranslator(File dir, String language, Object plugin, String ID) {
    copyDefaultLanguageFiles(dir, plugin.getClass(), ID);

    defaultLang = loadLanguageConfiguration(dir, DEFAULT_LANGUAGE).get();
    translation = loadLanguage(dir, language).withFallback(defaultLang).resolve();
  }

  public Optional<String> translate(Message message) {
    return translate(message, null);
  }

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

  public String translateWithFallback(Message message) {
    return translateWithFallback(message, null);
  }

  public String translateWithFallback(Message message, Map<String, String> replacements) {
    return translate(message, replacements).orElse(message.getStringPath());
  }

  protected Config loadLanguage(File dir, String language) {
    Config langConfig = loadLanguageConfiguration(dir, language).orElse(defaultLang);

    if ((langConfig != defaultLang) && langConfig.hasPath(INHERIT)) {
      String inheritLang = langConfig.getString(INHERIT);

      langConfig = langConfig.withFallback(loadLanguage(dir, inheritLang));
    }

    return langConfig;
  }

  protected Optional<Config> loadLanguageConfiguration(File dir, String language) {
    File langaugeFile = new File(dir, language + ".lang");

    try {
      if (!langaugeFile.exists() || loadedLanguages.contains(language)) {
        return Optional.empty();
      }

      loadedLanguages.add(language);

      return Optional.of(ConfigFactory.parseFile(langaugeFile, PARSE_OPTIONS));
    } catch (Exception e) {
      System.err.println("Could not load language: " + language);
      e.printStackTrace();
    }

    return Optional.empty();
  }

  protected String replacePlaceholder(String orginal, Map.Entry<String, String> replacement) {
    return replacePlaceholder(orginal, replacement.getKey(), replacement.getValue());
  }

  protected String replacePlaceholder(String orginal, String placeholder, String replacement) {
    final Pattern pattern = getPattern(placeholder);

    return pattern.matcher(orginal).replaceAll(replacement);
  }

  protected Pattern getPattern(String placeholder) {
    return replacementCache.computeIfAbsent(placeholder, this::generatePattern);
  }

  protected Pattern generatePattern(String placeholder) {
    return Pattern.compile('%' + Pattern.quote(placeholder) + '%', Pattern.CASE_INSENSITIVE);
  }
}
