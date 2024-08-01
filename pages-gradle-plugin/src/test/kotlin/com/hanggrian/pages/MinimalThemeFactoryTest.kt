package com.hanggrian.pages

import com.hanggrian.pages.PagesPlugin.Companion.TASK_DEPLOY_PAGES
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.IOException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MinimalThemeFactoryTest {
    @Rule @JvmField
    val rootFolder = TemporaryFolder()
    private lateinit var buildFile: File
    private lateinit var runner: GradleRunner

    @BeforeTest
    @Throws(IOException::class)
    fun setup() {
        rootFolder
            .newFile("settings.gradle.kts")
            .writeText("rootProject.name = \"minimal-test\"")
        buildFile = rootFolder.newFile("build.gradle.kts")
        runner =
            GradleRunner
                .create()
                .withPluginClasspath()
                .withProjectDir(rootFolder.root)
                .withTestKitDir(rootFolder.newFolder())
    }

    @Test
    fun fullConfiguration() {
        rootFolder
            .newFile("Content.md")
            .writeText(
                """
                # Hello
                ## World
                """.trimIndent(),
            )
        buildFile
            .writeText(
                """
                plugins {
                    id("com.hanggrian.pages")
                }
                pages {
                    outputDirectory.set(layout.buildDirectory.dir("custom-dir"))
                    content.put("index.html", file("Content.md"))
                    minimal {
                        colorPrimary = "#ff0000"
                        colorPrimaryContainer = "#00ff00"
                        colorPrimaryContainer2 = "#0000ff"
                        authorName = "Cool Dude"
                        authorUrl = "https://www.google.com"
                        projectName = "Cool Stuff"
                        projectDescription = "This project cures cancer"
                        projectUrl = "https://www.google.com"
                        isFooterCredit = false
                        button("Rate\nUs", "https://www.google.com")
                        button("Leave\nReview", "https://www.google.com")
                        button("Report\nUser", "https://www.google.com")
                    }
                }
                """.trimIndent(),
            )
        assertEquals(
            SUCCESS,
            runner
                .withArguments(TASK_DEPLOY_PAGES)
                .build()
                .task(":$TASK_DEPLOY_PAGES")!!
                .outcome,
        )
        rootFolder.root
            .resolve("build/custom-dir/index.html")
            .readText()
            .let {
                assertTrue("Cool Dude" in it)
                assertTrue("https://www.google.com" in it)
                assertTrue("Cool Stuff" in it)
                assertTrue("This project cures cancer" in it)
                assertTrue("Hello" in it)
                assertTrue("World" in it)
            }
    }
}
