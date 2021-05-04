package com.github.petrchatrny.puzzle8.collections.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.petrchatrny.puzzle8.R
import com.github.petrchatrny.puzzle8.collections.onClickListeners.OnNumberClickListener
import kotlinx.android.synthetic.main.item_number.view.*

class NumberAdapter(items: IntArray, listener: OnNumberClickListener) :
    RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {
    private var items: IntArray = IntArray(9)
    private var listener: OnNumberClickListener

    init {
        this.items = items
        this.listener = listener
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
        holder.itemView.setOnClickListener {
            listener.onNumberClick(items[position])
        }
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
