package com.cormabara.pwdmanager.gui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.cormabara.pwdmanager.R

enum class MsgType {
    MSG_ERROR, MSG_WARNING, MSG_INFO
}


fun msgDialog(context_: Context, type: MsgType, message_: String) {
    val dialog = Dialog(context_)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_msg)
    val ico = dialog.findViewById<ImageView>(R.id.dialog_msg_ico)
    when (type) {
        MsgType.MSG_INFO -> {
            ico.setImageResource(R.mipmap.img_info_foreground)
        }
        MsgType.MSG_WARNING -> {
            ico.setImageResource(R.mipmap.img_warning_foreground)
        }
        MsgType.MSG_ERROR -> {
            ico.setImageResource(R.mipmap.img_error_foreground)
        }
    }
    val message = dialog.findViewById(R.id.dialog_msg_text) as TextView
    message.text = message_
    val body = dialog.findViewById(R.id.dialog_msg_body) as LinearLayout
    body.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}