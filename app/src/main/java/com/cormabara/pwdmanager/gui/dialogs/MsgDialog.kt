package com.cormabara.pwdmanager.gui.dialogs

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun MsgDialog(context_: Context, title_: String, msg_: String) {
    val alertDialogBuilder = AlertDialog.Builder(context_)
    alertDialogBuilder.setTitle(title_)
    alertDialogBuilder.setMessage(msg_)
    alertDialogBuilder.setNegativeButton("OK", { dialogInterface: DialogInterface, i: Int -> })
    var alertDialog = alertDialogBuilder.create()
}