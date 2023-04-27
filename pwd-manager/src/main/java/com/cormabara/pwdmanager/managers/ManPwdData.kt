package com.cormabara.pwdmanager.managers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.cormabara.pwdmanager.R
import com.cormabara.pwdmanager.lib.MyLog
import com.cormabara.pwdmanager.lib.PwdCrypt
import com.cormabara.pwdmanager.gui.dialogs.ChooseDialog
import com.cormabara.pwdmanager.gui.dialogs.MsgDialog
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File


class ManPwdData(path_: File) {

    private lateinit var dataPath: File
    private var dataFile: File
    private var dataFileBkp: File
    private val cnfPwdFnCrypt = "pwd_data_crypt.json"
    private val cnfPwdFnCryptBkp = "pwd_data_crypt_bkp.json"
    class PwdItem() {

        var id: String = ""
        var name: String = ""
        var username: String = ""
        var password: String = ""
        var tagList: ArrayList<String> = ArrayList()

        constructor(id_: String, name_: String = "name", username_: String = "username", password_: String = "password") : this() {
            id = id_
            name = name_
            username = username_
            password = password_
        }
        fun setPwdName(v_: String) { name = v_ }
        fun setPwdUsername(v_: String) {username = v_}
        fun setPwdPassword(v_: String) {password = v_}
        fun addTag(tag_: String) {if ( (tag_.isNotEmpty()) && (tag_ !in tagList) ) tagList.add(tag_) }
        fun delTag(tag_: String) {if (tag_ in tagList) tagList.remove(tag_) }
        fun hasTag(tag_:String): Boolean  {return (tag_ in tagList) }
    }

    class PwdData() {
        var tagList: ArrayList<String> = ArrayList()
        var itemList: ArrayList<PwdItem> = ArrayList()
    }
    private var internal_data: PwdData = PwdData()

    init {
        dataFile = File(path_,cnfPwdFnCrypt)
        dataFileBkp = File(path_,cnfPwdFnCryptBkp)
    }

    private fun loadDataLow(file_: File, password_: String): Boolean
    {
        if (file_.exists()) {
            try {
                val mapper = jacksonObjectMapper()
                val str2 = PwdCrypt.FileDecrypt(password_, file_)
                MyLog.LInfo(str2)
                internal_data = mapper.readValue(str2)
                return true
            } catch (e: Exception) {
                MyLog.LError("Exception loading the data file ${file_.absoluteFile}, trying backup")
                return false
            }
        }
        MyLog.LError("Data file ${file_.absoluteFile} is missing")
        return false
    }

    /** @brief Force the deletion of the pwd data */
    fun newData () {
        if (dataFile.exists() ) {
            dataFile.delete()
        }
    }

    /** @brief Decrypt and load the pwd data, if are not present
     *  create an empty one */
    fun load (context: Context, password_: String) : Boolean {
        var retval = false
        if (!loadDataLow(dataFile,password_)) {
            val chooseDiag = ChooseDialog(context)
            chooseDiag.show("Cannot recover data", "Try to recover the last backup?") {
            if (it == ChooseDialog.ResponseType.YES)
                retval = loadDataLow(dataFileBkp,password_)
            }
        }
        else {
            retval = true
        }
        purgeTags()
        return retval
    }

    /** @brief Save all the pwd data with encrypt by "passwd_". If save is ok create 
     * also a backup copy */
    fun save(passwd_: String, updateBackup_: Boolean = false): Boolean
    {
        try {
            val mapper = jacksonObjectMapper()
            val myStr = mapper.writeValueAsString(internal_data)
            Log.i("ManPwdData", myStr)
            PwdCrypt.FileEncrypt(passwd_, myStr, dataFile)
            // If the operation ok the file is safe so make a backup copy
            if (updateBackup_)
                dataFile.copyTo(dataFileBkp, true)
            return true
        } catch (e: Exception) {
            MyLog.LError("Exception saving the data file ${dataFile.absoluteFile}")
        }
        return false
    }

