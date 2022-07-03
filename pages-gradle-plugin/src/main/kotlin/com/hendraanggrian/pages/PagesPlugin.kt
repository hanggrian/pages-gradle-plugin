package com.hendraanggrian.pages

import com.hendraanggrian.pages.internal.DefaultPagesExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.plugins.PublishingPlugin
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

/**
 * Static webpages generator for GitHub pages.
 *
 * @see <a href="https://github.com/hendraanggrian/pages-gradle-plugin">pages-gradle-plugin</a>
 */
open class PagesPlugin : Plugin<Project> {
    companion object {
        const val GROUP: String = PublishingPlugin.PUBLISH_TASK_GROUP
        const val TASK_DEPLOY_PAGES: String = "deployPages"
    }

    override fun apply(project: Project) {
        val pages = project.extensions.create(
            PagesExtension::class, "pages", DefaultPagesExtension::class,
            project.objects, project.layout, project.name
        )
        project.tasks.register<DeployPagesTask>(TASK_DEPLOY_PAGES) {
            group = GROUP
            description = "Write webpages and their resources."
            resourcesMap.set(pages.resourcesMap)
            webpagesMap.set(pages.webpagesMap)
            outputDirectory.set(pages.outputDirectory)
        }
    }
}
