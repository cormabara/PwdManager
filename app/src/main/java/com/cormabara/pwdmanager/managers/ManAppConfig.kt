package com.cormabara.pwdmanager.managers

import com.cormabara.pwdmanager.MyLog
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
                MyLog.LError("Exception loading the data file")
                appData = AppConfigData()
            }
        }
        else {
            appData = AppConfigData()
        }
        saveData()
        MyLog.LInfo("Data info: " + appCnfMapper.writeValueAsString(appData))
    }

    fun saveData() {
        MyLog.LInfo(appCnfMapper.writeValueAsString(appData))
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
}

