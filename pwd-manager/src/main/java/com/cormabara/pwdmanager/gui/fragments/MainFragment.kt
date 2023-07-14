package com.cormabara.pwdmanager.gui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.widget.PopupMenu
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
        binding.itemsList.divider = null;
        binding.itemsList.dividerHeight = 10;
        binding.itemsList.setPadding(10,10,10,10);

        binding.itemsList.onItemClickListener = OnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as ManPwdData.PwdItem
            MyLog.logInfo("Click on item $selectedItem")
        }
        binding.itemsList.setLongClickable(true);

        binding.itemsList.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { _, _, pos, _ -> // TODO Auto-generated method stub
            MyLog.logInfo("long clicked pos: $pos")
            val selectedItem = itemsAdapter!!.getItem(pos)
            editItemDialog(context as MainActivity, itemsAdapter!!,selectedItem)
            itemsAdapter!!.notifyDataSetChanged()
            true
        })
        createTagsPopMenu(binding.btnTagFilter)

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
                itemsAdapter!!.setStrFilter(p0!!)
                itemsAdapter!!.filter.filter(itemsAdapter!!.getStrFilter())
                searchFilter.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                itemsAdapter!!.setStrFilter(p0!!)
                itemsAdapter!!.filter.filter(itemsAdapter!!.getStrFilter())
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

    fun createTagsPopMenu(btn_: ImageButton) {
        // Setting onClick behavior to the button
        btn_.setOnClickListener {
            // Initializing the popup menu and giving the reference as current context
            val popupMenu = PopupMenu(context as MainActivity, btn_)

            // Inflating popup menu from popup_menu.xml file
            popupMenu.menuInflater.inflate(R.menu.tag_popup_menu, popupMenu.menu)
            for (tag in myActivity.manPwdData.getTags()) {
                var it = popupMenu.menu.add(tag)
                it.isCheckable = true
                it.isChecked = itemsAdapter?.getTagFilter() == tag;
            }
            popupMenu.setOnMenuItemClickListener { menuItem ->
                menuItem.isChecked = !menuItem.isChecked
                if (menuItem.isChecked)
                    itemsAdapter?.setTagFilter(menuItem.title.toString())
                else
                    itemsAdapter?.setTagFilter("")

                MyLog.logInfo("You Clicked " + menuItem.title + " and checked is: " + menuItem.isChecked)
                itemsAdapter!!.filter.filter(itemsAdapter?.getStrFilter())
                true
            }
            // Showing the popup menu
            popupMenu.show()
        }
    }
}
