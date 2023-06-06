package com.david.toolkitApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NoteListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var newNoteButton: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        db = FirebaseFirestore.getInstance()

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

        // Obtén el uid del usuario actualmente autenticado
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // Si el usuario no está autenticado, no permitir leer las notas
        if (userId == null) {
            Toast.makeText(this, "Necesitas estar autenticado para ver las notas.", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("users").document(userId).collection("notes")
            .get()
            .addOnSuccessListener { documents ->
                val myDataset = documents.map {
                    Pair(it.id, it.getString("text") ?: "")
                }.toMutableList()

                viewAdapter = MyAdapter(myDataset)
                recyclerView.adapter = viewAdapter
            }
            .addOnFailureListener { exception ->
                // manejar el error aquí
                Toast.makeText(this, "Error: $exception", Toast.LENGTH_SHORT).show()
            }
    }

}
