Version 1.4.0
-------------

\+ Added subproject MessagesTranslator-UnitTestHelper to still allow translations in unit tests without copying files.  
\* Completely restructured internal structure:
  - Turned `MessagesTranslator` into an interface.
  - Added `BaseMessagesTranslator` with built in replacement functionalities.
  - Added `HoconMessagesTranslator` to allow HOCON objects to be translation bases.
  - Renamed `MessagesTranslator` to `PluginMessagesTranslator`.
  - Added `UnitTestMessagesTranslator` in subproject MessagesTranslator-UnitTestHelper.
  - Project is now way more extensible.


Version 1.3.0
-------------

\* Changed language file dir from `/lang` to `/assets/<ID>/lang` (Fixes [#1](https://github.com/AuraDevelopmentTeam/MessagesTranslator/issues/1)).  


Version 1.2.0
-------------

\+ Implemented placeholder replacement.  


Version 1.1.1
-------------

\* Prevented infinite loops while loading inherited languages.  


Version 1.1.0
-------------

\* Changed license to MIT.  
\* Using the plugin object to get the `lang` dir.  


Version 1.0.0
-------------

\+ Implemented translation class `MessagesTranslator`.  
\+ Implemented language inheritance.  
\+ Implemented default fallback.  
\+ Added `Message` interface, which is best applied to an `enum`, as translation keys.  


Version 0.0.0
-------------

\* Initial commit  
