package com.cormabara.simpleapp

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
}
