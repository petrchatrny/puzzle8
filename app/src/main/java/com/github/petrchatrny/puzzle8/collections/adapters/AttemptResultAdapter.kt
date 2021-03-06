package com.github.petrchatrny.puzzle8.collections.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.petrchatrny.puzzle8.R
import com.github.petrchatrny.puzzle8.collections.onClickListeners.OnAttemptClickListener
import com.github.petrchatrny.puzzle8.databinding.ItemAttemptResultBinding
import com.github.petrchatrny.puzzle8.model.entities.AttemptResult

class AttemptResultAdapter(
    private val items: List<AttemptResult>,
    private val listener: OnAttemptClickListener
) : RecyclerView.Adapter<AttemptResultAdapter.AttemptResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttemptResultViewHolder {
        return AttemptResultViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_attempt_result,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AttemptResultViewHolder, position: Int) {
        holder.binding.attempt = items[position]
        holder.binding.root.setOnClickListener {
            listener.onAttemptClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class AttemptResultViewHolder(val binding: ItemAttemptResultBinding) :
        RecyclerView.ViewHolder(binding.root)

}