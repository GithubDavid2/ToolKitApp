package com.example.myapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var myDataset: MutableList<Pair<String, String>>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

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
            // Aquí se implementa la lógica para eliminar la nota
            val noteKey = myDataset[position].first
            val sharedPref = noteText.context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.remove(noteKey)
            editor.apply()
            myDataset.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, myDataset.size)
        }
    }

    override fun getItemCount() = myDataset.size
}

