package com.sepano.app.util

import android.content.Context
import android.util.TypedValue
import androidx.core.content.ContextCompat

fun getMaterialColor(context: Context, materialResId: Int): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(materialResId, typedValue, true)
    return ContextCompat.getColor(context, typedValue.resourceId)
}
