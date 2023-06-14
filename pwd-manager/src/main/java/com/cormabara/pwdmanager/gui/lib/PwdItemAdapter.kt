package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.annotation.LayoutRes
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.editItemDialog
import com.cormabara.pwdmanager.gui.dialogs.ChooseDialog
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
                var constraint = constraint_
                val results = FilterResults() // Holds the results of a filtering operation in values
                val FilteredArrList: MutableList<ManPwdData.PwdItem> = ArrayList()

                originalItemList = ArrayList(arrayList_) // saves the original data in mOriginalValues

                if (constraint == null || constraint.length == 0) {

                    // set the Original result to return
                    results.count = originalItemList.size
                    results.values = originalItemList
                }
                else {
                    constraint = constraint.toString().lowercase(Locale.getDefault())
                    for (i in 0 until originalItemList.size) {
                        val data: String = originalItemList.get(i).name
                        // The filter depends on the configuration search mode
                        var result: Boolean = if ( (context_ as MainActivity).manAppConfig.searchMode == ManAppConfig.SearchMode.CONTAINS ) {
                            data.lowercase(Locale.getDefault()).contains(constraint.toString())
                        } else {
                            data.lowercase(Locale.getDefault()).startsWith(constraint.toString(),true)
                        }

                        if (result) {
                            FilteredArrList.add(originalItemList[i])
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size
                    results.values = FilteredArrList
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
