package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import com.cormabara.pwdmanager.MyLog
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
    binding.option1.text = "opt_1"
    binding.option2.text = "opt_2"
    orientation = VERTICAL

    // Get radio group selected item using on checked change listener
    binding.optionGrp.setOnCheckedChangeListener { group, checkedId ->
        val radio: RadioButton = findViewById(checkedId)
        MyLog.LInfo(" On checked change :" + "${radio.text}")
        clistener?.onOptionChanged(radio.text as String)
    }
    binding.option1.setOnClickListener { view ->
        radio_button_click(view)
    }
    binding.option2.setOnClickListener { view ->
        radio_button_click(view)
    }
}
    // Get the selected radio button text using radio button on click listener
    fun radio_button_click(view: View) {
        /* Get the clicked radio button instance */
        val radio: RadioButton = findViewById(binding.optionGrp.checkedRadioButtonId)
        MyLog.LInfo("On click : ${radio.text}")
    }
    fun configure(title_: String, opt1_: String, opt2_: String)
    {
        binding.optionTitle.text = title_
        binding.option1.text = opt1_
        binding.option2.text = opt2_
    }

    fun getActive() : String
    {
        val radio: RadioButton = findViewById(binding.optionGrp.checkedRadioButtonId)
        return radio.text.toString()
    }
    fun setActive(val_: String)
    {
        val rg = binding.optionGrp
        for (rbPosition in 0 until rg.childCount) {
            val rb = rg.getChildAt(rbPosition) as RadioButton
            if (rb.text == val_) {
                //do stuff for example rb.isChecked = true
                rb.isChecked = true
            }
        }
    }
}