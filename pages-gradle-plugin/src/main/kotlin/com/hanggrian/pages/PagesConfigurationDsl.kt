package com.hanggrian.pages

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
public annotation class PagesConfigurationDsl
