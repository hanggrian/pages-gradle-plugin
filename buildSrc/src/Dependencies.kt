internal typealias Dependencies = org.gradle.api.artifacts.dsl.DependencyHandler

val Dependencies.commonmark get() = "org.commonmark:commonmark-ext-gfm-tables:0.18.2"

const val VERSION_TRUTH = "1.1.3"
fun Dependencies.google(module: String, version: String) = "com.google.$module:$module:$version"