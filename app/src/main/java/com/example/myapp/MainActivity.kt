package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.GridView

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private val icons = arrayOf(R.drawable.note_icon, R.drawable.calculator_icon, R.drawable.currency_converter_icon) // nombres iconos

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
    }
}
