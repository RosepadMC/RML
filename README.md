# Rosepad Mod Library

All Rosepad mods must be built on top of this unless you want to write your own mod loader (please don't, I don't want
Lilypad to end up being like modern MC where you must choose which modloader you want to use based on mods you use
rather than modloader itself)

## How to write mods

No idea, wait until I figure out myself. For the time being, look at the [Wiki](https://alphaver.fandom.com/wiki/Rosepad_Modding) and an [example mod](https://github.com/5GameMaker/Garden)

## Building

- Download JDK8 (not 17, 8)
- Clone the repo
- Do `./gradlew -Dorg.gradle.java.home=/path/to/your/jdk8 shadowJar`


