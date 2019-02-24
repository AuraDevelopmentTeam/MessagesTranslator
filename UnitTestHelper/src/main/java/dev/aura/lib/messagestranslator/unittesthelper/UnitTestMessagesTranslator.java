package dev.aura.lib.messagestranslator.unittesthelper;

import com.typesafe.config.ConfigFactory;
import dev.aura.lib.messagestranslator.HoconMessagesTranslator;

public class UnitTestMessagesTranslator extends HoconMessagesTranslator {
  public UnitTestMessagesTranslator(String ID) {
    this(UnitTestMessagesTranslator.class, ID);
  }

  public UnitTestMessagesTranslator(Class<?> resourceClass, String ID) {
    super(
        ConfigFactory.parseURL(
            resourceClass.getResource("/assets/" + ID + "/lang/" + DEFAULT_LANGUAGE + ".lang"),
            DEFAULT_PARSE_OPTIONS));
  }
}
