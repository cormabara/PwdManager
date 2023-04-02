package com.cormabara.pwdmanager

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import com.cormabara.pwdmanager.gui.lib.PwdItemAdapter
import com.cormabara.pwdmanager.gui.lib.TagListAdapter
import com.cormabara.pwdmanager.managers.ManPwdData


class CheckTag(n_: String,c_: Boolean) {
    var name: String = n_
    var checked: Boolean = c_
}

fun editItemDialog(context: Context, adapter_ : PwdItemAdapter, item: ManPwdData.PwdItem) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_edit_item)


    var txt_name: EditText = dialog.findViewById(R.id.txt_name)
    txt_name.setText(item.name)

    var txt_password: EditText = dialog.findViewById(R.id.txt_password)
    txt_password.setText(item.password)

    var txt_username: EditText = dialog.findViewById(R.id.txt_username)
    txt_username.setText(item.username)

    var txt_tag = dialog.findViewById<EditText>(R.id.txt_tag)
    txt_tag.setOnEditorActionListener(
        OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                  actionId == EditorInfo.IME_ACTION_DONE ||
                  actionId == EditorInfo.IME_ACTION_NEXT ||
                  event != null && event.action === KeyEvent.ACTION_DOWN && event.keyCode === KeyEvent.KEYCODE_ENTER) {
                if (event == null || !event.isShiftPressed) {
                    // the user is done typing.
                    (context as MainActivity).manPwdData.addTag(txt_tag.text.toString())
                    //val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    //if (imm != null)
                    //    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return@OnEditorActionListener true // consume.
                }
            }
            false // pass on to other listeners.
        }
    )

    var taglist = (context as MainActivity).manPwdData.getTags()
    var ctaglist: ArrayList<CheckTag> = ArrayList()
    for (tag in taglist) ctaglist.add(CheckTag(tag,false))
    var adapter = TagListAdapter(ctaglist, item,context)
    var listview = dialog.findViewById<ListView>(R.id.list_view_tags)
    listview.adapter = adapter


    val btnok = dialog.findViewById(R.id.btn_ok) as Button
    val btncancel = dialog.findViewById(R.id.btn_cancel) as Button
    btnok.setOnClickListener {
        item.setPwdName(txt_name.text.toString())
        item.setPwdPassword(txt_password.text.toString())
        item.setPwdUsername(txt_username.text.toString())

        dialog.dismiss()
        adapter_.notifyDataSetChanged()
        (context as MainActivity).manPwdData.saveData((context as MainActivity).mainPassword)
    }
    btncancel.setOnClickListener { dialog.dismiss() }
    dialog.show()
}
