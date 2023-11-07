package com.sepano.app.util

import android.content.Context
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt

fun getMaterialColor(context: Context, materialResId: Int): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(materialResId, typedValue, true)
    return ContextCompat.getColor(context, typedValue.resourceId)
}

fun setColorAlpha(color: Int, alpha: Double): Int {
    val defaultAlpha = 255
    val colorAlpha = alpha.times(defaultAlpha).roundToInt()
    return ColorUtils.setAlphaComponent(color, colorAlpha)
}