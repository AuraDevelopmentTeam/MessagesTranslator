package dev.aura.lib.messagestranslator;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import dev.aura.lib.messagestranslator.util.FileUtils;
import java.io.File;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

// TODO: Javadoc!
public class PluginMessagesTranslator extends HoconMessagesTranslator {
  protected static final String INHERIT = "inherit";

  protected SortedSet<String> loadedLanguages = new TreeSet<>();

  protected final Config defaultLang;

  protected static void copyDefaultLanguageFiles(File dir, Class<?> resourceClass, String ID) {
    FileUtils.copyResourcesRecursively(resourceClass.getResource("/assets/" + ID + "/lang"), dir);
  }

  public PluginMessagesTranslator(File dir, String language, Object plugin, String ID) {
    copyDefaultLanguageFiles(dir, plugin.getClass(), ID);

    defaultLang = loadLanguageConfiguration(dir, DEFAULT_LANGUAGE).get();
    setTranslation(loadLanguage(dir, language).withFallback(defaultLang).resolve());
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

      return Optional.of(ConfigFactory.parseFile(langaugeFile, DEFAULT_PARSE_OPTIONS));
    } catch (Exception e) {
      System.err.println("Could not load language: " + language);
      e.printStackTrace();
    }

    return Optional.empty();
  }
}
