package com.hanggrian.pages.materialist

import com.hanggrian.pages.PagesConfigurationDsl
import com.hanggrian.pages.internal.PageOptions

/**
 * Theme configuration for [materialist-theme](https://github.com/hendraanggrian/materialist-theme/).
 */
@PagesConfigurationDsl
public interface MaterialistOptions : PageOptions {
    /** Primary color, default is #448aff. */
    public var colorPrimary: String

    /** Secondary color, default is #44503b. */
    public var colorSecondary: String

    /** On primary color, default is #fff. */
    public var colorOnPrimary: String

    /** Surface color, default is #e9ebde. */
    public var colorSurface: String

    /** Surface container color, default is #f6f9ec. */
    public var colorSurfaceContainer: String

    /** Surface container high color, default is #e1e4d7. */
    public var colorSurfaceContainerHigh: String

    /** Surface container highest color, default is #dadcd1. */
    public var colorSurfaceContainerHighest: String

    /** On surface color, default is #141613. */
    public var colorOnSurface: String

    /** On surface variant color, default is #34382f. */
    public var colorOnSurfaceVariant: String

    /** Outline color, default is #616659. */
    public var colorOutline: String
}

internal class MaterialistOptionsImpl(override var projectName: String) : MaterialistOptions {
    override var colorPrimary = "#2f5813"
    override var colorSecondary = "#44503b"
    override var colorOnPrimary = "#fff"
    override var colorSurface = "#e9ebde"
    override var colorSurfaceContainer = "#f6f9ec"
    override var colorSurfaceContainerHigh = "#e1e4d7"
    override var colorSurfaceContainerHighest = "#dadcd1"
    override var colorOnSurface = "#141613"
    override var colorOnSurfaceVariant = "#34382f"
    override var colorOutline = "#616659"

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
