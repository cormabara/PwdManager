package com.cormabara.pwdmanager.gui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cormabara.pwdmanager.MyLog
import com.cormabara.pwdmanager.databinding.FragmentSettingBinding
import com.cormabara.pwdmanager.gui.lib.BinaryOption
import com.cormabara.pwdmanager.gui.lib.BinaryOption.BinaryOptionListener
import com.cormabara.pwdmanager.managers.ManAppConfig

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
        binding.themeOption.configure("Theme",ManAppConfig.GuiTheme.DARK.toString(),ManAppConfig.GuiTheme.LIGHT.toString())
        binding.findOption.configure("Find",ManAppConfig.SearchMode.CONTAINS.toString(),ManAppConfig.SearchMode.START_WITH.toString())

        binding.themeOption.setCustomObjectListener(object : BinaryOptionListener {
            override fun onOptionChanged(option_: String) {
                // Code to handle object ready
                MyLog.LInfo("The option is changed ${option_}")
            }
        })
        return binding.root
    }
}