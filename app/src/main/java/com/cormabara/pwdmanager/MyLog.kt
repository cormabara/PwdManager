package com.cormabara.pwdmanager

import android.util.Log
import mu.KotlinLogging


object MyLog {

    private val logger = KotlinLogging.logger {}

    fun LTrace(str_: String) {
        logger.trace { str_ }
        Log.i("MyLogT",str_)
    }
    fun LInfo(str_: String) {
        logger.info { str_ }
        Log.i("MyLogI",str_)
    }
    fun LWarn(str_: String) {
        logger.warn { str_ }
        Log.w("MyLogW",str_)

    }
    fun LError(str_: String) {
        logger.error { str_ }
        Log.e("MyLogE",str_)

    }
}
