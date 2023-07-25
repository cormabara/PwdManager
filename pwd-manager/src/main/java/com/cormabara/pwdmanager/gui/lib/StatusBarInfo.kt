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
        binding.txtStatus1.text = "tag: 45"
        binding.txtStatus2.text = "num: 45"
        binding.txtStatus3.text = "info"
    }
}