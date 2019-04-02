package dev.aura.lib.messagestranslator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TestMessages implements Message {
  // Simple
  TEST1("test1"),
  TEST2("test2"),
  FOO("foo"),
  // Placeholder
  PLACEHOLDER1("placeholder1"),
  PLACEHOLDER2("placeholder2"),
  // Nonexisting
  NONEXISTING("nonexisting");

  @Getter private final String stringPath;
}
