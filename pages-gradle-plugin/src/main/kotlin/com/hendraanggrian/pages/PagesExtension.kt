package com.hendraanggrian.pages

import com.hendraanggrian.pages.cayman.CaymanOptions
import com.hendraanggrian.pages.minimal.MinimalOptions
import org.gradle.api.Action
import org.gradle.api.file.CopySpec
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.invoke
import java.io.File

/** Extension instance when configuring `pages` in Gradle scripts. */
@PagesConfigurationDsl
interface PagesExtension {
    /**
     * Additional resources that will be included in output directory. For example, website favicon
     * and images linked in markdown file. Default is empty.
     */
    val resources: CopySpec

    /** Configures resources. */
    fun resources(action: Action<in CopySpec>): Unit = action(resources)

    /** Webpages to generate from markdown path. Default is project's README as `index.html`. */
    val content: MapProperty<String, File>

    /**
     * Creates webpage file from markdown file. The destination is evaluated as per
     * [org.gradle.api.Project.file].
     */
    fun content(htmlName: String, markdownFile: File): Unit = content.put(htmlName, markdownFile)

    /**
     * Optional relative path to website logo, the icon file should be included in [resources].
     * Default is none.
     */
    val favicon: Property<String>

    /**
     * Additional local or external stylesheets, local styles should be included in [resources]. For
     * example, syntax highlighting styles. Default is empty.
     */
    val styles: ListProperty<String>

    /**
     * Additional local or external javascripts, local scripts should be included in [resources].
     * For example, syntax highlighting scripts. Default is empty.
     */
    val scripts: ListProperty<String>

    /** Output directory of webpages and resources. Default is `$projectDir/build/pages`. */
    val outputDirectory: DirectoryProperty

    /**
     * Enables `minimal` theme and customizes its content with provided [action]. Only one theme may
     * be configured.
     */
    fun minimal(action: Action<in MinimalOptions>)

    /**
     * Enables `minimal` theme and customizes its content with provided [action]. Only one theme may
     * be configured.
     */
    fun cayman(action: Action<in CaymanOptions>)
}
