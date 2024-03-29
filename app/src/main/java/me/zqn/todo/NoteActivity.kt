package me.zqn.todo

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle

import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton

import me.zqn.todo.beans.Priority
import me.zqn.todo.beans.State
import me.zqn.todo.db.TodoContract
import me.zqn.todo.db.TodoDbHelper

class NoteActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var addBtn: Button? = null
    private var radioGroup: RadioGroup? = null
    private var lowRadio: AppCompatRadioButton? = null
    private var dbHelper: TodoDbHelper? = null
    private var database: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setTitle(R.string.take_a_note)

        dbHelper = TodoDbHelper(this)
        database = dbHelper!!.writableDatabase
        editText = findViewById(R.id.edit_text)
        editText!!.isFocusable = true
        editText!!.requestFocus()

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager?.showSoftInput(editText, 0)
        radioGroup = findViewById(R.id.radio_group)
        lowRadio = findViewById(R.id.btn_low)



        addBtn = findViewById(R.id.btn_add)
        addBtn?.setOnClickListener(View.OnClickListener {
            val content: CharSequence = editText!!.text
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(
                    this@NoteActivity,
                    "No content to add", Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            val succeed = saveNote2Database(
                content.toString().trim { it <= ' ' },
                selectedPriority
            )
            if (succeed) {
                Toast.makeText(
                    this@NoteActivity,
                    "Note added", Toast.LENGTH_SHORT
                ).show()
                setResult(Activity.RESULT_OK)
            } else {
                Toast.makeText(
                    this@NoteActivity,
                    "Error", Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        })
    }

    protected override fun onDestroy() {
        super.onDestroy()
        database!!.close()
        database = null
        dbHelper?.close()
        dbHelper = null
    }

    private fun saveNote2Database(content: String, priority: Priority): Boolean {
        if (database == null || TextUtils.isEmpty(content)) {
            return false
        }
        val values = ContentValues()
        values.put(TodoContract.TodoNote.COLUMN_CONTENT, content)
        values.put(TodoContract.TodoNote.COLUMN_STATE, State.TODO.intValue)
        values.put(TodoContract.TodoNote.COLUMN_DATE, System.currentTimeMillis())
        values.put(TodoContract.TodoNote.COLUMN_PRIORITY, priority.intValue)
        val rowId = database!!.insert(TodoContract.TodoNote.TABLE_NAME, null, values)
        return rowId != -1L
    }

    private val selectedPriority: Priority
        private get() = when (radioGroup!!.checkedRadioButtonId) {
            R.id.btn_high -> Priority.High
            R.id.btn_medium -> Priority.Medium
            else -> Priority.Low
        }
}