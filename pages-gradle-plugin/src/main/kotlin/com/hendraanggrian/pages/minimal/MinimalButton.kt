package com.hendraanggrian.pages.minimal

/**
 * Header button data class.
 * @param line1 first line of text.
 * @param line2 second line of text, with stronger emphasis.
 * @param url absolute or relative path to redirect to on button click.
 */
data class MinimalButton(val line1: String, val line2: String, val url: String)

/** Allows to add side-by-side buttons inside header tag. */
interface MinimalButtonsScope {
    /**
     * Add header button.
     * @param line1 button's top text.
     * @param line2 button's bottom text with stronger emphasis.
     * @param url to redirect on button click.
     */
    fun button(line1: String, line2: String, url: String)
}
