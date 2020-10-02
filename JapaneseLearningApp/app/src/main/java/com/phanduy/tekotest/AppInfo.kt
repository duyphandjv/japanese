package com.phanduy.tekotest

import android.util.DisplayMetrics
import android.view.WindowManager

object AppInfo {
    var width : Int? = null
    var height : Int? = null

    fun getAppInfo(windowManager: WindowManager) {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

    }
}