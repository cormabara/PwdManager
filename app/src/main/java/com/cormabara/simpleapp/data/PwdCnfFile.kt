package com.cormabara.simpleapp.data

import com.cormabara.simpleapp.data.PwdGroup
import com.cormabara.simpleapp.data.PwdItem

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

    fun FindGroupById(grpid_: String) : PwdGroup
    {
        for (grp in groupList) {
            if (grp.id == grpid_) return grp
        }
        return groupList[0]
    }

    /** Find all items that belongs to a group */
    fun FindElementsByGroup(grpid_: String) : ArrayList<PwdItem>
    {
        var itlist = ArrayList<PwdItem>()
        for (it in itemList) {
            if (it.groupId == grpid_)
                itlist.add(it)
        }
        return itlist
    }

    fun AddGroup(name_: String = "name", username_: String = "username", password_: String = "password"): PwdGroup {
        var pwdGroup = PwdGroup("gr_" + (groupList.size + 1), name_)
        groupList.add(pwdGroup)
        return pwdGroup
    }
}

