package com.cormabara.simpleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.cormabara.simpleapp.data.PwdCnfFile
import com.cormabara.simpleapp.data.PwdItem


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
            this.notifyDataSetChanged()
        }

        val btnEdit = rowView.findViewById(R.id.btn_edit_group) as ImageButton
        btnEdit.setOnClickListener {
            val selectedItem = getItem(position) as PwdItem
            editItemDialog(context,this,selectedItem)
            this.notifyDataSetChanged()
        }
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
}
