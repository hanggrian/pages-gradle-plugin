package com.hendraanggrian.pages.internal

import com.hendraanggrian.pages.ContentBuilder
import com.hendraanggrian.pages.ContentBuilderImpl
import com.hendraanggrian.pages.DeployPagesSpec
import com.hendraanggrian.pages.PagesExtension
import com.hendraanggrian.pages.cayman.CaymanPages
import com.hendraanggrian.pages.cayman.CaymanPagesOptions
import com.hendraanggrian.pages.cayman.CaymanPagesOptionsImpl
import com.hendraanggrian.pages.minimal.MinimalPages
import com.hendraanggrian.pages.minimal.MinimalPagesOptions
import com.hendraanggrian.pages.minimal.MinimalPagesOptionsImpl
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.setProperty
import org.w3c.dom.Document
import java.io.File

open class DefaultPagesExtension(private val project: Project) : PagesExtension, DeployPagesSpec {

    final override val resources: CopySpec = project.copySpec()

    final override val contents: ContentBuilder = ContentBuilderImpl(project)

    final override var favicon: Property<String> = project.objects.property()

    final override val styles: ListProperty<String> = project.objects.listProperty<String>()
        .convention(emptyList())

    final override val scripts: ListProperty<String> = project.objects.listProperty<String>()
        .convention(emptyList())

    final override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()
        .convention(project.layout.buildDirectory.dir("pages"))

    final override val staticResources: SetProperty<String> = project.objects.setProperty<String>()
        .convention(emptySet())

    final override val dynamicResources: MapProperty<Pair<String, String>, String> = project.objects
        .mapProperty<Pair<String, String>, String>().convention(emptyMap())

    final override val webpages: MapProperty<String, Document> = project.objects.mapProperty<String, Document>()
        .convention(emptyMap())

    val fencedCodeBlockIndent: Property<Int> = project.objects.property<Int>()
        .convention(0)
    private val extensions = listOf(TablesExtension.create())
    private val htmlRenderer = HtmlRenderer.builder().extensions(extensions).build()
    private val parser = Parser.builder().extensions(extensions).build()

    final override fun minimal(action: Action<in MinimalPagesOptions>) {
        checkTheme()
        val options = MinimalPagesOptionsImpl(project.name).also { action(it) }
        val pages = MinimalPages(options)
        staticResources.add("minimal/scripts/scale.fix.js")
        staticResources.add("minimal/scripts/theme.js")
        dynamicResources.put("styles" to "main.css", pages.mainCss)
        (contents as ContentBuilderImpl).contents.forEach { (htmlName, markdownFile) ->
            webpages.put(htmlName, pages.getPage(favicon.orNull, styles.orNull, scripts.orNull, markdownFile.readRaw()))
        }
        fencedCodeBlockIndent.set(4)
    }

    final override fun cayman(action: Action<in CaymanPagesOptions>) {
        checkTheme()
        val options = CaymanPagesOptionsImpl(project.name).also { action(it) }
        val pages = CaymanPages(options)
        if (options.dark) staticResources.add("cayman/styles/dark.css")
        staticResources.add("cayman/styles/normalize.css")
        dynamicResources.put("styles" to "main.css", pages.mainCss)
        (contents as ContentBuilderImpl).contents.forEach { (htmlName, markdownFile) ->
            webpages.put(
                htmlName,
                pages.getPage(options.dark, favicon.orNull, styles.orNull, scripts.orNull, markdownFile.readRaw())
            )
        }
        fencedCodeBlockIndent.set(3)
    }

    private fun checkTheme() =
        check(staticResources.get().isEmpty() && dynamicResources.get().isEmpty() && webpages.get().isEmpty()) {
            "Only one theme can be selected"
        }

    private fun File.readRaw() = htmlRenderer.render(parser.parse(readText()))
}
