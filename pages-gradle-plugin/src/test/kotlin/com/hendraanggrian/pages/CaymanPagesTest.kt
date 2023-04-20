package com.hendraanggrian.pages

import com.hendraanggrian.pages.PagesPlugin.Companion.TASK_DEPLOY_PAGES
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

class CaymanPagesTest {
    @Rule @JvmField
    val testProjectDir = TemporaryFolder()
    private lateinit var buildFile: File
    private lateinit var runner: GradleRunner

    @BeforeTest
    @Throws(IOException::class)
    fun setup() {
        testProjectDir.newFile("settings.gradle.kts")
            .writeText("rootProject.name = \"cayman-test\"")
        buildFile = testProjectDir.newFile("build.gradle.kts")
        runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())
    }

    @Test
    fun fullConfiguration() {
        testProjectDir.newFile("Content.md").writeText(
            """
            # Hello
            ## World
            """.trimIndent()
        )
        buildFile.writeText(
            """
            plugins {
                id("com.hendraanggrian.pages")
            }
            pages {
                outputDirectory.set(buildDir.resolve("custom-dir"))
                contents.index("Content.md")
                cayman {
                    accentColor = "#ff0000"
                    primaryColor = "#00ff00"
                    secondaryColor = "#0000ff"
                    authorName = "Cool Dude"
                    authorUrl = "https://www.google.com"
                    projectName = "Cool Stuff"
                    projectDescription = "Cures cancer"
                    projectUrl = "https://www.google.com"
                    footerCredit = false
                    button("Rate\nUs", "https://www.google.com")
                    button("Leave\nReview", "https://www.google.com")
                    button("Report\nUser", "https://www.google.com")
                }
            }
            """.trimIndent()
        )
        assertEquals(
            SUCCESS,
            runner.withArguments(TASK_DEPLOY_PAGES).build().task(":$TASK_DEPLOY_PAGES")!!.outcome
        )
        testProjectDir.root.resolve("build/custom-dir/index.html").readText().let {
            assertTrue("Cool Dude" in it)
            assertTrue("https://www.google.com" in it)
            assertTrue("Cool Stuff" in it)
            assertTrue("Cures cancer" in it)
            assertTrue("Hello" in it)
            assertTrue("World" in it)
        }
    }
}
