package com.cormabara.pwdmanager

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.*
import com.cormabara.pwdmanager.managers.ManPwdData

fun editItemDialog(context: Context,adapter_ :PwdItemAdapter , item: ManPwdData.PwdItem) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_edit_item)

    var txt_name: EditText = dialog.findViewById(R.id.txt_name)
    txt_name.setText(item.name)

    var txt_password: EditText = dialog.findViewById(R.id.txt_password)
    txt_password.setText(item.password)

    var txt_username: EditText = dialog.findViewById(R.id.txt_username)
    txt_username.setText(item.username)

    val btnok = dialog.findViewById(R.id.btn_ok) as Button
    val btncancel = dialog.findViewById(R.id.btn_cancel) as Button
    btnok.setOnClickListener {
        item.setPwdName(txt_name.text.toString())
        item.setPwdPassword(txt_password.text.toString())
        item.setPwdUsername(txt_username.text.toString())
        //item.setPwdGroup(spn_group.selectedItem.toString())
        dialog.dismiss()
        adapter_.notifyDataSetChanged()
    }
    btncancel.setOnClickListener { dialog.dismiss() }
    dialog.show()
}
