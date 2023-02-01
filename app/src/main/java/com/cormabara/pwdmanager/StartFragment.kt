package com.cormabara.pwdmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cormabara.pwdmanager.databinding.FragmentStartBinding

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
        var my_activity = (activity as MainActivity)

        // init view model
        // Init the item list view
        binding.btnSubmit.setOnClickListener() {
            my_activity.mainPassword = binding.textPassword.text.toString()
            //my_activity.mainPassword = "turbopino"
            val pwd = (context as MainActivity).mainPassword
            if ( (context as MainActivity).loadPwdData() == true)
                findNavController().navigate(R.id.action_StartFragment_to_MainFragment)
            else
                Toast.makeText(context as MainActivity, "Wrong password ($pwd)", Toast.LENGTH_SHORT).show()

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //findNavController().navigate(R.id.action_StartFragment_to_MainFragment)

    }
}