package com.cormabara.pwdmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.cormabara.pwdmanager.data.PwdGroup


//Class MyAdapter
class PwdGroupAdapter(private val context: Context, private val arrayList: java.util.ArrayList<PwdGroup>) : BaseAdapter() {
    private lateinit var id: TextView
    private lateinit var name: TextView
    private lateinit var btn_delete: ImageButton

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        //val rowView_old = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false)

        val rowView =  inflater.inflate(R.layout.listview_group, parent, false) as LinearLayout

        name = rowView.findViewById(R.id.pwd_group_name) as TextView
        btn_delete = rowView.findViewById(R.id.btn_delete)
        val myItem = getItem(position) as PwdGroup
        name.text = myItem.name

        btn_delete.setOnClickListener {
            Toast.makeText(context, "Delete button", Toast.LENGTH_SHORT).show()
            val element = getItem(position)
            arrayList.remove(element)
            notifyDataSetChanged()
        }
        val btnEdit = rowView.findViewById(R.id.btn_edit_group) as ImageButton
        btnEdit.setOnClickListener {
            val selectedItem = getItem(position) as PwdGroup
            editGroupDialog(context,this,selectedItem)
        }
        return rowView
    }

    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): Any {
        return arrayList[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
