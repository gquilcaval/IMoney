package com.example.imoney

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by Giancarlos Quilca on 30/09/2022.
 */

fun formatPriceToString(price: Float): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
    val price = format.format(price)
    return price.substring(2, price.length)
}

fun formatPriceToFloat(price: String): Float {
    return price.replace(",","").toFloat()
}