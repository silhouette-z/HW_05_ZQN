package me.zqn.todo

import me.zqn.todo.beans.Note

interface NoteOperator {
    fun deleteNote(note: Note?)
    fun updateNote(note: Note?)
}