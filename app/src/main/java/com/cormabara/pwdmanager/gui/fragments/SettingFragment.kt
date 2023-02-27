package com.cormabara.pwdmanager.gui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding =  FragmentSettingBinding.inflate(inflater, container, false)
        binding.themeOption.configure("Theme","dark","light")
        return binding.root
    }
}