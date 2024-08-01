package com.hanggrian.pages.internal

import com.hanggrian.pages.DeployPageSpec
import com.hanggrian.pages.PagesExtension
import com.hanggrian.pages.cayman.CaymanOptions
import com.hanggrian.pages.cayman.CaymanOptionsImpl
import com.hanggrian.pages.cayman.CaymanThemeFactory
import com.hanggrian.pages.materialist.MaterialistOptions
import com.hanggrian.pages.materialist.MaterialistOptionsImpl
import com.hanggrian.pages.materialist.MaterialistThemeFactory
import com.hanggrian.pages.minimal.MinimalOptions
import com.hanggrian.pages.minimal.MinimalOptionsImpl
import com.hanggrian.pages.minimal.MinimalThemeFactory
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

public open class DefaultPageExtension(private val project: Project) :
    PagesExtension,
    DeployPageSpec {
    final override val resources: CopySpec = project.copySpec()

    final override val content: MapProperty<String, File> =
        project.objects
            .mapProperty<String, File>()
            .convention(emptyMap())

    final override var favicon: Property<String> =
        project.objects
            .property()

    final override val styles: ListProperty<String> =
        project.objects
            .listProperty<String>()
            .convention(emptyList())

    final override val scripts: ListProperty<String> =
        project.objects
            .listProperty<String>()
            .convention(emptyList())

    final override val outputDirectory: DirectoryProperty =
        project.objects
            .directoryProperty()
            .convention(project.layout.buildDirectory.dir("pages"))

    final override val staticResources: SetProperty<String> =
        project.objects
            .setProperty<String>()
            .convention(emptySet())

    final override val dynamicResources: MapProperty<String, String> =
        project.objects
            .mapProperty<String, String>()
            .convention(emptyMap())

    final override val webpages: MapProperty<String, Document> =
        project.objects
            .mapProperty<String, Document>()
            .convention(emptyMap())

    public val fencedCodeBlockIndent: Property<Int> =
        project.objects
            .property<Int>()
            .convention(0)

    private val extensions = listOf(TablesExtension.create())
    private val htmlRenderer = HtmlRenderer.builder().extensions(extensions).build()
    private val parser = Parser.builder().extensions(extensions).build()

    final override fun minimal(action: Action<in MinimalOptions>) {
        checkTheme()
        scanReadme()
        val options = MinimalOptionsImpl(project.name).also { action(it) }
        val pages = MinimalThemeFactory(this, options)

        staticResources.add("minimal/scripts/scale.fix.js")
        staticResources.add("minimal/scripts/theme.js")
        dynamicResources.put("styles/main.css", pages.mainCss)
        content.get().forEach { (htmlName, markdownFile) ->
            webpages.put(htmlName, pages.getDocument(markdownFile.readRaw()))
        }
        fencedCodeBlockIndent.set(4)
    }

    final override fun cayman(action: Action<in CaymanOptions>) {
        checkTheme()
        scanReadme()
        val options = CaymanOptionsImpl(project.name).also { action(it) }
        val pages = CaymanThemeFactory(this, options)

        if (options.darkMode) {
            staticResources.add("cayman/styles/dark.css")
        }
        staticResources.add("cayman/styles/normalize.css")
        dynamicResources.put("styles/main.css", pages.mainCss)
        content.get().forEach { (htmlName, markdownFile) ->
            webpages.put(htmlName, pages.getDocument(markdownFile.readRaw()))
        }
        fencedCodeBlockIndent.set(3)
    }

    final override fun materialist(action: Action<in MaterialistOptions>) {
        checkTheme()
        scanReadme()
        val options = MaterialistOptionsImpl(project.name).also { action(it) }
        val pages = MaterialistThemeFactory(this, options)

        dynamicResources.put("styles/main.css", pages.mainCss)
        content.get().forEach { (htmlName, markdownFile) ->
            webpages.put(htmlName, pages.getDocument(markdownFile.readRaw()))
        }
        fencedCodeBlockIndent.set(4)
    }

    private fun checkTheme() =
        check(
            staticResources.get().isEmpty() &&
                dynamicResources.get().isEmpty() &&
                webpages.get().isEmpty(),
        ) { "Only one theme can be selected." }

    private fun scanReadme() {
        var readme = project.rootDir.resolve("README.md")
        if (readme.exists()) {
            content("index.html", readme)
            return
        }
        readme = project.rootDir.resolve("docs/README.md")
        if (readme.exists()) {
            content("index.html", readme)
            return
        }
        readme = project.rootDir.resolve(".github/README.md")
        if (readme.exists()) {
            content("index.html", readme)
        }
    }

    private fun File.readRaw() = htmlRenderer.render(parser.parse(readText()))
}
