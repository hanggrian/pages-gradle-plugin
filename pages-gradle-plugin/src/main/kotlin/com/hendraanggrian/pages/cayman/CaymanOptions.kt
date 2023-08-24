package com.hendraanggrian.pages.cayman

import com.hendraanggrian.pages.PagesConfigurationDsl
import com.hendraanggrian.pages.internal.PageOptions

/**
 * Theme configuration for [minimal-dark-theme](https://github.com/hendraanggrian/cayman-dark-theme/).
 */
@PagesConfigurationDsl
interface CaymanOptions : PageOptions {
    /** Use dark theme. Default is false. */
    var darkMode: Boolean

    /** Accent color of the webpage. Default is material color `Blue A200`. */
    var accentColor: String

    /**
     * Primary color of the webpage, used as banner's gradient end and heading color. Default
     * is `#159957`.
     */
    var primaryColor: String

    /**
     * Secondary color of the webpage, used as banner's gradient end and heading color. Default
     * is `#155799`.
     */
    var secondaryColor: String
}

internal class CaymanOptionsImpl(override var projectName: String) : CaymanOptions {
    internal val buttons = mutableMapOf<String, String>()

    override var darkMode: Boolean = false
    override var accentColor: String = "#448aff"
    override var primaryColor: String = "#159957"
    override var secondaryColor: String = "#155799"
    override var authorName: String? = null
    override var authorUrl: String? = null
    override var projectDescription: String? = null
    override var projectUrl: String? = null
    override var footerCredit: Boolean = true
    override fun button(text: String, url: String) {
        buttons += text to url
    }
}
