package com.cormabara.pwdmanager

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cormabara.pwdmanager.data.PwdGroup

fun editGroupDialog(context: Context, adapter_ : PwdGroupAdapter, group: PwdGroup) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_edit_group)

    var txtname: EditText = dialog.findViewById(R.id.txt_name)
    txtname.setText(group.name)
    dialog.findViewById<TextView>(R.id.txt_grp_title).text = "Group informations"
    val btnok = dialog.findViewById(R.id.btn_ok) as Button
    val btncancel = dialog.findViewById(R.id.btn_cancel) as Button
    btnok.setOnClickListener {
        group.setPwdName(txtname.text.toString())
        dialog.dismiss()
        adapter_.notifyDataSetChanged()
    }
    btncancel.setOnClickListener { dialog.dismiss() }
    dialog.show()
}
