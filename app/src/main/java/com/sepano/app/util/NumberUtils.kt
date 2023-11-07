package com.sepano.app.util

import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.toPrecision(precision: Int): Float {
    val value = 10.0.pow(precision.toDouble())
    return ((this * value).roundToInt())/value.toFloat()
}

fun Double.toNumString():String{
    return if(this.toInt().toDouble() == this){
        this.toInt().toString();
    }else{
        this.toString();
    }
}