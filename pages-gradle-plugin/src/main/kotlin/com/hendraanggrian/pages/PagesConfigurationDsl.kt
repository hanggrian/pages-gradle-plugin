package com.hendraanggrian.pages

/**
 * Forces pages' configurations to be on the same level, such as:
 *
 * ```
 * pages {
 *     minimal { }
 *     cayman { }
 *     materialist { }
 * }
 * ```
 */
@DslMarker
@Target(AnnotationTarget.CLASS)
annotation class PagesConfigurationDsl
