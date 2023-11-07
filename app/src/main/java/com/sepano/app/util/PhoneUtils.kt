package com.sepano.app.util

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.sepano.app.model.SpaceInfo


fun getMemoryInfo(context: Context): SpaceInfo {
    val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val memInfo = ActivityManager.MemoryInfo()
    actManager.getMemoryInfo(memInfo)
    val totalMemory = memInfo.totalMem
    val availMemory = memInfo.availMem
    return SpaceInfo(availMemory, totalMemory)
}

fun getStorageInfo(): SpaceInfo {
    val stat = StatFs(Environment.getExternalStorageDirectory().path)
    val totalMemory = stat.blockSizeLong * stat.blockCountLong
    val availMemory = stat.blockSizeLong * stat.availableBlocksLong
    return SpaceInfo(availMemory, totalMemory)
}