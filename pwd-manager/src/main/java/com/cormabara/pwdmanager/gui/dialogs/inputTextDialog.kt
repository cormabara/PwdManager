package com.cormabara.pwdmanager.gui.dialogs

import android.content.Context
import android.text.InputType

import android.widget.EditText


fun inputTextDialog(context_: Context, title_: String, listener: (r : Boolean, txt_: String) -> Unit)
{
    var onResponse: (r : Boolean, txt_: String) -> Unit = listener
    val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context_)
    builder.setTitle(title_)
    val input = EditText(context_)
    input.inputType = InputType.TYPE_CLASS_TEXT
    builder.setView(input)

    // performing positive action
    builder.setPositiveButton("Yes") { _, _ ->
        onResponse(true, input.text.toString())
    }

    // performing negative action
    builder.setNegativeButton("No") { _, _ ->
        onResponse(false, "")
    }

    builder.show()
}
