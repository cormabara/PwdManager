package com.cormabara.pwdmanager.gui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.*
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.gui.lib.BinaryOption
import com.cormabara.pwdmanager.gui.lib.ListOption
import com.cormabara.pwdmanager.lib.MyLog
import com.cormabara.pwdmanager.managers.ManAppConfig
import com.google.android.gms.common.util.WorkSourceUtil.getNames

fun settingsDialog(context: Context) {

    val dialog = Dialog(context,(context as MainActivity).theme.resources.hashCode())
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_settings)

    val cnf = (context as MainActivity).manAppConfig

    val themeOption = dialog.findViewById<BinaryOption>(R.id.theme_option)
    themeOption.configure(context.getString(R.string.setting_gui_theme),
        ManAppConfig.GuiTheme.DARK.toString(),
        ManAppConfig.GuiTheme.LIGHT.toString())
    themeOption.setActive(cnf.guiTheme.toString())

    val findOption = dialog.findViewById<BinaryOption>(R.id.find_option)
    findOption.configure(context.getString(R.string.setting_find_mode),
        ManAppConfig.SearchMode.CONTAINS.toString(),
        ManAppConfig.SearchMode.START_WITH.toString())
    findOption.setActive(cnf.searchMode.toString())

    val langOption = dialog.findViewById<ListOption>(R.id.language_option)
    val names = ManAppConfig.Languages::class.java.enumConstants.map { it.name }
    langOption.configure(context.getString(R.string.setting_language),names.toTypedArray())
    langOption.setSelected(cnf.language.toString())

    val userEmail = dialog.findViewById<TextView>(R.id.user_email)
    userEmail.setText(cnf.userMail)

    val btnSaveSettings = dialog.findViewById<ImageButton>(R.id.btn_save_settings)
    btnSaveSettings.setOnClickListener {
        cnf.guiTheme  = enumValueOf(themeOption.getActive())
        cnf.searchMode = enumValueOf(findOption.getActive())
        cnf.language = enumValueOf(langOption.getSelected())
        cnf.userMail = userEmail.text.toString()
        cnf.saveData()
        (context).setAppLocale(cnf.language.toString())
        (context).application.setTheme(
            if (cnf.guiTheme == ManAppConfig.GuiTheme.DARK) R.style.Theme_PwdManager_Dark
            else R.style.Theme_PwdManager_Light
        )

        MyLog.logInfo("New language ${cnf.language.toString()}")
        dialog.dismiss()
    }
    val btnExit = dialog.findViewById<ImageButton>(R.id.btn_exit_settings)
    btnExit.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}
