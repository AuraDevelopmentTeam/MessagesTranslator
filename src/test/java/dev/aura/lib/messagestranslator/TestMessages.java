package dev.aura.lib.messagestranslator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TestMessages implements Message {
  TEST1("test1"),
  TEST2("test2"),
  FOO("foo");

  @Getter private final String stringPath;
}
