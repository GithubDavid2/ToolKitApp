package com.david.toolkitApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registro exitoso, crear un documento de usuario en Firestore
                        val userId = auth.currentUser!!.uid
                        val db = FirebaseFirestore.getInstance()
                        val userDocument = hashMapOf("email" to email)

                        db.collection("users").document(userId)
                            .set(userDocument)
                            .addOnSuccessListener {
                                // Iniciar la LoginActivity
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                // Manejar error aquí
                            }
                    } else {
                        // Si el registro falla, mostrar un mensaje al usuario.
                        Toast.makeText(this, "Registro fallido: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }


        // Nuevo botón para ir a la pantalla de login
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
