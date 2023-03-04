package com.cormabara.pwdmanager.gui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.PwdItemAdapter
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.managers.ManPwdData
import com.cormabara.pwdmanager.databinding.FragmentMainBinding


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

        myActivity = (context as MainActivity)
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        // init password data manager
        manPwdData = (context as MainActivity).manPwdData

        // Init the item list view
        itemsAdapter = PwdItemAdapter(context as MainActivity, R.layout.listview_item,manPwdData.listPwdItems())
        binding.itemsList.adapter = itemsAdapter
        binding.itemsList.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as ManPwdData.PwdItem
            Toast.makeText(context, "Click on item $selectedItem", Toast.LENGTH_SHORT).show()
        }
        (activity as MainActivity).hideUpButton();

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

        val searchFilter: SearchView = myActivity.findViewById<ImageButton>(R.id.search_filter) as SearchView
        searchFilter.setQueryHint(getString(R.string.search_item_by_name));

        searchFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                itemsAdapter!!.filter.filter(p0)
                searchFilter.clearFocus();
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                itemsAdapter!!.filter.filter(p0)
                return false
            }
        })
        searchFilter.clearFocus();

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        itemsAdapter!!.notifyDataSetChanged()
    }
}
