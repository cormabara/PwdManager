package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.cormabara.pwdmanager.lib.MyLog
import com.cormabara.pwdmanager.databinding.ListOptionBinding


class ListOption @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var binding: ListOptionBinding
    private lateinit var aa: ArrayAdapter<String>
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ListOptionBinding.inflate(inflater,this)
        binding.optionListTitle.text = "option text"
        orientation = VERTICAL
    }

    fun configure(title_: String, options_: Array<String>)
    {
        aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, options_)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(binding.optionList)
        {
            adapter = aa
            gravity = Gravity.CENTER
        }
    }
    fun getSelected() : String
    {
        return binding.optionList.selectedItem as String
    }

    fun setSelected(val_: String)
    {
        binding.optionList.setSelection(aa.getPosition(val_))
    }

}