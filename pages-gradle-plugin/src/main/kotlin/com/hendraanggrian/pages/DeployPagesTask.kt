package com.hendraanggrian.pages

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.setProperty
import org.w3c.dom.Document
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/** Task to run when `deployPages` command is executed. */
open class DeployPagesTask : DefaultTask(), DeployPagesSpec {
    @Input
    final override val staticResources: SetProperty<String> = project.objects.setProperty()

    @Input
    final override val dynamicResources: MapProperty<Pair<String, String>, String> = project.objects.mapProperty()

    @Input
    final override val webpages: MapProperty<String, Document> = project.objects.mapProperty()

    @OutputDirectory
    final override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    private val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

    @TaskAction
    fun deploy() {
        check(
            staticResources.get().isNotEmpty() ||
                dynamicResources.get().isNotEmpty() ||
                webpages.get().isNotEmpty()
        ) { "No pages to write" }
        val outputDir = outputDirectory.asFile.get()

        logger.info("Copying resources:")
        staticResources.get().forEach { filepath ->
            val filepathWithoutRoot = filepath.substringAfter('/')
            logger.info("  $filepathWithoutRoot")
            val targetFile = outputDir.resolve(filepathWithoutRoot)
            targetFile.parentFile.prepare()
            Files.copy(
                javaClass.getResourceAsStream("/$filepath")!!,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
        }

        logger.info("Writing resources:")
        dynamicResources.get().forEach { (file, content) ->
            val dirname = file.first
            val filename = file.second
            logger.info("  $dirname/$filename")
            val targetDir = outputDir.resolve(dirname)
            targetDir.prepare()
            targetDir.resolve(filename).writeText(content)
        }

        logger.info("Writing pages:")
        webpages.get().forEach { (filename, document) ->
            logger.info("  $filename")
            val file = outputDir.resolve(filename)
            transformer.transform(DOMSource(document), StreamResult(FileWriter(file)))
            file.writeText(file.readText().fixFencedCodeBlock())
        }
    }

    private fun File.prepare() {
        if (!exists()) mkdir()
    }

    /**
     * kotlinx.html automatically add indent to commonmark's result.
     * This fix reverses that behavior.
     */
    private fun String.fixFencedCodeBlock(): String =
        replace("<pre>\n                    <code", "<pre><code")
            .replace("</code>\n                </pre>", "</code></pre>")
}
