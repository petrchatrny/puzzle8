package com.github.petrchatrny.puzzle8.collections.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.petrchatrny.puzzle8.R
import com.github.petrchatrny.puzzle8.databinding.ItemPathNodeBinding

class PathAdapter(
    private val items: List<IntArray>
) : RecyclerView.Adapter<PathAdapter.PathViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PathViewHolder {
        return PathViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_path_node,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PathViewHolder, position: Int) {
        val numberAdapter = NumberAdapter(items[position], null, 20)
        numberAdapter.notifyDataSetChanged()
        holder.binding.pathRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = numberAdapter
            setRecycledViewPool(recycledViewPool)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PathViewHolder(val binding: ItemPathNodeBinding) : RecyclerView.ViewHolder(binding.root)
}