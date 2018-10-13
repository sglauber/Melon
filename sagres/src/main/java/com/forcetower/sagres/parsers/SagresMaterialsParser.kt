/*
 * Copyright (c) 2018.
 * João Paulo Sena <joaopaulo761@gmail.com>
 *
 * This file is part of the UNES Open Source Project.
 *
 * UNES is licensed under the MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.forcetower.sagres.parsers

import com.forcetower.sagres.database.model.SMaterialLink
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import timber.log.Timber

object SagresMaterialsParser {
    @JvmStatic
    fun extractMaterials(document: Document): List<SMaterialLink> {
        val materials = mutableListOf<SMaterialLink>()
        var elements = document.select("label[class=\"material_apoio_arquivo\"]")
        for (element in elements) {
            elementProcessing(element, materials)
        }

        elements = document.select("label[class=\"material_apoio_url\"]")
        for (element in elements) {
            elementProcessing(element, materials)
        }

        elements = document.select("label[class=\"material_apoio_aula\"]")
        for (element in elements) {
            elementProcessing(element, materials)
        }

        elements = document.select("label[class=\"material_apoio_grid_aula\"]")
        for (element in elements) {
            elementProcessing(element, materials)
        }
        return materials
    }

    @JvmStatic
    private fun elementProcessing(element: Element, materials: MutableList<SMaterialLink>) {
        val a = element.selectFirst("a") ?: return

        val link = if (a.attr("href").isEmpty()) a.attr("href") else a.attr("HREF")
        var name = "Arquivo"

        var parent: Element? = element.parent()
        if (parent != null) {
            parent = parent.parent()
            if (parent != null) {
                val elName = parent.selectFirst("td")
                name = elName.text()
            }
        }
        Timber.d("Defined new material $name at $link")

        materials.add(SMaterialLink(name, link))
    }
}