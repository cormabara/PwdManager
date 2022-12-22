package com.cormabara.simpleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cormabara.simpleapp.databinding.FragmentFirstStartBinding

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
        if ( (context as MainActivity).CheckPwdData() == true) {
            findNavController().navigate(R.id.action_firstStartFragment_to_StartFragment)
        }
        // init view model
        // Init the item list view
        binding.btnSubmit.setOnClickListener() {
            val pwd1 = binding.textPassword1.text.toString()
            val pwd2 = binding.textPassword2.text.toString()
            Toast.makeText(context as MainActivity, "Submit button $pwd1-$pwd2", Toast.LENGTH_SHORT).show()
            if (pwd1 == pwd2) {
                (context as MainActivity).mainPassword = pwd1
                (context as MainActivity).createPwdData()
                findNavController().navigate(R.id.action_firstStartFragment_to_MainFragment)
            }
            else {
                Toast.makeText(context as MainActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}