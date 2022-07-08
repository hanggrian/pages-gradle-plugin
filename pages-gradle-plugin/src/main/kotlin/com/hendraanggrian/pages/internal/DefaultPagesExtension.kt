package com.hendraanggrian.pages.internal

import com.hendraanggrian.pages.ContentBuilder
import com.hendraanggrian.pages.DeployPagesSpec
import com.hendraanggrian.pages.PagesExtension
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
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.setProperty
import org.w3c.dom.Document
import java.io.File

open class DefaultPagesExtension(private val project: Project) : PagesExtension, DeployPagesSpec {

    final override val resources: CopySpec = project.copySpec()

    final override val contents: ContentBuilderImpl = ContentBuilderImpl(project)

    final override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()
        .convention(project.layout.buildDirectory.dir("pages"))

    final override val staticResources: SetProperty<String> = project.objects.setProperty<String>()
        .convention(emptySet())

    final override val dynamicResources: MapProperty<Pair<String, String>, String> =
        project.objects.mapProperty<Pair<String, String>, String>().convention(emptyMap())

    final override val webpages: MapProperty<String, Document> = project.objects.mapProperty<String, Document>()
        .convention(emptyMap())

    private val extensions = listOf(TablesExtension.create())
    private val htmlRenderer = HtmlRenderer.builder().extensions(extensions).build()
    private val parser = Parser.builder().extensions(extensions).build()

    final override fun minimal(action: Action<in MinimalPagesOptions>) {
        val options = MinimalPagesOptionsImpl(project.name)
        action.execute(options)
        staticResources.add("minimal/scripts/scale.fix.js")
        staticResources.add("minimal/scripts/theme.js")
        staticResources.add("minimal/styles/pygment_trac.css")
        dynamicResources.put(
            "styles" to "main.css",
            MinimalPages.getMainCss(
                options.accentColor,
                options.accentLightHoverColor,
                options.accentDarkHoverColor,
                options.buttons.size
            )
        )
        contents.forEach { (markdownFile, htmlName) ->
            webpages.put(htmlName, MinimalPages.getPage(options, markdownFile, htmlRenderer, parser))
        }
    }

    class ContentBuilderImpl(private val project: Project) :
        ContentBuilder,
        MutableMap<File, String> by mutableMapOf() {
        override fun add(markdownPath: Any, htmlName: String) {
            put(project.file(markdownPath), htmlName)
        }
    }
}
