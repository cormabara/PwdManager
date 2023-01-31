package com.cormabara.pwdmanager.data

class PwdGroup() {

    var id: String
    var name: String

    init {
        id = ""
        name = ""
    }

    constructor(id_: String, name_: String = "name") : this() {
        id = id_
        name = name_

    }

    fun setPwdName(v_: String) { name = v_ }
}
