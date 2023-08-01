package com.cormabara.pwdmanager.gui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.BuildConfig

fun aboutDialog(context: Context) {

    val dialog = Dialog(context,(context as MainActivity).theme.resources.hashCode())
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_about)

    // Inflate the layout for this fragment
    context.showUpButton()
    val buildCode = BuildConfig.VERSION_CODE
    val buildName = BuildConfig.VERSION_NAME
    val pkgName = context.applicationContext.packageName

    val aboutTitle = dialog.findViewById<TextView>(R.id.about_title)
    aboutTitle.text = context.getString(R.string.app_title)

    var appinfo = "App: $pkgName - Version: $buildName"
    val appInfo = dialog.findViewById<TextView>(R.id.about_appinfo)
    appInfo.text = appinfo

    var str = "App: $pkgName \n"
    str += "BuildCode: $buildCode\n"
    str += "Github: https://github.com/cormabara/PwdManager\n"
    val aboutInfo = dialog.findViewById<TextView>(R.id.about_info)
    aboutInfo.text = str

    val btnExit = dialog.findViewById<ImageButton>(R.id.btn_exit_about)
    btnExit.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}