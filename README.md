[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/pages-gradle-plugin)](https://travis-ci.com/github/hendraanggrian/pages-gradle-plugin/)
[![Plugin Portal](https://img.shields.io/maven-metadata/v.svg?label=plugin-portal&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fcom%2Fhendraanggrian%2Fpages%2Fcom.hendraanggrian.pages.gradle.plugin%2Fmaven-metadata.xml)](https://plugins.gradle.org/plugin/com.hendraanggrian.pages)
[![OpenJDK](https://img.shields.io/badge/jdk-1.8%2B-informational)](https://openjdk.java.net/projects/jdk8/)

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

Configure base extension, then select one of the available themes.

```gradle
pages {
    // custom local resources
    resources {
        from('images')
    }
    // map markdown files to HTML
    contents {
        index('README.md')
        add('About.md', 'about.html')
    }
    // enables prism syntax highlighter
    prism {
        version = '1.28.0'
        theme = 'dark'
        languages = ['java', 'kotlin]
    }
}
```

### [Materialist Theme](https://github.com/hendraanggrian/materialist-theme/)

![Materialist theme preview.](https://github.com/hendraanggrian/materialist-theme/raw/assets/preview_main.png)

```gradle
pages {
    materialist {
        darkMode.set(true)
    }
}
```

### [Cayman Theme](https://github.com/hendraanggrian/cayman-dark-theme/)

![Cayman theme preview.](https://github.com/hendraanggrian/cayman-dark-theme/raw/assets/preview_main.png)

```gradle
pages {
    cayman {
        darkMode.set(true)
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

### [Minimal Theme](https://github.com/hendraanggrian/minimal-dark-theme/)

![Minimal theme preview.](https://github.com/hendraanggrian/minimal-dark-theme/raw/assets/preview_main.png)

```gradle
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
