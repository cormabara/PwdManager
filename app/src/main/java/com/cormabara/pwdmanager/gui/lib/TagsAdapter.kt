package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.cormabara.pwdmanager.CheckTag
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.managers.ManPwdData

class TagListAdapter(private val dataSet: ArrayList<CheckTag>, private val pwdItem: ManPwdData.PwdItem, mContext: Context) :
    ArrayAdapter<CheckTag>(mContext, R.layout.listview_tag,dataSet) {

    private class ViewHolder {
        lateinit var txtName: TextView
        lateinit var checkBox: CheckBox
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getItem(position: Int): CheckTag {
        return dataSet[position] as CheckTag
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup ): View
    {
        var convertView = convertView
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.listview_tag, parent, false)
            viewHolder.txtName = convertView.findViewById(R.id.txtName)
            viewHolder.checkBox = convertView.findViewById(R.id.checkBox)
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }

        val item: CheckTag = getItem(position)
        viewHolder.txtName.text = item.name
        viewHolder.checkBox.isChecked = pwdItem.hasTag(item.name)

        viewHolder.checkBox.setOnCheckedChangeListener { btn_, isChecked ->
            if (isChecked)
                pwdItem.addTag(btn_.text.toString())
            else
                pwdItem.delTag(btn_.text.toString())
        }
        return result
    }
}