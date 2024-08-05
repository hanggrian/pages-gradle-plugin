[![CircleCI](https://img.shields.io/circleci/build/gh/hanggrian/pages-gradle-plugin)](https://app.circleci.com/pipelines/github/hanggrian/pages-gradle-plugin/)
[![Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/com.hanggrian.pages)](https://plugins.gradle.org/plugin/com.hanggrian.pages)
[![OpenJDK](https://img.shields.io/badge/jdk-11%2B-informational)](https://openjdk.org/projects/jdk/11/)

# Pages Gradle Plugin

Static webpages generator for GitHub pages.

## Download

Using plugins DSL:

```gradle
plugins {
    id('com.hanggrian.pages') version "$version"
}
```

Using legacy plugin application:

```gradle
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.hanggrian:pages-gradle-plugin:$version")
    }
}

apply plugin: 'com.hanggrian.pages'
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
}
```

### [Materialist Theme](https://github.com/hanggrian/materialist-theme/)

![Materialist theme preview.](https://github.com/hanggrian/materialist-theme/raw/assets/preview_main.png)

```gradle
pages {
    materialist {
        darkMode.set(true)
    }
}
```

### [Cayman Theme](https://github.com/hanggrian/cayman-dark-theme/)

![Cayman theme preview.](https://github.com/hanggrian/cayman-dark-theme/raw/assets/preview_main.png)

```gradle
pages {
    cayman {
        darkMode.set(true)
        authorName.set('John Doe')
        projectName.set('My Project')
        projectDescription.set('A very awesome project')
        markdownFile.set(file('path/to/README.md'))
        button('Download', 'Sources', 'https://somewhere.com')
    }
}
```

### [Minimal Theme](https://github.com/hanggrian/minimal-dark-theme/)

![Minimal theme preview.](https://github.com/hanggrian/minimal-dark-theme/raw/assets/preview_main.png)

```gradle
pages {
    minimal {
        authorName.set('John Doe')
        projectName.set('My Project')
        projectDescription.set('A very awesome project')
        markdownFile.set(file('path/to/README.md'))
        button('Download', 'Sources', 'https://somewhere.com')
    }
}
```
