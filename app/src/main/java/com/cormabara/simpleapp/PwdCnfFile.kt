package com.cormabara.simpleapp

import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class PwdCnfFile() {

    var itemList: ArrayList<PwdItem> = ArrayList()
    var groupList: ArrayList<PwdGroup> = ArrayList()


    fun ItemList(): ArrayList<PwdItem>  {
        return itemList
    }

    fun AddItem(name_: String = "name", username_: String = "username", password_: String = "password"): PwdItem
    {
        var pwdItem = PwdItem("it_" + (itemList.size+1),name_,username_,password_)
        itemList.add(pwdItem)
        return pwdItem
    }


    fun GroupList(): ArrayList<PwdGroup>  {
        return groupList
    }
    fun AddGroup(name_: String = "name", username_: String = "username", password_: String = "password"): PwdGroup
    {
        var pwdGroup = PwdGroup("gr_" + (groupList.size+1),name_)
        groupList.add(pwdGroup)
        return pwdGroup
    }
}

fun PwdCnfFileInit(file_: File) : PwdCnfFile
{
    val mapper = jacksonObjectMapper()
    var pwdCnfFile: PwdCnfFile

    if (file_.exists()) {
        try {
            val string = file_.readText()
            Log.i("PwdCnfFile", string)

            pwdCnfFile = mapper.readValue(string)
            Log.i("PwdCnfFile", file_.readText())

        } catch (e: Exception) {
            file_.delete()
            pwdCnfFile = PwdCnfFile()
            pwdCnfFile.AddItem("item1", "username1", "password1")
            pwdCnfFile.AddItem("item2", "username2", "password2")
        }
    } else {
        pwdCnfFile = PwdCnfFile()
        pwdCnfFile.AddItem("item1", "username1", "password1")
        pwdCnfFile.AddItem("item2", "username2", "password2")
    }
    Log.i("MainActivity", "Application init is done")
    return pwdCnfFile
}