package com.cormabara.pwdmanager

import mu.KotlinLogging


object MyLog {

    private val logger = KotlinLogging.logger {}

    fun LTrace(str_: String) {
        logger.trace { str_ }
    }
    fun LInfo(str_: String) {
        logger.info { str_ }
    }
    fun LWarn(str_: String) {
        logger.warn { str_ }
    }
    fun LError(str_: String) {
        logger.error { str_ }
    }
}
