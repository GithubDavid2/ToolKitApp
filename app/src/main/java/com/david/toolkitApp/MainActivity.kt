package com.david.toolkitApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.GridView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private val icons = arrayOf(
        R.drawable.note_icon,
        R.drawable.calculator_icon,
        R.drawable.currency_converter_icon
    ) // nombres iconos

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridview)
        gridView.adapter = ImageAdapter(this, icons)

        gridView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> startActivity(Intent(this, NoteListActivity::class.java))
                1 -> startActivity(Intent(this, CalculatorActivity::class.java))
                2 -> startActivity(Intent(this, CurrencyConverter::class.java))
            }
        }

        auth = FirebaseAuth.getInstance()

        val signOutButton = findViewById<Button>(R.id.signOutButton)
        signOutButton.setOnClickListener {
            auth.signOut()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
