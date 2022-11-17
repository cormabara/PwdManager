package com.cormabara.simpleapp

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class AppCnfFile(file_: File) {

    private val appCnfMapper = jacksonObjectMapper()
    data class AppConfigData (
        val id: Int,
        val main_password: String
    )
    private val file: File = file_
    private var appCnfData: AppConfigData

    init {

        if (file_.exists()) {
            appCnfData = appCnfMapper.readValue(file_)
        }
        else {
            appCnfData = AppConfigData(0, "default")
            Save()
        }
    }

    fun Save() {
        appCnfMapper.writeValue(file, appCnfData)
    }

    fun Backup() {
        val destdir = "/data/data/com.cormabara.simpleapp/files/"
        if (file.exists())
            file.copyTo(File(destdir,file.name),true)
    }
}

