package com.hendraanggrian.pages

/**
 * Forces pages' feature configurations to be on the same level, such as:
 *
 * ```
 * pages {
 *     minimal { }
 *     cayman { }
 * }
 * ```
 */
@DslMarker
@Target(AnnotationTarget.CLASS)
annotation class PagesConfigurationDsl
