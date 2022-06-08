internal typealias Plugins = org.gradle.plugin.use.PluginDependenciesSpec

const val VERSION_KOTLIN = "1.6.21"
const val VERSION_COROUTINES = "1.6.2"
const val VERSION_HTML = "0.7.5"
val Dependencies.dokka get() = "org.jetbrains.dokka:dokka-gradle-plugin:$VERSION_KOTLIN"
val Plugins.dokka get() = id("org.jetbrains.dokka")
fun Dependencies.kotlinx(module: String, version: String? = null) =
    "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" }.orEmpty()}"

const val VERSION_PAGES = "0.1"
val Dependencies.pages get() = "com.hendraanggrian:pages-gradle-plugin:$VERSION_PAGES"
val Plugins.pages get() = id("com.hendraanggrian.pages")

val Dependencies.`git-publish` get() = "org.ajoberstar.git-publish:gradle-git-publish:3.0.1"
val Plugins.`git-publish` get() = id("org.ajoberstar.git-publish")

val Dependencies.`gradle-publish` get() = "com.gradle.publish:plugin-publish-plugin:1.0.0-rc-2"
val Plugins.`gradle-publish` get() = id("com.gradle.plugin-publish")