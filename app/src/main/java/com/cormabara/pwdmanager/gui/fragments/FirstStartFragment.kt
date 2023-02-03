package com.cormabara.pwdmanager.gui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.databinding.FragmentFirstStartBinding

class FirstStartFragment : Fragment() {
    private var _binding: FragmentFirstStartBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstStartBinding.inflate(inflater, container, false)
        if ( (context as MainActivity).manPwdData.CheckData() == true) {
            findNavController().navigate(R.id.action_firstStartFragment_to_StartFragment)
        }
        // init view model
        // Init the item list view
        binding.insertPasswordConfirm.setOnClickListener() {
            val pwd1 = binding.startPassword1.text.toString()
            val pwd2 = binding.startPassword2.text.toString()
            Toast.makeText(context as MainActivity, "Submit button $pwd1-$pwd2", Toast.LENGTH_SHORT).show()
            if (pwd1 == pwd2) {
                (context as MainActivity).mainPassword = pwd1
                (context as MainActivity).manPwdData.newData()
                findNavController().navigate(R.id.action_firstStartFragment_to_MainFragment)
            }
            else {
                Toast.makeText(context as MainActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}