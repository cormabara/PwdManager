package com.cormabara.simpleapp

class PwdItem() {

    var id: String
    var name: String
    var username: String
    var password: String

    init {
        id = ""
        name = ""
        username = ""
        password = ""
    }

    constructor(id_: String, name_: String = "name", username_: String = "username", password_: String = "password") : this() {
        id = id_
        name = name_
        username = username_
        password = password_

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

}
