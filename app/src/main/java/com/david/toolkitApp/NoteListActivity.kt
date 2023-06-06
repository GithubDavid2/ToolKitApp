package com.david.toolkitApp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoteListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var newNoteButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }
        newNoteButton = findViewById<Button>(R.id.new_note_button).apply {
            setOnClickListener {
                val intent = Intent(this@NoteListActivity, NoteActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {

        super.onResume()
        // Obtener las SharedPreferences.
        val sharedPref = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        // Crear una lista con las notas de SharedPreferences
        val myDataset = sharedPref.all.map { Pair(it.key, it.value.toString()) }.toMutableList()
        viewAdapter = MyAdapter(myDataset)
        recyclerView.adapter = viewAdapter
    }
}

