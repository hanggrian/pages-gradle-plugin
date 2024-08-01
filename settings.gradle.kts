pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "pages-gradle-plugin"

include("pages-gradle-plugin")
include("website")
includeDir("samples")

fun includeDir(dir: String) =
    include(*file(dir).listFiles()!!
        .filter { it.isDirectory }
        .map { "$dir:${it.name}" }
        .toTypedArray())
