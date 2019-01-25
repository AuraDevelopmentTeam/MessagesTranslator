package dev.aura.lib.messagestranslator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TestMessages implements Message {
  TEST("");

  @Getter private final String stringPath;
}
