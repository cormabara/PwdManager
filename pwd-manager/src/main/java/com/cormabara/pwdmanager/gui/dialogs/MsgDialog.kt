package com.cormabara.pwdmanager.gui.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.cormabara.pwdmanager.R

fun MsgDialog(context_: Context, title_: String, msg_: String) {
    val alertDialogBuilder = AlertDialog.Builder(context_)
    alertDialogBuilder.setTitle(title_)
    alertDialogBuilder.setMessage(msg_)
    alertDialogBuilder.setNegativeButton("OK", { dialogInterface: DialogInterface, i: Int -> })
    var alertDialog = alertDialogBuilder.create()
}

fun showDialog(context_: Context,message_: String) {
    val dialog = Dialog(context_)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_msg)
    val message = dialog.findViewById(R.id.dialog_msg_text) as TextView
    message.text = message_
    val body = dialog.findViewById(R.id.dialog_msg_body) as LinearLayout
    body.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()

}