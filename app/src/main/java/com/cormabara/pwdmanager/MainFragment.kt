package com.cormabara.pwdmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cormabara.pwdmanager.data.PwdCnfFile
import com.cormabara.pwdmanager.data.PwdItem
import com.cormabara.pwdmanager.databinding.FragmentMainBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: CommonViewModel

    private var itemsAdapter: PwdItemAdapter? = null
    private lateinit var itemsListView: ListView

    private lateinit var pwdCnfFile: PwdCnfFile
    private lateinit var my_activity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        my_activity = (context as MainActivity)

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        // init view model
        viewModel = ViewModelProvider(requireActivity())[CommonViewModel::class.java]
        pwdCnfFile = (context as MainActivity).pwdCnfFile
        // Init the item list view
        itemsListView = binding.idItemsList
        itemsAdapter = PwdItemAdapter(context as MainActivity, R.layout.listview_item,pwdCnfFile.listPwdItems())
        itemsListView.adapter = itemsAdapter

        itemsListView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as PwdItem
            Toast.makeText(context, "Click on item $selectedItem", Toast.LENGTH_SHORT).show()
            // add this bundle when you move to another fragment.
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button to add an item to List
        val btnAddItem = my_activity.findViewById<ImageButton>(R.id.btn_add_item)
        btnAddItem?.setOnClickListener {
            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()
            itemsAdapter!!.addNewItem()
            itemsAdapter!!.notifyDataSetChanged()
        }

        // Button to search item by name
        val btnFindByName = my_activity.findViewById<ImageButton>(R.id.btn_find_by_name)
        btnFindByName.setOnClickListener {
            Toast.makeText(context, "Find by name", Toast.LENGTH_SHORT).show()
            itemsAdapter!!.filter.filter("name_it_1")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(context, "On resume function", Toast.LENGTH_SHORT).show()
        refresh()
    }

    fun refresh()
    {
        itemsAdapter!!.notifyDataSetChanged()
    }

}
