plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    id("org.jetbrains.dokka")
    id("com.diffplug.spotless")
    id("com.gradle.plugin-publish")
}

java.registerFeature("minimal") {
    usingSourceSet(sourceSets.main.get())
    capability(RELEASE_GROUP, "pages-minimal", RELEASE_VERSION)
}

gradlePlugin {
    plugins.register("pagesPlugin") {
        id = "$RELEASE_GROUP.pages"
        implementationClass = "$id.PagesPlugin"
        displayName = "Pages plugin"
        description = RELEASE_DESCRIPTION
    }
    testSourceSets(sourceSets.test.get())
}

pluginBundle {
    website = RELEASE_URL
    vcsUrl = "$RELEASE_URL.git"
    description = RELEASE_DESCRIPTION
    tags = listOf("website", "github-pages")
}

val minimalImplementation by configurations.getting

dependencies {
    minimalImplementation(libs.kotlinx.html.jvm)
    minimalImplementation(libs.commonmark.ext.gfm.tables)
    testImplementation(gradleTestKit())
    testImplementation(testLibs.kotlin.junit)
    testImplementation(testLibs.truth)
}
