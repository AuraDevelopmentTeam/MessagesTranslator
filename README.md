# [MessagesTranslator](https://github.com/AuraDevelopmentTeam/MessagesTranslator)

[![Build Status](https://gitlab.project-creative.de/AuraDev/MessagesTranslator/badges/master/build.svg)](https://gitlab.project-creative.de/AuraDev/MessagesTranslator/pipelines)
[![Coverage Report](https://gitlab.project-creative.de/AuraDev/MessagesTranslator/badges/master/coverage.svg)](https://gitlab.project-creative.de/AuraDev/MessagesTranslator/pipelines)

Simple plugin to limit how many of a type of block a player can place

## Downloads

You can download all builds from either:

- Maven:
  - Releases: https://maven.project-creative.de/repository/auradev-releases/
  - Snapshots: https://maven.project-creative.de/repository/auradev-snapshots/

## [Issue Reporting](https://github.com/AuraDevelopmentTeam/MessagesTranslator/issues)

If you found a bug or even are experiencing a crash please report it so we can fix it. Please check at first if a bug report for the issue already
[exits](https://github.com/AuraDevelopmentTeam/MessagesTranslator/issues). If not just create a
[new issue](https://github.com/AuraDevelopmentTeam/MessagesTranslator/issues/new) and fill out the form.

Please include the following:

* MessagesTranslator version
* Any relevant screenshots are greatly appreciated.
* For crashes:
  * Steps to reproduce
  * Logs if available

*(When creating a new issue please follow the template)*

## [Feature Requests](https://github.com/AuraDevelopmentTeam/MessagesTranslator/issues)

If you want a new feature added, go ahead an open a [new issue](https://github.com/AuraDevelopmentTeam/InvSync/MessagesTranslator/new), remove the existing form
and describe your feature the best you can. The more details you provide the easier it will be implementing it.  
You can also talk to us on [Discord](https://dicord.me/bungeechat).

## Developing with our Plugin

So you want to add support or even develop an addon for our plugin then you can easily add it to your development environment! All releases get uploaded to my
maven repository. (Replace `{version}` with the appropriate version!)

### Maven

    <repositories>
        <repository>
            <id>AuraDevelopmentTeam/id>
            <url>https://maven.project-creative.de/repository/auradev-releases/</url>
            <!--<url>https://maven.project-creative.de/repository/auradev-snapshots/</url>-->
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>dev.aura.lib.messagestranslator</groupId>
            <artifactId>MessagesTranslator</artifactId>
            <version>{version}</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

### Gradle

    repositories {
        maven {
            name "AuraDevelopmentTeam"
            url "https://maven.project-creative.de/repository/auradev-releases/"
            // url "https://maven.project-creative.de/repository/auradev-snapshots/"
        }
    }

    dependencies {
        provided "dev.aura.lib.messagestranslator:MessagesTranslator:{version}"
    }

## Setting up a Workspace/Compiling from Source

* Clone: Clone the repository like this: `git clone --recursive https://github.com/AuraDevelopmentTeam/MessagesTranslator.git`
* IDE-Setup: Run [gradle] in the repository root: `./gradlew installLombok <eclipse|idea>`
* Build: Run [gradle] in the repository root: `./gradlew build`. The build will be in `build/libs`
* If obscure Gradle issues are found try running `./gradlew cleanCache clean`

## Development builds

Between each offical release there are several bleeding edge development builds, which you can also use. But be aware that they might contain unfinished
features and therefore won't work properly.

You can find the builds here: https://gitlab.project-creative.de/AuraDev/MessagesTranslator/pipelines

On the right is a download symbol, click that a dropdown will open. Select "build". Then you'll download a zip file containing all atrifacts including the
plugin jar.

## Signing

### PGP Signing

All files will be signed with PGP.  
The public key to verify it can be found in `keys/publicKey.asc`. The signatures of the files will also be found in the maven.

### Jar Signing

All jars from all official download sources will be signed. The signature will always have a SHA-1 hash of `2238d4a92d81ab407741a2fdb741cebddfeacba6` and you
are free to verify it.

## License

MessagesTranslator is licensed under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.html)

## Random Quote

> Give a man a program, frustrate him for a day.  
> Teach a man to program, frustrate him for a lifetime.
>
> -- <cite>Waseem Latif</cite>
