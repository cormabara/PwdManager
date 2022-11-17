package com.cormabara.simpleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.cormabara.simpleapp.PwdItem
import com.cormabara.simpleapp.R


//Class MyAdapter
class PwdGroupAdapter(private val context: Context, private val arrayList: java.util.ArrayList<PwdGroup>) : BaseAdapter() {
    private lateinit var id: TextView
    private lateinit var name: TextView
    private lateinit var btn_delete: ImageButton

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        //val rowView_old = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false)

        val rowView =  inflater.inflate(R.layout.listview_group, parent, false) as LinearLayout

        id = rowView.findViewById(R.id.pwd_group_id) as TextView
        name = rowView.findViewById(R.id.pwd_group_name) as TextView
        btn_delete = rowView.findViewById(R.id.btn_delete)
        val myItem = getItem(position) as PwdGroup
        id.text = myItem.id
        name.text = myItem.name

        btn_delete.setOnClickListener {
            Toast.makeText(context, "Delete button", Toast.LENGTH_SHORT).show()
            val element = getItem(position)
            arrayList.remove(element)
            notifyDataSetChanged()
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
