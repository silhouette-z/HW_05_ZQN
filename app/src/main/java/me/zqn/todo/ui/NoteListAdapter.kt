package me.zqn.todo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import me.zqn.todo.NoteOperator
import me.zqn.todo.R
import me.zqn.todo.beans.Note
import me.zqn.todo.ui.NoteViewHolder

import java.util.ArrayList

class NoteListAdapter(private var operator: NoteOperator) :
    RecyclerView.Adapter<NoteViewHolder?>() {

    private val notes: MutableList<Note> = ArrayList<Note>()
    fun refresh(newNotes: List<Note>?) {
        notes.clear()
        if (newNotes != null) {
            notes.addAll(newNotes)
        }
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, pos: Int): NoteViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView, operator)
    }

    override fun onBindViewHolder(@NonNull holder: NoteViewHolder, pos: Int) {
        holder.bind(notes[pos])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    init {
        this.operator = operator
    }
}