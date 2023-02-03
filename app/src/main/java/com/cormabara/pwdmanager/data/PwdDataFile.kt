package com.cormabara.pwdmanager.data

import android.util.Log
import com.cormabara.pwdmanager.MyLog
import com.cormabara.pwdmanager.PwdCrypt
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class PwdDataFile(path_: File) {

    private lateinit var dataPath: File
    private lateinit var dataFile: File
    private val cnfPwdFnCrypt = "pwd_data_crypt.json"

    class PwdData() {
        var tagList: ArrayList<String> = ArrayList()
        var itemList: ArrayList<PwdDataItem> = ArrayList()
    }
    private var internal_data: PwdData = PwdData()

    init {
        dataFile = File(path_,cnfPwdFnCrypt)
    }

    fun newData () {
        if (dataFile.exists() ) {
            dataFile.delete()
        }
    }

    fun loadData (password_: String) : Boolean {
        if (dataFile.exists()) {
            try {
                val mapper = jacksonObjectMapper()
                val str2 = PwdCrypt.FileDecrypt(password_, dataFile)
                MyLog.LInfo(str2)
                internal_data = mapper.readValue(str2)
                return true
            } catch (e: Exception) {
                MyLog.LError("Exception loading the data file")
            }
        } else {
            internal_data = PwdData()
        }
        return false
    }

    fun saveData(passwd_: String)
    {
        val mapper = jacksonObjectMapper()
        val myStr = mapper.writeValueAsString(internal_data)
        Log.i("LogPwdDataSave",myStr)
        PwdCrypt.FileEncrypt(passwd_,myStr,dataFile)
    }

    fun CheckData() :Boolean
    {
        return dataFile.exists()
    }


    fun listPwdItems(): ArrayList<PwdDataItem>  {
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
    fun newItem(): PwdDataItem
    {
        val id = "it_" + newId().toString()
        val name = "name_$id"
        val username = "usr_$id"
        val password = "pwd_$id"
        var pwdDataItem = PwdDataItem(id,name,username,password)
        internal_data.itemList.add(pwdDataItem)
        return pwdDataItem
    }

    fun addTag(tag_: String)
    {
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

    /** Find all items that belongs to a group */
    fun itemsByTag(tag_: String) : ArrayList<PwdDataItem>
    {
        var itlist = ArrayList<PwdDataItem>()
        for (it in internal_data.itemList) {
            if (it.hasTag(tag_))
                itlist.add(it)
        }
        return itlist
    }
}

