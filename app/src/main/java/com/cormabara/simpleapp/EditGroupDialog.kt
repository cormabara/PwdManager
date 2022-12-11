package com.cormabara.simpleapp

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

fun editGroupDialog(context: Context, title: String, group: PwdGroup) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_edit_group)

    var txt_name: EditText = dialog.findViewById(R.id.txt_name)
    txt_name.setText(group.name)
    dialog.findViewById<TextView>(R.id.txt_grp_title).text = title
    val btn_ok = dialog.findViewById(R.id.btn_ok) as Button
    val btn_cancel = dialog.findViewById(R.id.btn_cancel) as Button
    btn_ok.setOnClickListener {
        group.setPwdName(txt_name.text.toString())
        dialog.dismiss()
    }
    btn_cancel.setOnClickListener { dialog.dismiss() }
    dialog.show()
}
