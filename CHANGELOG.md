Version 1.5.1
-------------

\+ Added README files that help understand how the translation files work.  


Version 1.5.0
-------------

\* Builtin original translations are now stored inside the `builtin` dir. The language files in the base dir are considered override files.  
\* Languages are now loaded by this logic:  
   - If the override file exists and the builtin one doesn't -> use the override
   - If the override file doesn't exist and the builtin one does -> use the builtin
   - If the override file exists and the builtin one does -> use the override and fall back to the builtin


Version 1.4.2
-------------

\* Fixed URLs in docs.  


Version 1.4.1
-------------

\* Fixed deploying to maven of MessagesTranslator-UnitTestHelper.  


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
