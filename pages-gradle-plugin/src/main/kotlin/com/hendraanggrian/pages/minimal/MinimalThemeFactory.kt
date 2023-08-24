package com.hendraanggrian.pages.minimal

import com.hendraanggrian.pages.PagesExtension
import com.hendraanggrian.pages.WebsiteFactory
import kotlinx.html.BODY
import kotlinx.html.HEAD
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.small
import kotlinx.html.strong
import kotlinx.html.title
import kotlinx.html.ul
import kotlinx.html.unsafe

internal class MinimalThemeFactory(extension: PagesExtension, options: MinimalOptionsImpl) :
    WebsiteFactory(extension, options.buttons), MinimalOptions by options {

    private companion object {
        const val HEADER_WIDTH = 270
        const val HEADER_WRAPPED_WIDTH_PERCENTAGE = 99
    }

    override fun HEAD.onCreateHead() {
        title(projectName)
        if (favicon.isPresent) {
            link(rel = "icon", href = favicon.get())
        }
        meta(name = "viewport", content = "width=device-width")
        meta(content = "chrome=1") { httpEquiv = "X-UA-Compatible" }
        link(rel = "stylesheet", href = "styles/main.css")
        link(
            rel = "stylesheet",
            href = "https://fonts.googleapis.com/" +
                "css2?family=Material+Symbols+Sharp:opsz,wght,FILL,GRAD@48,400,1,0"
        )
        if (styles.isPresent) {
            styles.get().forEach { link(rel = "stylesheet", href = it) }
        }
        script(src = "scripts/theme.js") { }
        if (scripts.isPresent) {
            scripts.get().forEach { script(src = it) { } }
        }
        comment("Primary meta tags")
        meta(name = "title", content = projectName)
        projectDescription?.let { meta(name = "description", content = it) }
    }

    override fun BODY.onCreateBody(content: String) {
        div(classes = "wrapper") {
            header {
                h1 { text(projectName) }
                projectDescription?.let { p { text(it) } }
                projectUrl?.let { url ->
                    p(classes = "view") {
                        a(href = url) {
                            if ("github.com" in url) {
                                text("View the Project on GitHub ")
                                val parts = when {
                                    !url.endsWith('/') -> url
                                    else -> url.substringBeforeLast('/')
                                }.split('/').reversed()
                                small { text("${parts[1]}/${parts[0]}") }
                            } else {
                                text("View the Project")
                            }
                        }
                    }
                }
                if (buttons.isNotEmpty()) {
                    ul {
                        buttons.forEach { (text, url) ->
                            li {
                                a(href = url) {
                                    text(text.substringBefore('\n'))
                                    strong { text(text.substringAfter('\n')) }
                                }
                            }
                        }
                    }
                }
            }
            section { unsafe { +content } }
            footer {
                p {
                    button {
                        classes = setOf("material-symbols-sharp")
                        id = "theme-toggle"
                        title = "Toggle dark mode"
                        onClick = "toggleDarkMode()"
                    }
                }
                p {
                    if (authorName != null) {
                        text("This project is maintained by ")
                        if (authorUrl != null) {
                            a(href = authorUrl) { text(authorName!!) }
                        } else {
                            text(authorName!!)
                        }
                    }
                }
                if (footerCredit) {
                    small {
                        text("Hosted on GitHub Pages — Theme by ")
                        a(href = "https://github.com/orderedlist/") { text("orderedlist") }
                    }
                }
            }
        }
        script(src = "scripts/scale.fix.js") { }
    }

    val mainCss: String
        get() {
            val buttonsSize = buttons.size
            return """
                * {
                  --accent: $accentColor; }

                :root {
                  --background: #fafafa; /* grey_50 */

                  --accent-hover: $accentLightHoverColor;

                  --button-background: #f5f5f5; /* Grey 100 */
                  --button-background-active: #c2c2c2; /* Grey 100 Dark */
                  --button-border: #e0e0e0; /* Grey 300 */
                  --button-text1: #9e9e9e; /* Grey 500 */
                  --button-text1-hover: #707070; /* Grey 500 Dark */
                  --button-text2: #212121; /* Grey 900 */
                  --button-text2-hover: #000000; /* Grey 900 Dark */;

                  --text-heavy: #212121; /* Grey 900 */
                  --text-medium: #424242; /* Grey 800 */
                  --text-body: #616161; /* Grey 700 */
                  --text-caption: #757575; /* Grey 600 */
                  --text-caption-hover: #494949; /* Grey 600 Dark */

                  --code-background: #f5f5f5; /* Grey 100 */
                  --code-border: #e0e0e0; /* Grey 300 */

                  --border: #e0e0e0; /* Grey 300 */

                  --theme-toggle-image: 'dark_mode'; }

                :root.theme-dark {
                  --background: #212121; /* grey_900*/

                  --accent-hover: $accentDarkHoverColor;

                  --button-background: #424242; /* Grey 800 */
                  --button-background-active: #6d6d6d; /* Grey 800 Light */
                  --button-border: #757575; /* Grey 600 */
                  --button-text1: #bdbdbd; /* Grey 400 */
                  --button-text1-hover: #8d8d8d; /* Grey 400 Light */
                  --button-text2: #fafafa; /* Grey 50 */
                  --button-text2-hover: #ffffff; /* Grey 50 Light */;

                  --text-heavy: #fafafa; /* Grey 50 */
                  --text-medium: #f5f5f5; /* Grey 100 */
                  --text-body: #bdbdbd; /* Grey 400 */
                  --text-caption: #9e9e9e; /* Grey 500 */
                  --text-caption-hover: #cfcfcf; /* Grey 500 Light */

                  --code-background: #424242; /* Grey 800 */
                  --code-border: #757575; /* Grey 600 */

                  --border: #424242; /* Grey 800 */

                  --theme-toggle-image: 'light_mode'; }

                body {
                  background-color: var(--background);
                  padding: 50px;
                  font: 14px/1.5 -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Fira Sans', 'Droid Sans', 'Helvetica Neue', Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';
                  color: var(--text-body);
                  font-weight: 400; }

                h1, h2, h3, h4, h5, h6 {
                  color: var(--text-heavy);
                  margin: 0 0 20px; }

                p, ul, ol, table, pre, dl {
                  margin: 0 0 20px; }

                h1, h2, h3 {
                  line-height: 1.1; }

                h1 {
                  font-size: 28px;
                  font-weight: 500; }

                h2 {
                  color: var(--text-heavy);
                  font-weight: 500; }

                h3, h4, h5, h6 {
                  color: var(--text-heavy);
                  font-weight: 500; }

                a {
                  color: var(--accent);
                  text-decoration: none; }
                  a:hover {
                    color: var(--accent-hover); }

                a small {
                  font-size: 11px;
                  color: var(--text-caption);
                  margin-top: -0.3em;
                  display: block; }
                  a:hover small {
                    color: var(--text-caption-hover); }

                p small {
                  color: var(--text-caption); }

                .wrapper {
                  width: 860px;
                  margin: 0 auto; }

                blockquote {
                  border-left: 4px solid var(--border);
                  margin: 0;
                  padding: 0 0 0 20px;
                  font-style: italic; }
                  blockquote p {
                    color: var(--text-caption); }

                code, pre {
                  font-family: Monaco, Bitstream Vera Sans Mono, Lucida Console, Terminal, Consolas, Liberation Mono, DejaVu Sans Mono, Courier New, monospace;
                  background-color: var(--code-background); }

                code {
                  padding: 2px 4px;
                  border-radius: 0.3rem; }

                pre {
                  padding: 8px 15px;
                  background: var(--code-background);
                  border-radius: 5px;
                  border: 1px solid var(--code-border);
                  overflow-x: auto; }
                  pre code {
                    padding: 0px 0px; }

                table {
                  width: 100%;
                  border-collapse: collapse; }

                th, td {
                  text-align: left;
                  padding: 5px 10px;
                  border-bottom: 1px solid var(--border); }

                dt {
                  color: var(--text-medium);
                  font-weight: 500; }

                th {
                  color: var(--text-medium); }

                img {
                  max-width: 100%; }

                header {
                  width: ${HEADER_WIDTH}px;
                  float: left;
                  position: fixed;
                  -webkit-font-smoothing: subpixel-antialiased; }
                  header ul {
                    list-style: none;
                    height: 40px;
                    padding: 0;
                    background: var(--button-background);
                    border-radius: 5px;
                    border: 1px solid var(--button-border);
                    width: 270px; }
                    header ul a {
                      line-height: 1;
                      font-size: 11px;
                      color: var(--button-text1);
                      display: block;
                      text-align: center;
                      padding-top: 6px;
                      height: 34px; }
                      header ul a:hover {
                        color: var(--button-text1-hover); }
                      header ul a:active {
                        background-color: var(--button-background-active); }
                      header ul a strong {
                        font-size: 14px;
                        display: block;
                        color: var(--button-text2); }
                  header li {
                    width: ${if (buttonsSize == 0) HEADER_WIDTH else HEADER_WIDTH / buttonsSize - 1}px;
                    float: left;
                    border-right: 1px solid var(--button-border);
                    height: 40px; }
                    header li:last-child {
                      border-right: 0px; }
                    header li:first-child a {
                      border-radius: 5px 0 0 5px; }
                    header li:last-child a {
                      border-radius: 0 5px 5px 0; }
                  header ul li + li + li {
                    border-right: none;
                    width: 89px; }

                strong {
                  color: var(--text-heavy);
                  font-weight: 500; }

                section {
                  width: 500px;
                  float: right;
                  padding-bottom: 50px; }

                small {
                  font-size: 11px; }

                hr {
                  border: 0;
                  background: var(--border);
                  height: 1px;
                  margin: 0 0 20px; }

                footer {
                  width: 270px;
                  float: left;
                  position: fixed;
                  bottom: 50px;
                  -webkit-font-smoothing: subpixel-antialiased; }
                  footer button {
                    height: 40px;
                    padding: 0;
                    background: var(--button-background);
                    border-radius: 5px;
                    border: 1px solid var(--button-border);
                    width: 40px;
                    color: var(--button-text2);
                    justify-content: center;
                    cursor: pointer; }
                    footer button:hover {
                      color: var(--button-text2-hover); }
                    footer button:active {
                      background-color: var(--button-background-active); }

                #theme-toggle:after {
                  content: var(--theme-toggle-image); }

                @media print, screen and (max-width: 960px) {
                  div.wrapper {
                    width: auto;
                    margin: 0; }

                  header, section, footer {
                    float: none;
                    position: static;
                    width: auto; }

                  header {
                    padding-right: 320px; }

                  footer {
                    padding-right: 60px; }

                  section {
                    border: 1px solid var(--border-regular);
                    border-width: 1px 0;
                    padding: 20px 0;
                    margin: 0 0 20px; }

                  header a small {
                    display: inline; }

                  header ul {
                    position: absolute;
                    right: 50px;
                    top: 52px; }

                  footer button {
                    position: fixed;
                    right: 50px;
                    bottom: 52px; } }

                @media print, screen and (max-width: 720px) {
                  body {
                    word-wrap: break-word; }

                  header {
                    padding: 0; }

                  header ul, header p.view {
                    position: static; }

                  pre, code {
                    word-wrap: normal; } }

                @media print, screen and (max-width: 480px) {
                  body {
                    padding: 15px; }

                  header ul {
                    width: $HEADER_WRAPPED_WIDTH_PERCENTAGE%; }

                  header li, header ul li + li + li {
                    width: ${if (buttonsSize == 0) HEADER_WRAPPED_WIDTH_PERCENTAGE else HEADER_WRAPPED_WIDTH_PERCENTAGE / buttonsSize}%; }

                  footer button {
                    position: fixed;
                    right: 25px;
                    bottom: 26px; } }

                @media print {
                  body {
                    padding: 0.4in;
                    font-size: 12pt;
                    background-color: white;
                    color: black; } }

            """.trimIndent()
        }
}
