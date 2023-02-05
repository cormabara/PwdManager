package com.cormabara.pwdmanager.gui.lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.cormabara.pwdmanager.R

class BinaryOption @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.binary_option, this, true)
        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                R.styleable.binary_option_attributes, 0, 0)
            val title = resources.getText(typedArray
                .getResourceId(R.styleable
                    .binary_option_attributes_binary_option_title,
                    R.string.setting_title))

            typedArray.recycle()
        }
    }
}