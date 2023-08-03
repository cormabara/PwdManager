package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.databinding.MainPasswordControlBinding
import com.cormabara.pwdmanager.databinding.PasswordControlBinding
import com.cormabara.pwdmanager.gui.dialogs.msgDialog
import com.cormabara.pwdmanager.gui.dialogs.MsgType
import com.cormabara.pwdmanager.lib.MyLog
import java.util.regex.Pattern

class PasswordControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var binding: PasswordControlBinding
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = PasswordControlBinding.inflate(inflater,this)
        binding.txtPwd.setOnClickListener {
            TextView.OnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_NEXT ||
                    event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    if (event == null || !event.isShiftPressed) {
                        newAndGo()
                        return@OnEditorActionListener true // consume.
                    }
                }
                false // pass on to other listeners.
            }
        }
    }
    private fun validatePassword(pwd1_: String,pwd2_: String):Boolean {
        val specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        val UpperCasePatten = Pattern.compile("[A-Z ]")
        val lowerCasePatten = Pattern.compile("[a-z ]")
        val digitCasePatten = Pattern.compile("[0-9 ]")
        if (pwd1_ != pwd2_) {
            msgDialog(context, MsgType.MSG_INFO, context.getString(R.string.different_password))
            return false
        }
        else if (pwd1_.isEmpty()) {
            msgDialog(context, MsgType.MSG_INFO, context.getString(R.string.password_is_empty))
            return false;
        }
        /*
        else if (pwd1_.length < 8) {
            showDialog(context,context.getString(R.string.password_too_short))
            return false;
        }
        else if (!UpperCasePatten.matcher(pwd1_).find()) {
            showDialog(context, context.getString(R.string.uppercase_missing))
            return false;
        }
        else if (!digitCasePatten.matcher(pwd1_).find()) {
            showDialog(context, context.getString(R.string.number_is_missing))
            return false;
        }

         */
        return true
    }

    private fun newAndGo()
    {
        val pwd1 = binding.startPassword1.text.toString()
        val pwd2 = binding.startPassword2.text.toString()
        MyLog.logInfo("{$pwd1-$pwd2}")
        if (validatePassword(pwd1, pwd2)) {
            Toast.makeText(
                context as MainActivity,
                "$pwd1-$pwd2",
                Toast.LENGTH_SHORT
            ).show()
            (context as MainActivity).mainPassword = pwd1
            // (context as MainActivity).manPwdData.newData()
            findNavController().navigate(R.id.action_to_mainFragment)
        }
    }
}