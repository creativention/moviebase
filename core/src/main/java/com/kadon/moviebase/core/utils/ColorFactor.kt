package com.kadon.moviebase.core.utils

import android.graphics.Color
import kotlin.math.roundToInt

/**
 * https://stackoverflow.com/questions/33072365/how-to-darken-a-given-color-int
 * @param color color provided
 * @param factor factor to make color darker
 * @return int as darker color
 */
object ColorFactor {
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

    /**
     * Invert the color
     *
     * @param color
     * @return inverted color
     */
    /*fun invertColor(color: Int): Int {
        val a = color shr 24 and 0xff
        var r = color shr 16 and 0xff
        var g = color shr 8 and 0xff
        var b = color and 0xff
        r = 255 - r
        g = 255 - g
        b = 255 - b
        return toRGB(r, g, b, a)
    }*/

    /**
     *
     * @param alpha
     * @param red
     * @param green
     * @param blue
     * @return
     */
    /*private fun toRGB(alpha: Int, red: Int, green: Int, blue: Int): Int {
        return alpha and 0xff shl 24 or (red and 0xff shl 16
                ) or (green and 0xff shl 8) or (blue and 0xff shl 0)
    }*/
}