package com.hendraanggrian.pages

import com.hendraanggrian.pages.internal.DefaultPagesExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.plugins.PublishingPlugin
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register

/**
 * Static webpages generator for GitHub pages.
 *
 * @see <a href="https://github.com/hendraanggrian/pages-gradle-plugin">pages-gradle-plugin</a>
 */
open class PagesPlugin : Plugin<Project> {
    companion object {
        const val GROUP: String = PublishingPlugin.PUBLISH_TASK_GROUP
        const val TASK_DEPLOY_RESOURCES: String = "deployResources"
        const val TASK_DEPLOY_PAGES: String = "deployPages"
    }

    override fun apply(project: Project) {
        val pages = project.extensions.create(PagesExtension::class, "pages", DefaultPagesExtension::class, project)
        val deployResources = project.tasks.register<DeployResourcesTask>(TASK_DEPLOY_RESOURCES) {
            group = GROUP
            description = "Write images, styles and scripts directories for minimal website."
        }
        val deployWebpages = project.tasks.register<DeployPagesTask>(TASK_DEPLOY_PAGES) {
            group = GROUP
            description = "Write HTML files for minimal website."
            dependsOn(deployResources)
        }
        project.afterEvaluate {
            deployResources {
                resourcesMap.convention(pages.resourcesMap)
                outputDirectory.convention(pages.outputDirectory)
            }
            deployWebpages {
                webpagesMap.convention(pages.webpagesMap)
                outputDirectory.convention(pages.outputDirectory)
            }
        }
    }
}
