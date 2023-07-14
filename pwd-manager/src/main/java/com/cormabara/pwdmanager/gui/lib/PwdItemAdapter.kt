package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.editItemDialog
import com.cormabara.pwdmanager.managers.ManAppConfig
import com.cormabara.pwdmanager.managers.ManPwdData
import java.util.*


class PwdItemAdapter(private val context_: Context, @LayoutRes private val layoutResource: Int, private val arrayList_: java.util.ArrayList<ManPwdData.PwdItem>):
    ArrayAdapter<ManPwdData.PwdItem>(context_,layoutResource,arrayList_),
    Filterable {

    private lateinit var id: TextView
    private lateinit var name: TextView
    private lateinit var username: TextView
    private lateinit var password: TextView
    private var tagFilter: String = ""
    private var strFilter: String = ""
    // Create a copy of the original array
    private var originalItemList = ArrayList(arrayList_)
    private var operativeItemList = arrayList_

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView =  inflater.inflate(R.layout.listview_item, parent, false) as LinearLayout
        name = rowView.findViewById(R.id.pwd_item_name) as TextView
        username = rowView.findViewById(R.id.pwd_item_username) as TextView
        password = rowView.findViewById(R.id.pwd_item_password) as TextView

        val myItem = getItem(position)
        name.text = myItem.name
        username.text = myItem.username
        password.text = myItem.password

        return rowView
    }

    fun getTagFilter(): String {
        return tagFilter
    }
    fun setTagFilter(val_: String) {
        tagFilter = val_
    }
    fun getStrFilter(): String {
        return strFilter
    }
    fun setStrFilter(val_: String) {
        strFilter = val_
    }

    override fun getCount(): Int {
        return operativeItemList.size
    }
    override fun getItem(position: Int): ManPwdData.PwdItem {
        return operativeItemList[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint_: CharSequence?): FilterResults? {
                strFilter = constraint_.toString()
                val results = FilterResults() // Holds the results of a filtering operation in values

                originalItemList = ArrayList(arrayList_) // saves the original data in mOriginalValues
                var tagItemList = ArrayList(arrayList_)
                val filteredArrList: MutableList<ManPwdData.PwdItem> = ArrayList()
                filteredArrList.clear()
                tagItemList.clear()

                if (tagFilter.isNotEmpty()) {
                    for (it in originalItemList) {
                        if (it.hasTag(tagFilter))
                            tagItemList.add(it)
                    }
                }
                else {
                    tagItemList.addAll(originalItemList)
                }

                if (constraint_ == null || constraint_.length == 0) {

                    // set the Original result to return
                    results.count = tagItemList.size
                    results.values = tagItemList
                }
                else {
                    val constraint = strFilter
                    for (it in tagItemList) {
                        val data: String = it.name
                        // The filter depends on the configuration search mode
                        var result: Boolean = if ( (context_ as MainActivity).manAppConfig.searchMode == ManAppConfig.SearchMode.CONTAINS ) {
                            data.lowercase(Locale.getDefault()).contains(constraint,true)
                        } else {
                            data.lowercase(Locale.getDefault()).startsWith(constraint,true)
                        }

                        if (result) {
                            filteredArrList.add(it)
                        }
                    }
                    // set the Filtered result to return
                    results.count = filteredArrList.size
                    results.values = filteredArrList
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                operativeItemList = results.values as ArrayList<ManPwdData.PwdItem>
                if (results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }

    // Add a new item into the list
    fun addNewItem()
    {
        editItemDialog(context,this,null)
    }
    fun changeData(datas: ArrayList<ManPwdData.PwdItem>) {
        originalItemList = datas
        operativeItemList = datas
        notifyDataSetChanged()
    }}
