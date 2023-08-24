pluginManagement.repositories {
    mavenCentral()
    gradlePluginPortal()
}
dependencyResolutionManagement.repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

rootProject.name = "pages-gradle-plugin"

include("pages-gradle-plugin")
include("website")
includeDir("samples")

fun includeDir(dir: String) = include(*file(dir).listFiles()!!
    .filter { it.isDirectory }
    .map { "$dir:${it.name}" }
    .toTypedArray())
