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
import org.gradle.work.DisableCachingByDefault
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/** Task to run when `deployPages` command is executed. */
@DisableCachingByDefault
public abstract class DeployPagesTask :
    DefaultTask(),
    DeployPagesSpec {
    @get:Input
    abstract override val staticResources: SetProperty<String>

    @get:Input
    abstract override val dynamicResources: MapProperty<String, String>

    @get:OutputDirectory
    abstract override val outputDirectory: DirectoryProperty

    @get:Internal
    abstract override val webpages: MapProperty<String, String>

    @get:Internal
    abstract override val fencedCodeBlockIndent: Property<Int>

    @TaskAction
    public fun deploy() {
        val staticResourcesContent = staticResources.get()
        val dynamicResourcesContent = dynamicResources.get()
        val webpagesContent = webpages.get()
        check(
            staticResourcesContent.isNotEmpty() ||
                dynamicResourcesContent.isNotEmpty() ||
                webpagesContent.isNotEmpty(),
        ) { "Nothing to write." }
        val outputDir = outputDirectory.asFile.get()

        logger.info("Copying resources:")
        staticResourcesContent.forEach { filepath ->
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
        dynamicResourcesContent.forEach { (filepath, content) ->
            logger.info("  - $filepath")
            val targetDir = outputDir.resolve(filepath.substringBefore('/'))
            targetDir.prepare()
            targetDir.resolve(filepath.substringAfter('/')).writeText(content)
        }

        logger.info("Writing pages:")
        webpagesContent.forEach { (filename, content) ->
            logger.info("  - $filename")
            outputDir.resolve(filename).writeText(
                buildString {
                    appendLine("<!doctype html>")
                    var text = content
                    if (fencedCodeBlockIndent.isPresent) {
                        text = text.fixFencedCodeBlock(fencedCodeBlockIndent.get())
                    }
                    append(text)
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
