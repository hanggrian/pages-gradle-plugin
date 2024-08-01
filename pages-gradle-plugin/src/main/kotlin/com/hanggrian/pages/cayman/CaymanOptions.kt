package com.hanggrian.pages.cayman

import com.hanggrian.pages.PagesConfigurationDsl
import com.hanggrian.pages.internal.PageOptions

/**
 * Theme configuration for [minimal-dark-theme](https://github.com/hendraanggrian/cayman-dark-theme/).
 */
@PagesConfigurationDsl
public interface CaymanOptions : PageOptions {
    /** Use dark mode, default is false. */
    public var darkMode: Boolean

    /** Primary color, default is #159957. */
    public var colorPrimary: String

    /** Secondary color, default is #448aff. */
    public var colorSecondary: String

    /** Secondary container color, default is #155799. */
    public var colorSecondaryContainer: String
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
    override var isFooterCredit: Boolean = true

    internal val buttons = mutableMapOf<String, String>()

    override fun button(text: String, url: String) {
        buttons += text to url
    }
}
