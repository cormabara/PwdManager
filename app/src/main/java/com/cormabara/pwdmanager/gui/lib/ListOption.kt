package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import com.cormabara.pwdmanager.MyLog
import com.cormabara.pwdmanager.databinding.ListOptionBinding


class ListOption @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var binding: ListOptionBinding

    interface ListOptionListener {
        fun onOptionChanged(option_: String)
    }
    private var  clistener: ListOptionListener? = null

    // Assign the listener implementing events interface that will receive the events
    public fun setCustomObjectListener(listener_: ListOptionListener) {
        this.clistener = listener_
    }

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ListOptionBinding.inflate(inflater,this)
        binding.optionListTitle.text = "option text"
        orientation = VERTICAL

        // Get radio group selected item using on checked change listener
        //binding.optionList.setOnCheckedChangeListener { group, checkedId ->
        //}
    }

    fun configure(title_: String, options_: Array<String>)
    {
    }

    fun getActive() : String
    {
        return ""
    }

    fun setActive(val_: String)
    {
    }
}