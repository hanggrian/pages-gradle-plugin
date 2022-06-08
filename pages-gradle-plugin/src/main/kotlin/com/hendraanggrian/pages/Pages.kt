package com.hendraanggrian.pages

import com.hendraanggrian.pages.minimal.MinimalSpec
import org.gradle.api.Action
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.w3c.dom.Document

/** Extension instance when configuring `pages` in Gradle scripts. */
interface Pages : ResourcesDeployment, PagesDeployment {
    /**
     * Use minimal feature.
     * Must call `capabilities { requireCapability(...) }` in `dependencies { classpath(...) }` Gradle buildscript.
     */
    fun minimal(action: Action<MinimalSpec>)
}

interface ResourcesDeployment {
    /** Resources mapping with filepath as key and content as value. */
    val resourcesMap: MapProperty<String, String>

    /**
     * Output directory of this task.
     * Default is `$projectDir/build/pages`.
     */
    val outputDirectory: DirectoryProperty
}

interface PagesDeployment {
    /** Webpages mapping with filename as key and content as value. */
    val webpagesMap: MapProperty<String, Document>

    /**
     * Output directory of this task.
     * Default is `$projectDir/build/pages`.
     */
    val outputDirectory: DirectoryProperty
}
