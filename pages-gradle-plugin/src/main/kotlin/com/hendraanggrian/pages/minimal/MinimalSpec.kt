package com.hendraanggrian.pages.minimal

import org.gradle.api.Action
import java.io.File

interface MinimalSpec {
    /**
     * Accent color of the webpage.
     * Default is material color `Blue A200`.
     */
    var accentColor: String

    /**
     * Accent color of the webpage when hovered in light theme.
     * Default is material color `Blue A200 Dark`.
     */
    var accentLightHoverColor: String

    /**
     * Accent color of the webpage when hovered in dark theme.
     * Default is material color `Blue A200 Light`.
     */
    var accentDarkHoverColor: String
    /**
     * Optional relative path to website logo.
     * E.g. `images/icon.png`.
     */
    var icon: String?

    /**
     * Author full name in title and footer.
     * If left empty, corresponding tag in footer is removed but title will still show project name.
     */
    var authorName: String?

    /**
     * Author website url in footer.
     * If left empty, author information in footer will not be clickable.
     */
    var authorUrl: String?

    /**
     * Project full name in header.
     * If left empty, module name will be used.
     */
    var projectName: String

    /**
     * Project description in header.
     * If left empty, corresponding tag in header is removed.
     */
    var projectDescription: String?

    /**
     * Project website url in header.
     * If left empty, corresponding tag in header is removed.
     */
    var projectUrl: String?

    /**
     * The content of this markdown will be converted into `index.html`.
     * Cannot be empty.
     */
    var markdownFile: File?

    /** Configures header buttons. Header buttons size is capped at 3. */
    fun headerButtons(action: Action<MinimalButtonsScope>)

    /**
     * Small theme credit in footer.
     * Enabled by default.
     */
    var footerCredit: Boolean
}
