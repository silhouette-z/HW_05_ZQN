package me.zqn.todo.beans

import java.util.*

class Note(val id: Long) {
    var date: Date? = null
    var state: State? = null
    var content: String? = null
    var priority: Priority? = null

}