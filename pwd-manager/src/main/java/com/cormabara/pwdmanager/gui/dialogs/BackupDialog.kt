package com.cormabara.pwdmanager.gui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import com.cormabara.pwdmanager.MainActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.gui.fragments.MainFragment
import com.cormabara.pwdmanager.lib.MyLog
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class BkFilesListAdapter(context_: Context, private val dataSet: ArrayList<File>) :
    ArrayAdapter<File>(context_, R.layout.listview_bkfiles,dataSet) {

    private class ViewHolder {
        lateinit var bkp_name: TextView
        lateinit var bkp_del: ImageButton
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getItem(position: Int): File {
        return dataSet[position]
    }

    private fun refresh()
    {

    }

    override fun getView(position: Int, convertView_: View?, parent: ViewGroup): View
    {
        var convertView = convertView_
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.listview_bkfiles, parent, false)
            viewHolder.bkp_name = convertView.findViewById(R.id.bk_item_name)
            viewHolder.bkp_del = convertView.findViewById(R.id.bkp_btn_delete)
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
        viewHolder.bkp_del.setOnClickListener {
            val delitem: File = getItem(position)
            val chooseDiag = ChooseDialog(context)
            chooseDiag.show("Delete backup file ?", "If YES ${delitem.name} backup file will be deleted, are you sure?") {
                if (it == ChooseDialog.ResponseType.YES) {
                    MyLog.logInfo("Backup ${delitem.name} deleted.")
                    delitem.delete()
                    dataSet.remove(delitem)
                    refresh()
                    notifyDataSetChanged()
                }
            }
        }
        val item: File = getItem(position)
        viewHolder.bkp_name.text = item.nameWithoutExtension
        return result
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun backupDialog(context_: Context,save_: Boolean) {
    val dir = File((context_ as MainActivity).backupPath)
    if (!dir.exists())
        dir.mkdir()

    if (!dir.exists())
        MyLog.logError("Error backup folder is missing")

    val files_v = dir.listFiles()
    val fileNames = arrayOfNulls<String>(files_v.size)
    files_v?.mapIndexed { index, item ->
        fileNames[index] = item?.name
    }
    val datetime = context_.getDateTime("yyyy_MM_dd_HH_mm_ss")
    val bkfilename = File("$dir/$datetime.bkp")

    val files: ArrayList<File> = ArrayList(listOf(*files_v))

    val dialog = Dialog(context_)
    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_backup)

    //val title = dialog.findViewById(R.id.backup_title) as TextView
    //title.setText(R.string.dialog_backup_title)

    val adapter = BkFilesListAdapter(context_, files)
    val listView = dialog.findViewById(R.id.list_view_backup) as ListView
    listView.adapter = adapter

    listView.setOnItemClickListener { parent, _, position, _ ->
        MyLog.logInfo("clicked pos: $position")
        val selectedItem = adapter!!.getItem(position)
        // Restore the backup data from the backup file
        context_.manPwdData.restoreBackupData(context_, selectedItem, context_.mainPassword)
        dialog.dismiss()
        true
    }
    listView.setOnItemLongClickListener { parent, view, position, id ->
        MyLog.logInfo("clicked pos: $position")
        val selectedItem = adapter!!.getItem(position)
        // Restore the backup data from the backup file
        context_.manPwdData.restoreBackupData(context_, selectedItem, context_.mainPassword)
        dialog.dismiss()
        val navHostFragment: NavHostFragment =
            context_.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                    as NavHostFragment
        (navHostFragment.childFragmentManager.fragments[0] as MainFragment?)?.reloadData()
        true
    }
    listView.isClickable = true;
    listView.isLongClickable = true;

    val filetext = dialog.findViewById(R.id.bkp_files_list) as EditText
    filetext.setText(bkfilename.name)
    filetext.visibility = if (save_) View.VISIBLE  else View.INVISIBLE


    val saveBtn = dialog.findViewById(R.id.saveBtn) as Button
    saveBtn.visibility = if (save_) View.VISIBLE  else View.INVISIBLE
    saveBtn.setOnClickListener {
        context_.manPwdData.saveBackupData(context_, bkfilename,context_.mainPassword)
        dialog.dismiss()
    }
    val cancelBtn = dialog.findViewById(R.id.cancelBtn) as Button
    cancelBtn.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}

