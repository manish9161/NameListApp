package com.demo.namelistapp.core

import com.orhanobut.logger.Logger

object LogUtils {

    fun printDebug(message: String) {
        Logger.d(message)
    }

    fun printError(message: String) {
        Logger.e(message)
    }
}