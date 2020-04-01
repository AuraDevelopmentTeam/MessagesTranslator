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
  protected static final String ORIGINALS_DIR_NAME = "builtin";

  protected SortedSet<String> loadedLanguages = new TreeSet<>();

  protected final Config defaultLang;

  protected static void copyDefaultLanguageFiles(File dir, Class<?> resourceClass, String ID) {
    // TODO: Consider adding code to migrate the old structure.
    FileUtils.copyResourcesRecursively(
        resourceClass.getResource("/assets/" + ID + "/lang"), new File(dir, ORIGINALS_DIR_NAME));
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
    final String languageFileName = language + ".lang";
    final File originalFilesDir = new File(dir, ORIGINALS_DIR_NAME);

    final File langaugeFileOverride = new File(dir, languageFileName);
    final File langaugeFile = new File(originalFilesDir, languageFileName);

    final boolean langaugeFileOverrideExists = langaugeFileOverride.exists();
    final boolean langaugeFileExists = langaugeFile.exists();

    try {
      if (!(langaugeFileOverrideExists || langaugeFileExists)
          || loadedLanguages.contains(language)) {
        return Optional.empty();
      }

      loadedLanguages.add(language);

      // The code below boils down to:
      // - If the override file exists and the builtin one doesn't -> use the override
      // - If the override file doesn't exist and the builtin one does -> use the builtin
      // - If the override file exists and the builtin one does -> use the override and fall back to
      // the builtin
      Config parsedLanguageOverride =
          langaugeFileOverrideExists
              ? ConfigFactory.parseFile(langaugeFileOverride, DEFAULT_PARSE_OPTIONS)
              : null;
      Config parsedLanguage =
          langaugeFileExists ? ConfigFactory.parseFile(langaugeFile, DEFAULT_PARSE_OPTIONS) : null;

      if (langaugeFileOverrideExists && langaugeFileExists) {
        parsedLanguageOverride = parsedLanguageOverride.withFallback(parsedLanguage);
      }

      return Optional.of(langaugeFileOverrideExists ? parsedLanguageOverride : parsedLanguage);
    } catch (Exception e) {
      System.err.println("Could not load language: " + language);
      e.printStackTrace();
    }

    return Optional.empty();
  }
}
