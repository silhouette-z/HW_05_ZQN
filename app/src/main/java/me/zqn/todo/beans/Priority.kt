package me.zqn.todo.beans

import android.graphics.Color

enum class Priority(val intValue: Int, val color: Int) {
    High(2, Color.RED), Medium(1, Color.GREEN), Low(0, Color.WHITE);

    companion object {
        fun from(intValue: Int): Priority {
            for (priority in me.zqn.todo.beans.Priority.values()) {
                if (priority.intValue == intValue) {
                    return priority
                }
            }
            return Low // default
        }
    }
}