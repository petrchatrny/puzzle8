package com.github.petrchatrny.puzzle8.collections.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.petrchatrny.puzzle8.R
import kotlinx.android.synthetic.main.item_number.view.*

class NumberAdapter(items: IntArray) : RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {
    private var items: IntArray = IntArray(9)

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        return NumberViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_number,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        if (items[position] == 0) {
            holder.itemView.number.text = ""
            return
        }
        holder.itemView.number.text = items[position].toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}
