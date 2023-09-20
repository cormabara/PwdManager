package com.cormabara.pwdmanager.gui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.gui.dialogs.MsgType
import com.cormabara.pwdmanager.gui.dialogs.PasswordMode
import com.cormabara.pwdmanager.gui.dialogs.msgDialog
import com.cormabara.pwdmanager.gui.dialogs.passwordDialog
import com.cormabara.pwdmanager.lib.MyLog

/**
 * A simple [Fragment] subclass.
 * Use the [StartupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartupFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_startup, container, false)
    }

    private fun insertPassword()
    {
        val myActivity = (context as MainActivity)
        passwordDialog(myActivity, PasswordMode.PWD_INSERT) { r, txt_ ->
            if (r) {
                if (myActivity.manPwdData.load(myActivity, txt_)) {
                    MyLog.logInfo("the password $txt_ is valid")
                    myActivity.mainPassword = txt_
                    myActivity.findNavController(R.id.nav_host_fragment_content_main)
                        .navigate(R.id.action_to_mainFragment)
                } else {
                    MyLog.logError("Wrong password, retry")
                    msgDialog(myActivity,MsgType.MSG_ERROR,"Wrong password or damaged file")
                }
            }
        }
    }

    private fun newPassword()
    {
        val myActivity = (context as MainActivity)
        passwordDialog(myActivity, PasswordMode.PWD_START) { r, txt_ ->
            if (r) {
                MyLog.logInfo("new password is valid, is $txt_")
                myActivity.manPwdData.newData()
                myActivity.mainPassword = txt_
                myActivity.manPwdData.save(myActivity.mainPassword)
                myActivity.findNavController(R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_to_mainFragment)

            } else {
                MyLog.logError("New password is not valid, retry")
                msgDialog(myActivity,MsgType.MSG_ERROR,"New password is not valid, retry")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val myActivity = (context as MainActivity)
        // If the manPwdData is present ask for the password else is the first start so
        // ask new password
        if (myActivity.manPwdData.CheckData())
            insertPassword()
        else
            newPassword()
    }
}