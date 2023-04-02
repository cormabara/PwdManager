package com.cormabara.pwdmanager.gui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.BuildConfig
import com.cormabara.pwdmanager.databinding.FragmentAboutBinding
import com.cormabara.pwdmanager.databinding.FragmentSettingBinding
import com.cormabara.pwdmanager.lib.MyLog


/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity).showUpButton()
        val binding =  FragmentAboutBinding.inflate(inflater, container, false)
        val buildCode = BuildConfig.VERSION_CODE
        val buildName = BuildConfig.VERSION_NAME
        val pkgName = (activity as MainActivity).applicationContext.packageName

        binding.aboutTitle.text = getString(R.string.app_title)

        var str: String = "App: $pkgName \n"
        str += "Build: $buildName - $buildCode\n"
        binding.aboutInfo.text = str
        return binding.root
    }
}