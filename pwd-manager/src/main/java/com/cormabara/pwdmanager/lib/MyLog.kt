package com.cormabara.pwdmanager.lib

import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.cormabara.pwdmanager.MainActivity
import io.getstream.log.CompositeStreamLogger
import io.getstream.log.Priority
import io.getstream.log.StreamLog
import io.getstream.log.kotlin.KotlinStreamLogger
import io.getstream.log.streamLog
import io.getstream.logging.file.FileStreamLogger
import mu.KotlinLogging
import java.io.File
import java.time.LocalDateTime
import java.time.LocalDateTime.*


object MyLog {

    @RequiresApi(Build.VERSION_CODES.O)
    fun logOpen(context_: Context) {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (path != null) {
            if (!path.exists()) {
                path.mkdirs()
            }
        }
        val fileLoggerConfig = FileStreamLogger.Config(
            filesDir = path!!, // an internal file directory
            externalFilesDir = path, // an external file directory. This is an optional.
            app = FileStreamLogger.Config.App( // application information.
                versionCode = 1,
                versionName = "1.0.0"
            ),
            device = FileStreamLogger.Config.Device( // device information
                model = "%s %s".format(Build.MANUFACTURER, Build.DEVICE),
                androidApiLevel = Build.VERSION.SDK_INT
            )
        )
        val fileLogger = FileStreamLogger(fileLoggerConfig)
        val kotlinLogger = KotlinStreamLogger()
        val compositeLogger = CompositeStreamLogger(kotlinLogger, fileLogger)

        StreamLog.install(compositeLogger)
        StreamLog.install(KotlinStreamLogger())
        StreamLog.setValidator { priority, _ ->
            priority.level >= Priority.VERBOSE.level
        }
        logInfo("This is the log : ")
        logInfo("Log path: $path")
        logInfo("Device info:"
                + " \n manufacturer: " + Build.MANUFACTURER
                + " \n model: " + Build.MODEL
                + " \n version: " + Build.VERSION.SDK_INT
                + " \n versionRelease: " + Build.VERSION.RELEASE)
    }

    fun logInfo(str_: String) {
        streamLog(priority = Priority.INFO) { str_ }
        // logger.info { str_ }
        // Log.i("MyLogI",str_)
    }
    fun logWarn(str_: String) {
        streamLog(priority = Priority.WARN) { str_ }
        // logger.warn { str_ }
        // Log.w("MyLogW",str_)
    }
    fun logError(str_: String) {
        streamLog(priority = Priority.ERROR) { str_ }
        // logger.error { str_ }
        // Log.e("MyLogE",str_)
    }
    fun logDebug(str_: String) {
        streamLog(priority = Priority.DEBUG) { str_ }
        // logger.error { str_ }
        // Log.d("MyLogD",str_)
    }
}
