package com.cormabara.pwdmanager.managers

import android.content.Context
import android.net.Uri
import com.cormabara.pwdmanager.lib.MyLog
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class ManAppConfig(path_: File) {

    enum class Languages(val type: String) {
        IT("It"),
        EN("En"),
    }

    enum class SearchMode {
        START_WITH, CONTAINS
    }
    enum class GuiTheme {
        DARK, LIGHT
    }

    class AppConfigData() {
        var language: Languages = Languages.IT
        var searchMode: SearchMode = SearchMode.CONTAINS
        var guiTheme: GuiTheme = GuiTheme.DARK
        var mail: String = "usermail@mail.com"
    }
    private val configFileName = "app_config.json"
    private val appCnfMapper = jacksonObjectMapper()
    private var appData: AppConfigData
    private val configFile: File

    init {
        configFile = File(path_,configFileName)
        if (configFile.exists()) {
            try {
                appData = appCnfMapper.readValue(configFile)
            } catch (e: Exception) {
                MyLog.logError("Exception loading data on start")
                appData = AppConfigData()
            }
        }
        else {
            appData = AppConfigData()
        }
        saveData()
        MyLog.logInfo("Data info: " + appCnfMapper.writeValueAsString(appData))
    }

    fun saveData() {
        MyLog.logInfo(appCnfMapper.writeValueAsString(appData))
        appCnfMapper.writeValue(configFile, appData)
    }

    fun backupData(): String {
        val myStr = appCnfMapper.writeValueAsString(appData)
        return myStr
    }

    var searchMode: SearchMode
        get() = appData.searchMode
        set(value) {appData.searchMode = value}
    var guiTheme: GuiTheme
        get() = appData.guiTheme
        set(value) {appData.guiTheme = value}
    var language: Languages
        get() = appData.language
        set(value) {appData.language = value}
    var userMail: String
        get() = appData.mail
        set(value) {appData.mail = value}

    fun exportData(context_: Context, uri_: Uri)
    {
        try {
            MyLog.logInfo("Exporting data")
            //val selectedFilePath: String? = MyFileUtils.getPath(context_, uri_)
            //MyLog.LInfo("File Uri: $selectedFilePath")
            //val file = selectedFilePath?.let { File(it) }
            MyLog.logInfo("appData: $appData")
            //if (file != null) {
            //    file.writeBytes(appData.toString().toByteArray())
            //}
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

