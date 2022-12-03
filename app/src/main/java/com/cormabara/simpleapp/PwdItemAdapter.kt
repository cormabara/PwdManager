package com.cormabara.simpleapp

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*


//Class MyAdapter
class PwdItemAdapter(private val context: Context, private val arrayList: java.util.ArrayList<PwdItem>) : BaseAdapter() {
    private lateinit var id: TextView
    private lateinit var name: TextView
    private lateinit var username: TextView
    private lateinit var password: TextView

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val pwdCnfFile: PwdCnfFile = (context as MainActivity).pwdCnfFile

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val rowView =  inflater.inflate(R.layout.listview_item, parent, false) as LinearLayout
        id = rowView.findViewById(R.id.pwd_item_id) as TextView
        name = rowView.findViewById(R.id.pwd_item_name) as TextView
        username = rowView.findViewById(R.id.pwd_item_username) as TextView
        password = rowView.findViewById(R.id.pwd_item_password) as TextView

        val myItem = getItem(position) as PwdItem
        id.text = myItem.id
        name.text = myItem.name
        username.text = myItem.username
        password.text = myItem.password

        var btn_delete = rowView.findViewById(R.id.btn_delete) as ImageButton
        btn_delete.setOnClickListener {
            Toast.makeText(context, "Delete button", Toast.LENGTH_SHORT).show()
            val element = getItem(position)
            arrayList.remove(element)
            notifyDataSetChanged()
        }

        val btnEdit = rowView.findViewById(R.id.btn_edit) as ImageButton
        btnEdit.setOnClickListener {
            val selectedItem = getItem(position) as PwdItem
            showEditDialog("pippo",selectedItem)
        }

        var txt_main = rowView.findViewById<TextView>(R.id.txt_username)
        return rowView
    }

    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): PwdItem {
        return arrayList[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private fun showEditDialog(title: String, item: PwdItem) {
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

        class GrpArrayAdapter(context: Context, private val arrayList: ArrayList<PwdGroup>) : ArrayAdapter<PwdGroup>(context,android.R.layout.simple_spinner_item,arrayList) {

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
                return createViewFromResource(position, convertView, parent)
            }

            private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup?): View{
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
            notifyDataSetChanged()
        }
        btn_cancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}
