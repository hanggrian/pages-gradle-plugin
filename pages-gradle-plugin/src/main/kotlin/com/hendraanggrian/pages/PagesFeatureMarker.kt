package com.hendraanggrian.pages

/**
 * Forces pages' feature configurations to be on the same level, such as:
 *
 * ```
 * pages {
 *     minimal { }
 * }
 * ```
 */
@DslMarker
@Target(AnnotationTarget.CLASS)
annotation class PagesFeatureMarker
