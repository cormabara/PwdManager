package com.cormabara.pwdmanager.gui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.gui.lib.PasswordControl
import com.cormabara.pwdmanager.lib.MyLog


enum class PasswordMode {
    PWD_INSERT,PWD_START, PWD_CHANGE
}

fun passwordDialog(context: Context,mode_: PasswordMode, listener: (r : Boolean, txt_: String) -> Unit) {

    var onResponse: (r : Boolean, txt_: String) -> Unit = listener

    val pwd_dialog = Dialog(context, (context as MainActivity).theme.resources.hashCode())
    pwd_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

    val window: Window? = pwd_dialog.getWindow()
    val wlp = window?.attributes
    if (wlp != null) {
        wlp.gravity = Gravity.TOP
    }
    pwd_dialog.setCancelable(false)
    pwd_dialog.setContentView(R.layout.dialog_password)

    val pwdIns = pwd_dialog.findViewById<PasswordControl>(R.id.ctrl_insert_password)
    val pwdInsTitle = pwdIns.findViewById<TextView>(R.id.title_pwd)
    val pwdInsVal = pwdIns.findViewById<EditText>(R.id.txt_pwd)
    pwdInsVal.onFocusChangeListener = View.OnFocusChangeListener { _ ,focusv_ ->
        if (!focusv_) {
            if (mode_ == PasswordMode.PWD_INSERT) {
                (context).mainPassword = pwdIns.password
                if (context.manPwdData.load(context, pwdIns.password)) {
                    onResponse(true, pwdIns.password)
                    pwd_dialog.dismiss()
                } else {
                    MyLog.logInfo("Wrong password ($pwdIns.password)")
                    msgDialog(context, MsgType.MSG_ERROR, "Wrong password")
                    onResponse(false, pwdIns.password)
                    pwd_dialog.dismiss()
                }
            }
        }
    }


        pwdInsTitle.setText(R.string.insert_password)
        val pwdCnf = pwd_dialog.findViewById<PasswordControl>(R.id.ctrl_confirm_password)
        val pwdCnfTitle = pwdCnf.findViewById<TextView>(R.id.title_pwd)
        val pwdCnfVal = pwdCnf.findViewById<EditText>(R.id.txt_pwd)
        pwdCnfTitle.setText(R.string.confirm_password)
        pwdCnf.isVisible = (mode_ != PasswordMode.PWD_INSERT)

        fun checkPasswords(pwd1_: String, pwd2_: String): Boolean {
            if (pwd1_ != pwd2_) {
                msgDialog(context, MsgType.MSG_ERROR, "The two password are different")
                return false
            }
            return true
        }

        pwd_dialog.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                MyLog.logInfo("Back key pressed exiting from dialog")
                onResponse(false, "")
            }
            false
        }

        val btnSavePwd = pwd_dialog.findViewById<ImageButton>(R.id.btn_confirm_password)

        // The save button behaviour depends on the mode
        when (mode_) {
            PasswordMode.PWD_INSERT -> {
                btnSavePwd.setOnClickListener {
                    (context).mainPassword = pwdIns.password
                    if ((context).manPwdData.load(context, pwdIns.password)) {
                        onResponse(true, pwdIns.password)
                        pwd_dialog.dismiss()
                    } else {
                        MyLog.logInfo("Wrong password ($pwdIns.password)")
                        msgDialog(context, MsgType.MSG_ERROR, "Wrong password")
                        onResponse(false, pwdIns.password)
                        pwd_dialog.dismiss()
                    }
                }
            }
            PasswordMode.PWD_CHANGE -> {
                btnSavePwd.setOnClickListener {
                    if (checkPasswords(pwdIns.password, pwdCnf.password)) {
                        (context).mainPassword = pwdIns.password
                        onResponse(true, pwdIns.password)
                        pwd_dialog.dismiss()
                    }
                }
            }
            PasswordMode.PWD_START -> {
                btnSavePwd.setOnClickListener {
                    if (checkPasswords(pwdIns.password, pwdCnf.password)) {
                        (context).mainPassword = pwdIns.password
                        onResponse(true, pwdIns.password)
                        pwd_dialog.dismiss()
                    }
                }
            }
        }
        pwd_dialog.show()
    }