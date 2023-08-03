package com.cormabara.pwdmanager.gui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.EditText
import androidx.core.view.isVisible
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.gui.lib.MainPasswordControl

enum class PasswordMode {
    PWD_INSERT,PWD_START, PWD_CHANGE
}

fun passwordDialog(context: Context,mode_: PasswordMode) {

    val dialog = Dialog(context, (context as MainActivity).theme.resources.hashCode())
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_password)
    val pwdIns = dialog.findViewById<MainPasswordControl>(R.id.ctrl_insert_password)
    val pwdInsTitle = pwdIns.findViewById<EditText>(R.id.title_pwd)
    val pwdInsVal = pwdIns.findViewById<EditText>(R.id.txt_pwd)
    pwdInsTitle.setText(R.string.insert_password)
    val pwdCnf = dialog.findViewById<MainPasswordControl>(R.id.ctrl_confirm_password)
    val pwdCnfTitle = pwdCnf.findViewById<EditText>(R.id.title_pwd)
    val pwdCnfVal = pwdCnf.findViewById<EditText>(R.id.txt_pwd)
    pwdCnfTitle.setText(R.string.confirm_password)
    pwdCnf.isVisible = (mode_ != PasswordMode.PWD_INSERT)

    when (mode_) {
        PasswordMode.PWD_INSERT -> {

        }
        PasswordMode.PWD_CHANGE -> {

        }
        PasswordMode.PWD_START -> {

        }

    }

}