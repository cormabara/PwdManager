package com.cormabara.simpleapp

class PwdItem() {

    var id: String
    var name: String
    var username: String
    var password: String
    var groupId: String

    init {
        id = ""
        name = ""
        username = ""
        password = ""
        groupId = ""
    }

    constructor(id_: String, name_: String = "name", username_: String = "username", password_: String = "password") : this() {
        id = id_
        name = name_
        username = username_
        password = password_
        groupId = ""

    }

    /*
    var ItId: String
        get() = id
        set(value_) { id = value_ }
    var ItName: String
        get() = name
        set(value_) { name = value_ }
    var ItUsername: String
        get() = username
        set(value_) { username = value_ }
    var ItPassword: String
        get() = password
        set(value_) { password = value_ }

     */
    fun setPwdName(v_: String) { name = v_ }
    fun setPwdUsername(v_: String) {username = v_}
    fun setPwdPassword(v_: String) {password = v_}
    fun setPwdGroup(v_: String) {groupId = v_}
}
