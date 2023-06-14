package com.cormabara.pwdmanager.gui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.databinding.FragmentMainBinding
import com.cormabara.pwdmanager.editItemDialog
import com.cormabara.pwdmanager.gui.lib.PwdItemAdapter
import com.cormabara.pwdmanager.lib.MyLog
import com.cormabara.pwdmanager.managers.ManPwdData


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var itemsAdapter: PwdItemAdapter? = null

    private lateinit var manPwdData: ManPwdData
    private lateinit var myActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        MyLog.logDebug("MainFragment on create view")
        myActivity = (context as MainActivity)
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        // init password data manager
        manPwdData = (context as MainActivity).manPwdData

        // Init the item list view
        itemsAdapter = PwdItemAdapter(context as MainActivity, R.layout.listview_item,manPwdData.listPwdItems())
        binding.itemsList.adapter = itemsAdapter
        binding.itemsList.onItemClickListener = OnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as ManPwdData.PwdItem
            Toast.makeText(context, "Click on item $selectedItem", Toast.LENGTH_SHORT).show()
        }
        binding.itemsList.setLongClickable(true);

        binding.itemsList.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { _, _, pos, _ -> // TODO Auto-generated method stub
            MyLog.logInfo("long clicked pos: $pos")
            val selectedItem = itemsAdapter!!.getItem(pos)
            editItemDialog(context as MainActivity, itemsAdapter!!,selectedItem)
            itemsAdapter!!.notifyDataSetChanged()
            true
        })

        (activity as MainActivity).hideUpButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button to add an item to List
        val btnAddItem = myActivity.findViewById<ImageButton>(R.id.btn_add_item)
        btnAddItem?.setOnClickListener {
            itemsAdapter!!.addNewItem()
            itemsAdapter!!.notifyDataSetChanged()
        }

        val searchFilter: SearchView = myActivity.findViewById(R.id.search_filter)
        searchFilter.queryHint = getString(R.string.search_item_by_name)

        searchFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                itemsAdapter!!.filter.filter(p0)
                searchFilter.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                itemsAdapter!!.filter.filter(p0)
                return false
            }
        })
        searchFilter.clearFocus()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        MyLog.logDebug("MainFragment on resume")
        itemsAdapter!!.notifyDataSetChanged()
        //reloadfrag()
    }

    fun reloadData() {
        MyLog.logInfo("mainfragment reload data")
        itemsAdapter?.changeData(manPwdData.listPwdItems())
    }

    fun reloadfrag() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.detach(this).attach(this).commit()
    }
}
