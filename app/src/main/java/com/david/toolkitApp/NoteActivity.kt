package com.david.toolkitApp

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NoteActivity : AppCompatActivity() {
    private lateinit var noteEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        db = FirebaseFirestore.getInstance()
        noteEditText = findViewById(R.id.note_edit_text)
        saveButton = findViewById(R.id.save_button)

        saveButton.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val noteText = noteEditText.text.toString()

        // Obtén el uid del usuario actualmente autenticado
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // Si el usuario no está autenticado, no permitir guardar la nota
        if (userId == null) {
            Toast.makeText(this, "Necesitas estar autenticado para guardar notas.", Toast.LENGTH_SHORT).show()
            return
        }

        // Crea un objeto de nota
        val note = hashMapOf("text" to noteText)

        // Guarda la nota en Firestore dentro de la colección del usuario
        db.collection("users").document(userId).collection("notes")
            .add(note)
            .addOnSuccessListener {
                Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()
            }
    }

}
