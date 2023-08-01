package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import com.cormabara.pwdmanager.lib.MyLog
import com.cormabara.pwdmanager.databinding.BinaryOptionBinding


class BinaryOption @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
    ) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var binding: BinaryOptionBinding

    interface BinaryOptionListener {
        fun onOptionChanged(option_: String)
    }
    private var  clistener: BinaryOptionListener? = null

    // Assign the listener implementing events interface that will receive the events
    public fun setCustomObjectListener(listener_: BinaryOptionListener) {
        this.clistener = listener_
    }

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = BinaryOptionBinding.inflate(inflater,this)
        binding.optionTitle.text = "option text"
        orientation = VERTICAL

        binding.optionSwitch.setOnCheckedChangeListener { _, _ ->
            if (binding.optionSwitch.isChecked) {
                binding.optionSwitch.text = binding.optionSwitch.textOn
            }
            else {
                binding.optionSwitch.text = binding.optionSwitch.textOff
            }
        }
    }

    fun configure(title_: String, opt1_: String, opt2_: String)
    {
        binding.optionTitle.text = title_
        binding.optionSwitch.textOn = opt1_
        binding.optionSwitch.textOff = opt2_
    }

    fun getActive() : String
    {
        return binding.optionSwitch.text as String
    }
    fun setActive(val_: String)
    {
        binding.optionSwitch.isChecked = val_ == binding.optionSwitch.textOn
    }
}