    fun restoreBackupData(context_: Context,passwd_: String) : Boolean {
        return importData(context_,dataFileBkp,passwd_)
    }
    /** @brief Function to check if pwd data are present or not */
    fun CheckData() :Boolean
    {
        return dataFile.exists()
    }
    
    /** @brief This function exports the data by email */
    fun exportData(context: Context, mail_: String)
    {
        val dpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        var filen = "${dpath}/${dataFile.name}"
        MyLog.LInfo("Destination File ${filen}.")


        var file = File(filen)
        dataFile.copyTo(file,true)
        if (!file.exists())
            MyLog.LInfo("File ${file.name} not present")

        val path: Uri = Uri.fromFile(file)
        val emailIntent = Intent(Intent.ACTION_SEND)
        // set the type to 'email'
        emailIntent.type = "vnd.android.cursor.dir/email"
        val to = arrayOf(mail_)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        // the attachment
        MyLog.LInfo("Email attachment ${path}")
        emailIntent.putExtra(Intent.EXTRA_STREAM, path)
        // the mail subject
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pwd Manager backup")
        startActivity(context,Intent.createChooser(emailIntent, "Send email..."),null)
    }
    /** @brief This function import data from file */
    fun importData(context_: Context,file_: File, password_: String) : Boolean
    {
        if (!loadDataLow(file_,password_)) {
            MsgDialog(context_, "ERROR", context_.getString(R.string.err_import_data))
            return false
        }
        return save(password_)
    }

    fun listPwdItems(): ArrayList<PwdItem>  {
        return internal_data.itemList
    }
    
    /** @brief Check if an item with the "id_" identifier is already present */
    fun checkId(id_: String) : Boolean
    {
        for (it in internal_data.itemList) {
            if (it.id == id_)
                return true;
        }
        return false;
    }

    fun newId() : Int
    {
        var newid = 1
        val MAXIDITEMS = 200000
        while(this.checkId("it_" + newid.toString())) {
            newid = newid + 1
            if (newid > MAXIDITEMS) return 0
        }
        return newid
    }
    
    /** @brief Add a new item in the list with the auto data */
    fun newItem(): PwdItem
    {
        val id = "it_" + newId().toString()
        val name = "name_$id"
        val username = "usr_$id"
        val password = "pwd_$id"
        var pwdItem = PwdItem(id,name,username,password)
        internal_data.itemList.add(pwdItem)
        return pwdItem
    }
    /** @brief Add a new item in the list with the auto data */
    fun delItem(pi_: PwdItem)
    {
        internal_data.itemList.remove(pi_)
    }

    fun findTagInItems(tag_: String): Boolean
    {
        for (item in internal_data.itemList) {
            if (item.tagList.find { name -> name == tag_ } != null)
                return true
        }
        return false
    }

    fun purgeTags()
    {
        var todel = ArrayList<String>()
        for (tag in internal_data.tagList) {
            if (findTagInItems(tag) == false) {
                MyLog.LInfo("tag '${tag}' is no more used, deleted")
                todel.add(tag)
            }
        }
        for (tag in todel)
            delTag(tag)
    }
    fun addTag(tag_: String)
    {
        if(tag_ !in  internal_data.tagList)
            internal_data.tagList.add(tag_)
    }
    fun delTag(tag_: String)
    {
        internal_data.tagList.remove(tag_)
    }
    fun checkTag(tag_: String) : Boolean
    {
        return internal_data.tagList.contains(tag_)
    }
    fun getTags(): ArrayList<String> {return internal_data.tagList}

    /** Find all items that belongs to a group */
    fun itemsByTag(tag_: String) : ArrayList<PwdItem>
    {
        var itlist = ArrayList<PwdItem>()
        for (it in internal_data.itemList) {
            if (it.hasTag(tag_))
                itlist.add(it)
        }
        return itlist
    }
}
