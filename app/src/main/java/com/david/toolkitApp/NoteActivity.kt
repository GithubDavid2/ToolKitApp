package com.david.toolkitApp
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NoteActivity : AppCompatActivity() {
    private lateinit var noteEditText: EditText
    private lateinit var saveButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        noteEditText = findViewById(R.id.note_edit_text)
        saveButton = findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            saveNote()
        }
    }
    private fun saveNote() {
        val noteText = noteEditText.text.toString()
        // Obtener las SharedPreferences.
        val sharedPref = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        // Obtener un editor de SharedPreferences.
        val editor = sharedPref.edit()
        // Crear una clave única para esta nota.
        val noteKey = System.currentTimeMillis().toString()
        // Agregar la nota a las SharedPreferences.
        editor.putString(noteKey, noteText)
        // Aplicar los cambios.
        editor.apply()
        // Mostrar una notificación.
        Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show()
        // Regresar a la NoteListActivity.
        finish()
    }
}
