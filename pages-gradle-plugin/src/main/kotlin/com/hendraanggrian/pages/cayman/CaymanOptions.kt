package com.hendraanggrian.pages.cayman

import com.hendraanggrian.pages.PagesConfigurationDsl
import com.hendraanggrian.pages.internal.PageOptions

/**
 * Theme configuration for [minimal-dark-theme](https://github.com/hendraanggrian/cayman-dark-theme/).
 */
@PagesConfigurationDsl
interface CaymanOptions : PageOptions {
    /** Use dark mode, default is false. */
    var darkMode: Boolean

    /** Primary color, default is #159957. */
    var colorPrimary: String

    /** Secondary color, default is #448aff. */
    var colorSecondary: String

    /** Secondary container color, default is #155799. */
    var colorSecondaryContainer: String
}

internal class CaymanOptionsImpl(override var projectName: String) : CaymanOptions {
    override var darkMode = false
    override var colorPrimary = "#159957"
    override var colorSecondary = "#448aff"
    override var colorSecondaryContainer = "#155799"

    override var authorName: String? = null
    override var authorUrl: String? = null
    override var projectDescription: String? = null
    override var projectUrl: String? = null
    override var footerCredit: Boolean = true

    internal val buttons = mutableMapOf<String, String>()
    override fun button(text: String, url: String) {
        buttons += text to url
    }
}
