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
import com.cormabara.pwdmanager.databinding.FragmentInsPasswordBinding

class InsPasswordFragment : Fragment() {
    private var _binding: FragmentInsPasswordBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInsPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }
}