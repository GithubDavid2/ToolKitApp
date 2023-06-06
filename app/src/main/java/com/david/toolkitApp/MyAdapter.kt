package com.david.toolkitApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyAdapter(private var myDataset: MutableList<Pair<String, String>>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val noteText = holder.view.findViewById<TextView>(R.id.note_text)
        val deleteButton = holder.view.findViewById<Button>(R.id.delete_button)

        noteText.text = myDataset[position].second

        deleteButton.setOnClickListener {
            val noteId = myDataset[position].first

            // Obtén el uid del usuario actualmente autenticado
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            // Si el usuario no está autenticado, no permitir borrar la nota
            if (userId == null) {
                Toast.makeText(noteText.context, "Necesitas estar autenticado para borrar notas.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.collection("users").document(userId).collection("notes").document(noteId)
                .delete()
                .addOnSuccessListener {
                    myDataset.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, myDataset.size)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(noteText.context, "Error al borrar nota: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }

    override fun getItemCount() = myDataset.size
}
