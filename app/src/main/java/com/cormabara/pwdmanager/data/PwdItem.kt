package com.cormabara.pwdmanager.data

class PwdItem() {

    var id: String = ""
    var name: String = ""
    var username: String = ""
    var password: String = ""
    var groupId: String = ""
    var tagList: ArrayList<String> = ArrayList<String>()

    constructor(id_: String, name_: String = "name", username_: String = "username", password_: String = "password") : this() {
        id = id_
        name = name_
        username = username_
        password = password_
        groupId = ""
    }
    fun setPwdName(v_: String) { name = v_ }
    fun setPwdUsername(v_: String) {username = v_}
    fun setPwdPassword(v_: String) {password = v_}
    fun setPwdGroup(v_: String) {groupId = v_}
    fun addTag(tag_: String) {if (tag_ !in tagList) tagList.add(tag_) }
    fun hasTag(tag_:String): Boolean  {return (tag_ in tagList) }
}
