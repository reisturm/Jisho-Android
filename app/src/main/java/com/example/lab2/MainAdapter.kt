package com.example.lab2

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.definition_row.view.*

class MainAdapter(val kanjiResults: KanjiResults): RecyclerView.Adapter<CustomViewHolder>() {


    // numberOfItems
    override fun getItemCount(): Int {
        return kanjiResults.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context);
        val cellsForRow = layoutInflater.inflate(R.layout.definition_row, parent, false);
        return CustomViewHolder(cellsForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val kanjiCharacter = kanjiResults.data.get(position).japanese.get(0).word
        val reading = kanjiResults.data.get(position).japanese.get(0).reading
        val defs = mutableListOf<String>()

        for (d in kanjiResults.data.get(position).senses) {
            val def = StringBuilder()
            val numIdx = d.english_definitions.count()
            for (i in 0..numIdx - 2) {
                def.append(d.english_definitions.get(i)).append("; ")
            }
            def.append(d.english_definitions.get(numIdx - 1))
            defs.add(def.toString())
        }

        val defTextBoxContent = StringBuilder()
        for (i in 0..defs.count() - 1) {
            defTextBoxContent.append((i + 1).toString()).append(")  ").append(defs[i]).append(System.getProperty("line.separator"));
        }
        if (kanjiCharacter != null) {
            holder.view.pronounceTextView.text = reading
            holder.view.kanjiTextView.text = kanjiCharacter
        }
        else {
            holder.view.kanjiTextView.text = reading
        }
        holder.view.definitionTextView.text = defTextBoxContent.toString()
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}