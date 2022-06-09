group = RELEASE_GROUP
version = RELEASE_VERSION

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    dokka
    spotless
    `gradle-publish`
}

java {
    registerFeature("minimal") {
        usingSourceSet(sourceSets.main.get())
        capability(RELEASE_GROUP, "pages-minimal", RELEASE_VERSION)
    }
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
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    minimalImplementation(kotlin("stdlib", VERSION_KOTLIN))
    minimalImplementation(kotlinx("html-jvm", VERSION_HTML))
    minimalImplementation(commonmark)
    testImplementation(gradleTestKit())
    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
    testImplementation(google("truth", version = VERSION_TRUTH))
}