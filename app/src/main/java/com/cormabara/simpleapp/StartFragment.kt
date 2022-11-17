package com.cormabara.simpleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cormabara.simpleapp.databinding.FragmentMainBinding
import com.cormabara.simpleapp.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStartBinding.inflate(inflater, container, false)

        // init view model
        // Init the item list view
        binding.btnSubmit.setOnClickListener() {
            val pwd = binding.etPassword.text
            Toast.makeText(context as MainActivity, "Submit button.$pwd", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_StartFragment_to_MainFragment)
        }

        return binding.root
    }
}