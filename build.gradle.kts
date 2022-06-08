buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(dokka)
        classpath(pages) { capabilities { requireCapability("com.hendraanggrian.pages:minimal:$VERSION_PAGES") } }
        classpath(`git-publish`)
        classpath(`gradle-publish`)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}