package dev.aura.lib.messagestranslator;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import dev.aura.lib.messagestranslator.util.FileUtils;
import java.io.File;
import java.util.Optional;

public class MessagesTranslator {
  public static final String DEFAULT_LANGUAGE = "en_US";
  protected static final ConfigParseOptions PARSE_OPTIONS =
      ConfigParseOptions.defaults().setAllowMissing(false).setSyntax(ConfigSyntax.CONF);
  private static final String INHERIT = "inherit";

  private final Config defaultLang;
  private final Config translation;

  private static void copyDefaultLanguageFiles(File dir, Class<?> resourceClass) {
    FileUtils.copyResourcesRecursively(resourceClass.getResource("/lang"), dir);
  }

  public MessagesTranslator(File dir, String language, Object plugin) {
    copyDefaultLanguageFiles(dir, plugin.getClass());

    defaultLang = loadLanguageConfiguration(dir, DEFAULT_LANGUAGE).get();
    translation = loadLanguage(dir, language).withFallback(defaultLang).resolve();
  }

  public Optional<String> translate(Message message) {
    String path = message.getStringPath();

    if (translation.hasPath(path)) return Optional.of(translation.getString(path));
    else return Optional.empty();
  }

  public String translateWithFallback(Message message) {
    return translate(message).orElse(message.getStringPath());
  }

  private Config loadLanguage(File dir, String language) {
    Config langConfig = loadLanguageConfiguration(dir, language).orElse(defaultLang);

    if (langConfig.hasPath(INHERIT)) {
      String inheritLang = langConfig.getString(INHERIT);

      langConfig = langConfig.withFallback(loadLanguage(dir, inheritLang));
    }

    return langConfig;
  }

  private Optional<Config> loadLanguageConfiguration(File dir, String language) {
    File langaugeFile = new File(dir, language + ".lang");

    try {
      if (langaugeFile.exists())
        return Optional.of(ConfigFactory.parseFile(langaugeFile, PARSE_OPTIONS));
    } catch (Exception e) {
      System.err.println("Could not load language: " + language);
      e.printStackTrace();
    }

    return Optional.empty();
  }
}
