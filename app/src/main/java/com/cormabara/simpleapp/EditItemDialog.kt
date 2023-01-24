package com.cormabara.simpleapp

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.cormabara.simpleapp.data.PwdGroup
import com.cormabara.simpleapp.data.PwdItem

fun editItemDialog(context: Context,adapter_ :PwdItemAdapter , item: PwdItem) {
    val dialog = Dialog(context)
    val pwdCnfFile = (context as MainActivity).pwdCnfFile
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_edit_item)

    var txt_name: EditText = dialog.findViewById(R.id.txt_name)
    txt_name.setText(item.name)

    var txt_password: EditText = dialog.findViewById(R.id.txt_password)
    txt_password.setText(item.password)

    var txt_username: EditText = dialog.findViewById(R.id.txt_username)
    txt_username.setText(item.username)
/*
    class GrpArrayAdapter2(ctx: Context,moods: List<PwdGroup>) : ArrayAdapter<PwdGroup>(ctx, 0, moods) {

        override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
            return this.createView(position, recycledView, parent)
        }

        override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
            return this.createView(position, recycledView, parent)
        }

        private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {

            val mood = getItem(position)

            val view = recycledView ?: LayoutInflater.from(context).inflate(
                R.layout.demo_spinner,
                parent,
                false
            )

            view.moodImage.setImageResource(mood.image)
            view.moodText.text = mood.description

            return view
        }
    }
*/
    class GrpArrayAdapter(context: Context, private val arrayList: ArrayList<PwdGroup>) : ArrayAdapter<PwdGroup>(context,android.R.layout.simple_spinner_item,arrayList) {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return createViewFromResource(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
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

    val btnok = dialog.findViewById(R.id.btn_ok) as Button
    val btncancel = dialog.findViewById(R.id.btn_cancel) as Button
    btnok.setOnClickListener {
        item.setPwdName(txt_name.text.toString())
        item.setPwdPassword(txt_password.text.toString())
        item.setPwdUsername(txt_username.text.toString())
        item.setPwdGroup(spn_group.selectedItem.toString())
        dialog.dismiss()
        adapter_.notifyDataSetChanged()
    }
    btncancel.setOnClickListener { dialog.dismiss() }
    dialog.show()
}
