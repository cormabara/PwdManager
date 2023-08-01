package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.databinding.BinaryOptionBinding
import com.cormabara.pwdmanager.databinding.StatusbarInfoBinding

class StatusBarInfo @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    private lateinit var binding: StatusbarInfoBinding
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = StatusbarInfoBinding.inflate(inflater, this)
        binding.txtStatus1.text = "text 1: text1"
        binding.txtStatus2.text = "text 2: text2"
        binding.txtStatus3.text = "text 3: text3"
    }
}