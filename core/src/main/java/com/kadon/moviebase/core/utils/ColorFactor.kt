package com.kadon.moviebase.core.utils

import android.graphics.Color
import kotlin.math.roundToInt

/**
 * https://stackoverflow.com/questions/33072365/how-to-darken-a-given-color-int
 * @param color color provided
 * @param factor factor to make color darker
 * @return int as darker color
 */
object ColorFactor{
    fun darkenColor(color: Int, factor: Float): Int {
        val a: Int = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt()
        val g = (Color.green(color) * factor).roundToInt()
        val b = (Color.blue(color) * factor).roundToInt()
        return Color.argb(
            a,
            r.coerceAtMost(255),
            g.coerceAtMost(255),
            b.coerceAtMost(255)
        )
    }
}