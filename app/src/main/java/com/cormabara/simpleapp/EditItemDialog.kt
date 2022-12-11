package com.cormabara.simpleapp

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*

fun editItemDialog(context: Context, title: String, item: PwdItem) {
    val dialog = Dialog(context)
    val pwdCnfFile = (context as MainActivity).pwdCnfFile
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_edit_item)

    dialog.findViewById<TextView>(R.id.txt_item_title).text = title
    var txt_name: EditText = dialog.findViewById(R.id.txt_name)
    txt_name.setText(item.name)

    var txt_password: EditText = dialog.findViewById(R.id.txt_password)
    txt_password.setText(item.password)

    var txt_username: EditText = dialog.findViewById(R.id.txt_username)
    txt_username.setText(item.username)

    class GrpArrayAdapter(context: Context, private val arrayList: ArrayList<PwdGroup>) : ArrayAdapter<PwdGroup>(context,android.R.layout.simple_spinner_item,arrayList) {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return createViewFromResource(position, convertView, parent)
        }

        private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false) as TextView
            view.setText(arrayList[position].name)
            return view
        }
    }
    var spn_group: Spinner = dialog.findViewById(R.id.spn_group)
    var adapter = GrpArrayAdapter(context,pwdCnfFile.GroupList())
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spn_group.adapter = adapter
    val pos = adapter.getPosition(pwdCnfFile.FindGroupById(item.groupId))
    spn_group.setSelection(pos)

    val btn_ok = dialog.findViewById(R.id.btn_ok) as Button
    val btn_cancel = dialog.findViewById(R.id.btn_cancel) as Button
    btn_ok.setOnClickListener {
        item.setPwdName(txt_name.text.toString())
        item.setPwdPassword(txt_password.text.toString())
        item.setPwdUsername(txt_username.text.toString())
        item.setPwdGroup(spn_group.selectedItem.toString())
        dialog.dismiss()
    }
    btn_cancel.setOnClickListener { dialog.dismiss() }
    dialog.show()
}
