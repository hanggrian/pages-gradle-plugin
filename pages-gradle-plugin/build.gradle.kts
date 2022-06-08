group = RELEASE_GROUP
version = RELEASE_VERSION

plugins {
    `kotlin-dsl`
    dokka
    `gradle-publish`
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))
    }
}

java {
    registerFeature("minimal") {
        usingSourceSet(sourceSets.main.get())
        capability("$RELEASE_GROUP.pages", "minimal", RELEASE_VERSION)
    }
}

gradlePlugin {
    plugins.register("pagesPlugin") {
        id = "$RELEASE_GROUP.pages"
        implementationClass = "$RELEASE_GROUP.pages.PagesPlugin"
        displayName = "Pages plugin"
        description = RELEASE_DESCRIPTION
    }
    testSourceSets(sourceSets.test.get())
}

pluginBundle {
    website = RELEASE_GITHUB
    vcsUrl = "$RELEASE_GITHUB.git"
    description = RELEASE_DESCRIPTION
    tags = listOf("website", "github-pages")
}

dependencies {
    "minimalImplementation"(kotlin("stdlib", VERSION_KOTLIN))
    "minimalImplementation"(kotlinx("html-jvm", VERSION_HTML))
    "minimalImplementation"(commonmark)
    testImplementation(gradleTestKit())
    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
}

tasks {
    dokkaHtml {
        outputDirectory.set(buildDir.resolve("dokka/dokka"))
    }
}

ktlint()