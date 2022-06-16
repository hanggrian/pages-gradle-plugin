[![Plugin Portal](https://img.shields.io/maven-metadata/v?label=plugin-portal&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fcom%2Fhendraanggrian%2Fpages%2Fcom.hendraanggrian.pages.gradle.plugin%2Fmaven-metadata.xml)](https://plugins.gradle.org/plugin/com.hendraanggrian.pages/)
[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/pages-gradle-plugin)](https://travis-ci.com/github/hendraanggrian/pages-gradle-plugin/)
[![OpenJDK](https://img.shields.io/badge/jdk-1.8+-informational)](https://openjdk.java.net/projects/jdk8/)

# Pages Gradle Plugin

Static webpages generator for GitHub pages.

## Download

Using plugins DSL:

```gradle
plugins {
    id('com.hendraanggrian.pages') version "$version"
}
```

Using legacy plugin application:

```gradle
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.hendraanggrian:pages-gradle-plugin:$version")
    }
}

apply plugin: 'com.hendraanggrian.pages'
```

## Usage

On its own main dependency, the plugin does nothing. Declare a feature by invoking `requireCapability`.

### Feature: [Minimal](https://github.com/hendraanggrian/minimal-theme/)

![Minimal preview](images/preview_minimal.png)

At the moment, this is the only feature.

```gradle
dependencies {
    classpath("com.hendraanggrian:pages-gradle-plugin:$version") {
        capabilities {
            requireCapability('com.hendraanggrian:pages-minimal')
        }
    }
}
```

Configure minimal theme by invoking `minimal` DSL.

```gradle
plugins {
    id('com.hendraanggrian.pages')
}

pages {
    minimal {
        authorName.set('Hendra Anggrian')
        projectName.set('My Project')
        projectDescription.set('A very awesome project')
        markdownFile.set(file('path/to/README.md'))
        headerButtons {
            button('Download', 'Sources', 'https://somewhere.com')
        }
    }
}
```
