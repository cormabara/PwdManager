package com.cormabara.simpleapp

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
import com.cormabara.simpleapp.data.PwdCnfFile
import com.cormabara.simpleapp.data.PwdItem
import com.cormabara.simpleapp.databinding.FragmentMainBinding


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

    private var groupsAdapter: PwdGroupAdapter? = null
    private lateinit var groupsListView: ListView

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
        itemsAdapter = PwdItemAdapter(context as MainActivity,pwdCnfFile.ItemList())
        itemsListView.adapter = itemsAdapter
        groupsListView = binding.idGroupsList
        groupsAdapter = PwdGroupAdapter(context as MainActivity,pwdCnfFile.GroupList())
        groupsListView.adapter = groupsAdapter

        itemsListView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as PwdItem
            Toast.makeText(context, "Click on item $selectedItem", Toast.LENGTH_SHORT).show()
            // add this bundle when you move to another fragment.
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)!!.supportActionBar?.setTitle("pippo")

        val btnAddItem = (activity as MainActivity).findViewById<ImageButton>(R.id.btn_add_item)
        btnAddItem?.setOnClickListener {
            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()
            pwdCnfFile.AddItem("grp")
            itemsAdapter!!.notifyDataSetChanged()
        }

        val btnAddGroup = (activity as MainActivity).findViewById<ImageButton>(R.id.btn_add_group)
        btnAddGroup?.setOnClickListener {
            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()
            pwdCnfFile.AddGroup("grp")
            groupsAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        //shoudRefreshOnResume is a global var
        Toast.makeText(context, "On resume function", Toast.LENGTH_SHORT).show()
        refresh()
    }

    fun refresh()
    {
        itemsAdapter!!.notifyDataSetChanged()
        groupsAdapter!!.notifyDataSetChanged()
    }

}
