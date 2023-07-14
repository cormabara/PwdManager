package com.cormabara.pwdmanager

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.cormabara.pwdmanager.gui.dialogs.ChooseDialog
import com.cormabara.pwdmanager.gui.dialogs.inputTextDialog
import com.cormabara.pwdmanager.gui.lib.PwdItemAdapter
import com.cormabara.pwdmanager.lib.MyLog
import com.cormabara.pwdmanager.managers.ManPwdData
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun editItemDialog(context: Context, adapter_ : PwdItemAdapter, item_: ManPwdData.PwdItem?) {
    var create = false
    var item = item_
    if (item == null) {
        item = (context as MainActivity).manPwdData.createItem()
        create = true
    }
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_edit_item)


    var btnDelete = dialog.findViewById(R.id.btn_delete) as ImageButton
    btnDelete.setOnClickListener {
        val chooseDiag = ChooseDialog(context)
        chooseDiag.show("Delete element","If YES ${item.name} will be deleted") {
            if (it == ChooseDialog.ResponseType.YES) {
                (context as MainActivity).manPwdData.delItem(item)
                context.manPwdData.save(context.mainPassword)
                dialog.dismiss()
                adapter_.notifyDataSetChanged()
            }
        }
    }

    var txt_name: EditText = dialog.findViewById(R.id.txt_name)
    txt_name.setText(item.name)

    var txt_password: EditText = dialog.findViewById(R.id.txt_password)
    txt_password.setText(item.password)

    var txt_username: EditText = dialog.findViewById(R.id.txt_username)
    txt_username.setText(item.username)

    val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun addNewTagChip(tags_grp_: ChipGroup, tag_: String, item_: ManPwdData.PwdItem? = null) {
        val chip = Chip(tags_grp_.context)
        chip.text= "${tag_}"
        // necessary to get single selection working
        chip.isClickable = true
        chip.isCheckable = true
        if ( (item_ != null) && (item_.tagList.contains(tag_)) )
            chip.isChecked = true
        chip.setOnClickListener {
            MyLog.logInfo("Chip click (ischecked=${chip.isChecked}")
            if (chip.isChecked) item_?.addTag(tag_)
            else item?.delTag(tag_)
        }

        tags_grp_.addView(chip)
    }

    var taglist = (context as MainActivity).manPwdData.getTags()
    val tags_grp = dialog.findViewById<ChipGroup>(R.id.tags_group)
    val plus_chip = Chip(tags_grp.context)
    plus_chip.chipIcon = ContextCompat.getDrawable(context,R.mipmap.img_add_item_foreground)
    plus_chip.isClickable = true
    plus_chip.text = ""
    plus_chip.setOnClickListener {
        inputTextDialog(context, "Insert new tag")
        { r, txt_ ->
            if (r) {
                if (txt_ != "") {
                    val mpwdd = context.manPwdData
                    if (!mpwdd.checkTag(txt_)) {
                        mpwdd.addTag(txt_)
                        item.addTag(txt_)
                        addNewTagChip(tags_grp, txt_)
                        MyLog.logInfo("Tag $txt_ inserted")
                    }
                }
            }
        }
    }
    tags_grp.addView(plus_chip)
    for (tag in taglist) {
        addNewTagChip(tags_grp, tag,item)
    }

    val btnok = dialog.findViewById(R.id.btn_ok) as Button
    val btncancel = dialog.findViewById(R.id.btn_cancel) as Button
    btnok.setOnClickListener {
        item.setPwdName(txt_name.text.toString())
        item.setPwdPassword(txt_password.text.toString())
        item.setPwdUsername(txt_username.text.toString())
        for (chip in tags_grp.children ) {
            if ( (chip as Chip).isChecked)
                item.addTag(chip.text.toString())
        }
        dialog.dismiss()
        if (create)
            context.manPwdData.addItem(item)
        adapter_.notifyDataSetChanged()
        context.manPwdData.save(context.mainPassword)
    }
    btncancel.setOnClickListener { dialog.dismiss() }
    dialog.show()
}
