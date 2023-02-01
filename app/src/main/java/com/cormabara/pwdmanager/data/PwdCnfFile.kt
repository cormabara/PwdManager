package com.cormabara.pwdmanager.data

class PwdCnfFile() {

    var tagList: ArrayList<String> = ArrayList()
    var itemList: ArrayList<PwdItem> = ArrayList()

    fun listPwdItems(): ArrayList<PwdItem>  {
        return itemList
    }
    
    /** @brief Check if an item with the "id_" identifier is already present */
    fun checkId(id_: String) : Boolean
    {
        for (it in itemList) {
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
        itemList.add(pwdItem)
        return pwdItem
    }

    fun addTag(tag_: String)
    {
        tagList.add(tag_)
    }
    fun delTag(tag_: String)
    {
        tagList.remove(tag_)
    }
    fun checkTag(tag_: String) : Boolean
    {
        return tagList.contains(tag_)
    }
    /** Find all items that belongs to a group */
    fun itemsByTag(tag_: String) : ArrayList<PwdItem>
    {
        var itlist = ArrayList<PwdItem>()
        for (it in itemList) {
            if (it.hasTag(tag_))
                itlist.add(it)
        }
        return itlist
    }
}

