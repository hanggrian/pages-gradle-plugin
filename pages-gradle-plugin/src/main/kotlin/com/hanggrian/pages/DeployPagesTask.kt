package com.hanggrian.pages

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.setProperty
import org.w3c.dom.Document
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/** Task to run when `deployPages` command is executed. */
public open class DeployPagesTask :
    DefaultTask(),
    DeployPagesSpec {
    @Input
    final override val staticResources: SetProperty<String> =
        project.objects
            .setProperty()

    @Input
    final override val dynamicResources: MapProperty<String, String> =
        project.objects
            .mapProperty()

    @Input
    final override val webpages: MapProperty<String, Document> =
        project.objects
            .mapProperty()

    @OutputDirectory
    final override val outputDirectory: DirectoryProperty =
        project.objects
            .directoryProperty()

    @Internal
    public val fencedCodeBlockIndent: Property<Int> =
        project.objects
            .property()

    private val transformer = TransformerFactory.newInstance().newTransformer()

    @TaskAction
    public fun deploy() {
        check(
            staticResources.get().isNotEmpty() ||
                dynamicResources.get().isNotEmpty() ||
                webpages.get().isNotEmpty(),
        ) { "Nothing to write." }
        val outputDir = outputDirectory.asFile.get()

        logger.info("Copying resources:")
        staticResources.get().forEach { filepath ->
            val filepathWithoutRoot = filepath.substringAfter('/')
            logger.info("  - $filepathWithoutRoot")
            val targetFile = outputDir.resolve(filepathWithoutRoot)
            targetFile.parentFile.prepare()
            Files.copy(
                javaClass.getResourceAsStream("/$filepath")!!,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
            )
        }

        logger.info("Writing resources:")
        dynamicResources.get().forEach { (filepath, content) ->
            logger.info("  - $filepath")
            val targetDir = outputDir.resolve(filepath.substringBefore('/'))
            targetDir.prepare()
            targetDir.resolve(filepath.substringAfter('/')).writeText(content)
        }

        logger.info("Writing pages:")
        webpages.get().forEach { (filename, document) ->
            logger.info("  - $filename")
            val file = outputDir.resolve(filename)
            transformer.transform(DOMSource(document), StreamResult(FileWriter(file)))
            file.writeText(
                buildString {
                    appendLine("<!doctype html>")
                    if (fencedCodeBlockIndent.isPresent) {
                        append(file.readText().fixFencedCodeBlock(fencedCodeBlockIndent.get()))
                    } else {
                        append(file.readText())
                    }
                },
            )
        }
    }

    private fun File.prepare() {
        if (!exists()) {
            mkdir()
        }
    }

    /**
     * kotlinx.html automatically add indent to commonmark's result. This fix reverses that
     * behavior.
     */
    private fun String.fixFencedCodeBlock(indentTimes: Int): String {
        val indent = "    "
        val totalIndent =
            buildString {
                repeat(indentTimes) {
                    append(indent)
                }
            }
        return replace("<pre>\n$totalIndent$indent<code", "<pre><code")
            .replace("</code>\n$totalIndent</pre>", "</code></pre>")
    }
}
