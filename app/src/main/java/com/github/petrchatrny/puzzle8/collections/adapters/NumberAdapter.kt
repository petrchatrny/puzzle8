package com.github.petrchatrny.puzzle8.collections.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.petrchatrny.puzzle8.R
import com.github.petrchatrny.puzzle8.collections.onClickListeners.OnNumberClickListener
import com.github.petrchatrny.puzzle8.databinding.ItemNumberBinding

class NumberAdapter(items: IntArray, listener: OnNumberClickListener?, textSize: Int) :
    RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {
    private var items: IntArray = IntArray(9)
    private lateinit var listener: OnNumberClickListener
    private val textSize: Int

    init {
        this.items = items
        if (listener != null) {
            this.listener = listener
        }
        this.textSize = textSize
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        return NumberViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_number,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        // text size
        holder.binding.number.textSize = textSize.toFloat()

        // onClickListener
        if (this::listener.isInitialized) {
            holder.binding.numberLayout.setOnClickListener {
                listener.onNumberClick(items[position])
            }
        }

        // values
        if (items[position] == 0) {
            holder.binding.number.text = ""
            return
        }
        holder.binding.number.text = items[position].toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class NumberViewHolder(val binding: ItemNumberBinding) : RecyclerView.ViewHolder(binding.root)

}
