include("pages-gradle-plugin")
include("website")
includeDir("samples")

fun includeDir(dir: String) = file(dir)
    .listFiles()
    .filter { it.isDirectory }
    .forEach { include("$dir:${it.name}") }

dependencyResolutionManagement {
    versionCatalogs {
        val kotlinVersion = "1.6.21"
        register("sdk") {
            version("jdk", "8")
        }
        register("plugs") {
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").version(kotlinVersion)
            plugin("kotlin-kapt", "org.jetbrains.kotlin.kapt").version(kotlinVersion)
            plugin("dokka", "org.jetbrains.dokka").version(kotlinVersion)
            plugin("spotless", "com.diffplug.spotless").version("6.7.2")
            plugin("gradle-publish", "com.gradle.plugin-publish").version("1.0.0-rc-3")
            plugin("git-publish", "org.ajoberstar.git-publish").version("3.0.1")
            library("pages", "com.hendraanggrian:pages-gradle-plugin:0.1")
        }
        register("libs") {
            library("kotlinx-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
            library("kotlinx-html-jvm", "org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.5")
            library("commonmark-ext-gfm-tables", "org.commonmark:commonmark-ext-gfm-tables:0.18.2")
        }
        register("testLibs") {
            library("kotlin-junit", "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
            library("truth", "com.google.truth:truth:1.1.3")
        }
    }
}
