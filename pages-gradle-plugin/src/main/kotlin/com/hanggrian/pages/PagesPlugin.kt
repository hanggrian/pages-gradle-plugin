package com.hanggrian.pages

import com.hanggrian.pages.internal.DefaultPagesExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

/**
 * Static webpages generator for GitHub pages.
 *
 * @see <a href="https://github.com/hanggrian/pages-gradle-plugin">pages-gradle-plugin</a>
 */
public open class PagesPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val pages =
            project.extensions.create(
                PagesExtension::class,
                "pages",
                DefaultPagesExtension::class,
                project,
            ) as DefaultPagesExtension
        val deployResources =
            project.tasks.register<Copy>(TASK_DEPLOY_RESOURCES) {
                group = GROUP
                description = "Copy local resources."
                with(pages.resources)
                into(pages.outputDirectory)
            }
        project.tasks.register<DeployPagesTask>(TASK_DEPLOY_PAGES) {
            group = GROUP
            description = "Write webpages and their resources."
            staticResources.convention(pages.staticResources)
            dynamicResources.convention(pages.dynamicResources)
            webpages.convention(pages.webpages)
            outputDirectory.convention(pages.outputDirectory)
            fencedCodeBlockIndent.set(pages.fencedCodeBlockIndent)
            dependsOn(deployResources)
        }
    }

    public companion object {
        public const val GROUP: String = "website"
        public const val TASK_DEPLOY_RESOURCES: String = "deployResources"
        public const val TASK_DEPLOY_PAGES: String = "deployPages"
    }
}
