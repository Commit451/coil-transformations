package com.commit451.coiltransformations

import coil.size.Dimension

/**
 * If this is a [Dimension.Pixels] value, return its number of pixels. Else, invoke and return
 * the value from [block].
 */
internal inline fun Dimension.pxOrElse(block: () -> Int): Int {
    return if (this is Dimension.Pixels) px else block()
}
