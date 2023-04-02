package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.findNavController
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.databinding.MainPasswordControlBinding

class MainPasswordControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var binding: MainPasswordControlBinding
    val insertMode_: Boolean = false
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = MainPasswordControlBinding.inflate(inflater,this)
        var attributeArray: TypedArray? = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.password_control_attributes, 0, 0
        )
        var insertNewPassword = false;
        if (attrs != null) {
            insertNewPassword = attributeArray?.getBoolean(
                R.styleable.password_control_attributes_insert_new_password,
                false
            ) ?: false;
        }
        if (insertNewPassword) {
            binding.startPassword2.visibility = VISIBLE
            binding.titleConfirmPassword.visibility = VISIBLE
            binding.insertPasswordConfirm.setOnClickListener() {
                val pwd1 = binding.startPassword1.text.toString()
                val pwd2 = binding.startPassword2.text.toString()
                Toast.makeText(
                    context as MainActivity,
                    "Submit button $pwd1-$pwd2",
                    Toast.LENGTH_SHORT
                ).show()
                if (pwd1 == pwd2) {
                    context.mainPassword = pwd1
                    context.manPwdData.newData()
                    findNavController().navigate(R.id.action_firstStartFragment_to_MainFragment)
                } else {
                    Toast.makeText(
                        context,
                        "Incorrect password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        else {
            binding.startPassword2.visibility = GONE;
            binding.titleConfirmPassword.visibility = GONE
            binding.insertPasswordConfirm.setOnClickListener() {
                (context as MainActivity).mainPassword = binding.startPassword1.text.toString()
                val pwd = (context as MainActivity).mainPassword
                if (context.manPwdData.loadData(context,pwd))
                    findNavController().navigate(R.id.action_StartFragment_to_MainFragment)
                else
                    Toast.makeText(context, "Wrong password ($pwd)", Toast.LENGTH_SHORT).show()
            }
        }
    }


}