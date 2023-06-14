package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.managers.ManPwdData

class TagListAdapter(private val dataSet: ArrayList<String>, private val pwdItem: ManPwdData.PwdItem, mContext: Context) :
    ArrayAdapter<String>(mContext, R.layout.listview_tag,dataSet) {

    private class ViewHolder {
        lateinit var checkBox: CheckBox
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getItem(position: Int): String {
        return dataSet[position]
    }

    override fun getView(position: Int, convertView_: View?, parent: ViewGroup ): View
    {
        var convertView = convertView_
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.listview_tag, parent, false)
            viewHolder.checkBox = convertView.findViewById(R.id.checkBox)
            viewHolder.checkBox.setOnCheckedChangeListener(null)
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }

        val item: String = getItem(position)
        viewHolder.checkBox.text = item
        viewHolder.checkBox.isChecked = pwdItem.hasTag(item)

        viewHolder.checkBox.setOnCheckedChangeListener { btn_, _ ->
            if (btn_.isChecked)
                pwdItem.addTag((btn_ as CheckBox).text.toString())
            else
                pwdItem.delTag(btn_.text.toString())
        }
        return result
    }

}