package com.cormabara.pwdmanager.gui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.lib.MyLog
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.databinding.FragmentSettingBinding
import com.cormabara.pwdmanager.gui.lib.BinaryOption.BinaryOptionListener
import com.cormabara.pwdmanager.managers.ManAppConfig

inline fun <reified T : Enum<T>> getNames() = enumValues<T>().map { it.name }

class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cnf = (context as MainActivity).manAppConfig
        // Inflate the layout for this fragment
        val binding =  FragmentSettingBinding.inflate(inflater, container, false)
        binding.themeOption.configure("Theme",ManAppConfig.GuiTheme.DARK.toString(),ManAppConfig.GuiTheme.LIGHT.toString())
        binding.themeOption.setActive(cnf.guiTheme.toString())
        binding.findOption.configure("Find",ManAppConfig.SearchMode.CONTAINS.toString(),ManAppConfig.SearchMode.START_WITH.toString())
        binding.findOption.setActive(cnf.searchMode.toString())
        binding.languageOption.configure("Language",getNames<ManAppConfig.Languages>().toTypedArray())
        binding.findOption.setActive(cnf.searchMode.toString())
        binding.userEmail.setText(cnf.userMail)
        binding.btnSaveSettings.setOnClickListener {
            cnf.guiTheme  = enumValueOf(binding.themeOption.getActive())
            cnf.searchMode = enumValueOf(binding.findOption.getActive())
            cnf.userMail = binding.userEmail.text.toString()
            cnf.saveData()
            findNavController().navigate(R.id.action_to_mainFragment)
        }
        return binding.root
    }
}